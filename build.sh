#!/bin/bash

mvn clean package -Dmaven.test.skip=true

if [ ! -d "./build/" ];then
  mkdir ./build
else
  echo "build directory had exits, will delete..."
  rm -rf ./build
  mkdir ./build
fi

cp ./simple-dfs-master/target/simple-dfs-master-jar-with-dependencies.jar ./build
cp ./simple-dfs-work/target/simple-dfs-work.jar ./build
