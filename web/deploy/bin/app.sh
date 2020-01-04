#!/usr/bin/env bash

export set LC_ALL='en_US.UTF-8'

#=====
# replace placeholder by package_jar.sh
jar_name=${jar_name}
server_port=${server_port}
logdir=${log_dir}
#=====

PRG="$0"
BIN_HOME=`cd $(dirname "$PRG"); pwd`
APP_HOME=`cd ${BIN_HOME}/..;pwd`
jar_path="${APP_HOME}/lib"
#LOG_DIR is log4j dir
JVM_OPTS="-DLOG_DIR=${logdir} -Duser.timezone=GMT+08 -server -Xms6G -Xmx6G -XX:MaxMetaspaceSize=512M -XX:MetaspaceSize=512M -Xloggc:${logdir}/gc.log -XX:-PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationStoppedTime -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70  -XX:+UseCMSCompactAtFullCollection -XX:+CMSParallelRemarkEnabled -XX:+HeapDumpOnOutOfMemoryError"
#JVM_OPTS="-Duser.timezone=GMT+08 -server"
startlog="${logdir}/console.log"
server_url=http://localhost:$server_port

mkdir -p $logdir

#=====

ping_server(){

    while [[ true ]]
    do
      echo waiting  server $server_url ...
      urlstatus=$(curl -s -m 5 -IL $server_url |grep HTTP)
      if [ "${urlstatus}" != "" ];then
            echo "${server_url} is ONLINE"
            break
      fi
      sleep 3
    done
}

get_pid(){
    pid=`ps -ef | grep -v grep | grep "${jar_path}/${jar_name}" |  awk '{print $2}'`
    echo $pid
}

process_is_running(){
	pid=`get_pid`
	if [ -z $pid ]
	then
		echo 1
	else
		echo 0
	fi
}

start() {
	pid=`get_pid`
	if test `process_is_running` -eq 0
	then
		echo "WARN:process is running,pid is $pid"
		exit 1
	else
		echo "Starting server: "
		echo "setsid java ${JVM_OPTS} -Dspring.config.additional-location=file:${APP_HOME}/conf/application.properties,file:${APP_HOME}/conf/application.yml -jar ${jar_path}/${jar_name} >${startlog} 2>&1 &"
		# cd app_home for log relative path
		cd $APP_HOME
		setsid java ${JVM_OPTS} -Dspring.config.additional-location=file:${APP_HOME}/conf/application.properties,file:${APP_HOME}/conf/application.yml -jar ${jar_path}/${jar_name} >${startlog} 2>&1 &
		sleep 2s
		pid=`get_pid`
		if test `process_is_running` -eq 0
		then
			echo "start success! pid is $pid"
			ping_server
		else
			echo "start fail."
		fi
	fi
}

stop() {
	pid=`get_pid`
	if test `process_is_running` -eq 0
	then
		echo "stopping..."
		pid=`get_pid`
		kill -9 $pid
		if test `process_is_running` -eq 0
		then
			echo "stop fail"
		else
			echo "stop success"
		fi
	else
		echo "WARN:process is not exist."
	fi
}

restart() {
    stop
    start
}

rh_status() {
	pid=`get_pid`
	if test `process_is_running` -eq 0
	then
		echo "process is running,pid is $pid"
	else
		echo "process is not running"
	fi
    RETVAL=$?
    return $RETVAL
}

#=====

case "$1" in
    start)
        start
    ;;
    stop)
        stop
    ;;
    restart)
        restart
    ;;
    status)
        rh_status
    ;;
    pid)
        get_pid
    ;;
    *)
        echo $"Usage: $0 {start|stop|status|restart|pid}"
        exit 1
esac

