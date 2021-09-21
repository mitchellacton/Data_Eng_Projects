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
#timestamp=$(date +"%F %T")
timestamp=$(date +"%Y-%m-%d %H:%M:%S")
memory_free=$(echo "$mem_info" | egrep "Mem:" | awk '{print $4}' | xargs)
cpu_idle=$(vmstat | tail -1 | awk '{print $15}' | xargs)
cpu_kernel=$(vmstat | tail -1 | awk '{print $14}' | xargs)
disk_io=$(vmstat -d | tail -1 | awk '{print $10}' | xargs)
disk_available_temp=$(df -BM / | tail -1 | awk '{print $4}' | xargs)
disk_available=${disk_available_temp::-1}

#-----PSQL-----
#get host id
host_id_string="(SELECT id,
              '$timestamp','$memory_free',
              '$cpu_idle','$cpu_kernel',
              '$disk_io','$disk_available' from host_info WHERE hostname='$hostname')";

insert_stmt="INSERT INTO host_usage(
        host_id,timestamp,memory_free,
        cpu_idle,cpu_kernel,disk_io,disk_available)
        $host_id_string
      "

export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?

