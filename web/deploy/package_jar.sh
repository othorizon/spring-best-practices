#!/usr/bin/env bash
set -ex

if [ $1 == "false" ];then
echo skip packageConf;
exit 0
fi

PRG=$0
workdir=`cd $(dirname "$PRG"); pwd`
jarName=$2
serverPort=$3
logDir=$4
env=$5
# 打包方式 tar 包 或者 docker 镜像
type=${6-"tar"}

if [ -z $env ];then
echo env is emty
exit 1
fi

#=========

cd target
mkdir compose
mkdir compose/bin
mkdir compose/lib
mkdir compose/conf

cp $jarName.jar compose/lib/
cp ../deploy/bin/app.sh compose/bin/
cp ../deploy/conf/application-$env.yml compose/conf/application.yml

# sed命令在linux和macos的语法不同 macos 强制需要 -i 指定备份，该写法可以兼容两个系统
sed -i".bak" "s/\${jar_name}/$jarName.jar/g" compose/bin/app.sh
sed -i".bak" "s/\${server_port}/$serverPort/g" compose/bin/app.sh
sed -i".bak" "s:\${log_dir}:$logDir:g" compose/bin/app.sh
rm compose/bin/app.sh.bak


if [ $type == "tar" ]; then

  cd compose
  tar -czvf ../$jarName.tar.gz .

elif [ $type == "docker" ]; then

  cp ../deploy/Dockerfile compose/
  sed -i".bak" "s:\${server_port}:$serverPort:g" compose/Dockerfile
  sed -i".bak" "s/\${jar_name}/$jarName.jar/g" compose/Dockerfile
  rm compose/Dockerfile.bak

  cd compose
  docker build -t rizon/spring-best-practice .

else
  echo not support type: $type
fi
