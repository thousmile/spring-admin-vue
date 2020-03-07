FROM openjdk:8-jre

MAINTAINER Wang Chen Chen<932560435@qq.com>

ENV VERSION 1.0
# 挂载容器目录
# tmp: 临时文件目录，内嵌tomcat启动时，用到
# logs: spring-boot 日志输出的目录
# config: spring-boot 外部化配置文件目录
VOLUME ["/tmp","/logs","/config"]

# 对应pom.xml文件中的dockerfile-maven-plugin插件JAR_FILE的值
ARG JAR_FILE

# 复制打包 完成后的jar文件，名字修改成 app.jar
COPY ${JAR_FILE} app.jar

# 设置时区为上海
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

# 设置编码
ENV LANG C.UTF-8

# 服务暴露端口PORT
EXPOSE 8090

# 启动 Spring Boot App 命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
