#!/bin/bash

mvn clean package -Dmaven.test.skip=true

if [ ! -d "./build/" ];then
  mkdir ./data
else
  echo "文件夹已经存在"
fi