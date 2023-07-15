#!/bin/bash

## get the current working dir
_pwd="$(pwd)"

PROJECT_NAME="Notification Service"
PROJECT_DIR="$_pwd/target"
pid_file="$PROJECT_DIR/app.pid"

echo "Stopping -------------------- $PROJECT_NAME -------------------"
pid=$(cat $pid_file)
kill $pid
rm -fR $PROJECT_DIR
echo  "done killing $pid"