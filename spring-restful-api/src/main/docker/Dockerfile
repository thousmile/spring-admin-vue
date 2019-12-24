FROM openjdk:8-jre-alpine
MAINTAINER Wang Chen Chen<932560435@qq.com>
ENV VERSION 1.0
# 挂载容器卷
VOLUME ["/tmp","/var/log/restful-api"]
ADD spring-restful-api-2.0.jar app.jar
# 设置环境为上海时间
RUN echo "Asia/Shanghai" > /etc/timezone
# 定义Spring环境
ENV SPRING_ENV="dev"
# 服务暴露端口PORT
EXPOSE 8090
# 启动 Spring Boot App 命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=$SPRING_ENV","-c"]
