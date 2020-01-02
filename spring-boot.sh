#!/bin/bash
#chkconfig:345 61 61
#description: Spring Boot App Script
#auther: Wang Chen Chen

#===========================================================================================
# Document And Example
#===========================================================================================
#用法: ./脚本名.sh {start|stop|restart|status} {APP_NAME}
#例子(一)：./spring-boot.sh start example.jar 					# 默认启动
#例子(二)：./spring-boot.sh start example.jar prod 				# 指定环境
#例子(三)：./spring-boot.sh start example.jar prod 8080			# 指定环境和端口号

#===========================================================================================
# Spring Boot Application Start Configuration
#===========================================================================================
# Spring Boot App 名称
APP_NAME=$2
# spring.profiles.active 配置激活环境
SPRING_ENV=$3
# server.port  App 端口
SPRING_PORT=$4

# 第一个参数 start|stop|restart|status 中的一个
if [ "$1" = "" ];
then
    echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
    exit 1
fi

# 第二个参数 xxx.jar 包名称 
if [ "$APP_NAME" = "" ];
then
    echo -e "\033[0;31m 未输入应用名 \033[0m"
    exit 1
fi

# 第三个参数 spring.profiles.active 配置激活环境
if [ "$SPRING_ENV" = "" ];
then
	SPRING_ENV=prod
fi

# 第四个参数 server.port App 端口 
if [ "$SPRING_PORT" = "" ];
then
    SPRING_PORT=8090
fi

# 启动服务
function start(){
    count=`ps -ef |grep java|grep $APP_NAME|grep -v grep|wc -l`
    if [ $count != 0 ];then
        echo "$APP_NAME is running..."
    else
        echo "Start $APP_NAME Active: $SPRING_ENV Port: $SPRING_PORT success..." &
		nohup java -jar -Dspring.profiles.active=$SPRING_ENV -Dserver.port=$SPRING_PORT $APP_NAME >/dev/null 2>&1 &
    fi
}

# 停止服务
function stop(){
    echo "Stop $APP_NAME"
    boot_id=`ps -ef |grep java|grep $APP_NAME|grep -v grep|awk '{print $2}'`
    count=`ps -ef |grep java|grep $APP_NAME|grep -v grep|wc -l`
    if [ $count != 0 ];then
        kill $boot_id
        count=`ps -ef |grep java|grep $APP_NAME|grep -v grep|wc -l`
        boot_id=`ps -ef |grep java|grep $APP_NAME|grep -v grep|awk '{print $2}'`
        kill -15 $boot_id
    fi
}

# 重启服务
function restart(){
    stop
    sleep 2
    start
}

# 查看服务状态
function status(){
    count=`ps -ef |grep java|grep $APP_NAME|grep -v grep|wc -l`
    if [ $count != 0 ];then
        echo "$APP_NAME is running..."
    else
        echo "$APP_NAME is not running..."
    fi
}

case $1 in
    start)
    start;;
    stop)
    stop;;
    restart)
    restart;;
    status)
    status;;
    *)

    echo -e "\033[0;31m Usage: \033[0m  \033[0;34m sh  $0  {start|stop|restart|status}  {APP_NAME} \033[0m
\033[0;31m Example: \033[0m
      \033[0;33m sh  $0  start example.jar \033[0m"
esac

