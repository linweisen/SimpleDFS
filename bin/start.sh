#!/bin/bash

BASE_DIR=$(pwd)
LIB="${BASE_DIR}/lib/"
JAVA_OPTS=" -Xmx512m -XX:PermSize=64m -XX:MaxPermSize=128m -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=1 -XX:GCLogFileSize=1024k -XX:+HeapDumpOnOutOfMemoryError -server -Dfile.encoding=UTF-8"
START_CLASS="com.abc.cde.aaaserver"
nohup java ${JAVA_OPTS} -server -classpath "${LIB}/*:${LIB}/droolsRuntime/*:eiServer.jar" ${START_CLASS} &