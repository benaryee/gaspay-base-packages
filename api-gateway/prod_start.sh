#!/bin/sh

_pwd="$(pwd)"


PROJECT_NAME="Api Gateway"
PROJECT_DIR="$_pwd/target"
pid_file="$PROJECT_DIR/app.pid"
PROJECT_JAR="$_pwd/target/api-gateway.jar"
PROJECT_LOG="/var/log/codebase/api-gateway.log"


echo "Starting -------------------- $PROJECT_NAME -------------------"
./prod_build.sh

nohup java -jar $PROJECT_JAR >> $PROJECT_LOG 2>&1 &
echo $! > $pid_file

tail -f $PROJECT_LOG
