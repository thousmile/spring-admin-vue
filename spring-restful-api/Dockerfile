FROM eclipse-temurin:11.0.13_8-jre

MAINTAINER Wang Chen Chen<932560435@qq.com>

ENV VERSION 1.1
# 挂载容器目录
# tmp: 临时文件目录，内嵌tomcat启动时，用到
# logs: spring-boot 日志输出的目录
# config: spring-boot 外部化配置文件目录
VOLUME ["/tmp","/logs","/config"]

# 对应pom.xml文件中的dockerfile-maven-plugin插件JAR_FILE的值
ARG JAR_FILE

# 复制打包 完成后的jar文件，名字修改成 app.jar
COPY ${JAR_FILE} app.jar

# 设置编码
ENV LANG C.UTF-8

# 设置环境变量
ENV CONSUL_SERVER_PORT=8500

# JVM参数
ENV JVM_OPTS="-server -Xms2048M -Xmx2048M -XX:+DisableExplicitGC -XX:+HeapDumpOnOutOfMemoryError"

# spring 启动环境
ENV SPRING_ENV="prod"

# 服务暴露端口PORT
EXPOSE 18888

# 启动 Spring Boot App 命令
ENTRYPOINT java ${JVM_OPTS} -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -jar /app.jar --spring.profiles.active=${SPRING_ENV}
