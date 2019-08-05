FROM openjdk:8-jre-alpine
MAINTAINER Wang Chen Chen <932560435@qq.com>
ENV VERSION 1.3.0-SNAPSHOT
VOLUME /tmp
ADD spring-restful-api-1.0.jar app.jar
# 暴露端口PORT
EXPOSE 8090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]