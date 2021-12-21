#!/bin/bash

#BASE_DIR=$(pwd)
#LIB="${BASE_DIR}/lib/"
DUMP_OPTS="-Xmx512m "
GC_OPTS="-XX:+PrintGCApplicationStoppedTime XX:+PrintHeapAtGC -XX:+PrintGCDateStamps -Xloggc:./gc/gc.log "
JAVA_OPTS=${DUMP_OPTS}${GC_OPTS}
#START_CLASS="org.simpledfs.master.Master"
nohup java ${JAVA_OPTS} -jar ../build/simple-dfs-master-jar-with-dependencies.jar -f ./conf/master-config.xml >/dev/null 2>&1 &


restart(){
  stop
  start
}

usage() {
    echo "Usage: sh 执行脚本.sh [start|stop|restart|status]"
    exit 1
}

case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
exit 0

