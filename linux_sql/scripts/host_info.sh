#!/bin/bash
#check number of args
if [ $# -ne 5 ]; then
  echo "Not enough arguments!"
  echo "Args: hostname, port number, database name, username, password"
  exit 1
fi

#args
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Store raw hardware data
cpu_specs=$(lscpu)
mem_info=$(free)
hostname=$(hostname -f)

#Assign specific hardware information to variables
cpu_number=$(echo "$cpu_specs" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$cpu_specs" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$cpu_specs" | egrep "^Model name:" | awk '{print $3, $4, $5, $6, $7}' | xargs)
cpu_mhz=$(echo "$cpu_specs" | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache_temp=$(echo "$cpu_specs" | egrep "^L2 cache:" | awk '{print $3}' | xargs)
l2_cache=${l2_cache_temp::-1}
total_mem=$(echo "$mem_info" | egrep "Mem:" | awk '{print $2}' | xargs)
timestamp=$(date +"%F %T")

#-----PSQL-----
#Insert host info data
insert_stmt="INSERT INTO host_info(hostname,cpu_number,cpu_architecture, cpu_model,cpu_mhz,l2_cache,total_mem,timestamp)
      VALUES(
        '$hostname','$cpu_number','$cpu_architecture',
        '$cpu_model','$cpu_mhz','$l2_cache','$total_mem','$timestamp'
      )"

export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?

