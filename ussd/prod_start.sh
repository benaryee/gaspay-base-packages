#!/bin/sh

_pwd="$(pwd)"


PROJECT_NAME="Gaspay Ussd"
PROJECT_DIR="$_pwd/target"
pid_file="$PROJECT_DIR/app.pid"
PROJECT_JAR="$_pwd/target/ussd-1.0.jar"
PROJECT_LOG="/var/log/codebase/gaspay-ussd-app.log"


echo "Starting -------------------- $PROJECT_NAME -------------------"
./prod_build.sh

nohup java -jar $PROJECT_JAR >> $PROJECT_LOG 2>&1 &
echo $! > $pid_file

tail -f $PROJECT_LOG
