#!/bin/bash
cmd=$1
username=$2
password=$3

#start docker
sudo systemctl status docker || systemctl start docker
#checks exit code of docker status, if code is not 0 (docker is not active), starts docker

#check container status via exit code
docker container inspect jrvs-psql
container_status=$?

#execute start/create/stop command
case $cmd in
  create)
  #check if container already exists
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    exit 1
  fi

  #check for required arguments
  if [ $# -ne 3 ];
  then
    echo 'Not enough arguments! Include Username and Password to create'
    exit 1
  fi

  #create container with username and password
  docker volume create pgdata
  docker run --name jrvs-psql -e POSTGRES_PASSWORD=$password -e POSTGRES_USERNAME=$username -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
  exit $?
  ;;

  #exectute start or stop command
  start|stop)
  #check container status; exit 1 if container has not been created
  if [ $container_status -eq 1 ]; then
    echo 'Container does not exist'
    exit 1
  fi

  #Start/stop container
  docker container $cmd jrvs-psql
  exit $?
  ;;

#Illegal args
*)
  echo 'Illegal command'
  echo 'Commands: start|stop|create'
  exit 1
  ;;
esac


