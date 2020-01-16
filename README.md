# spring boot security jwt 整合vue-admin-template 2.0 项目模板

首先感谢[PanJiaChen](https://github.com/PanJiaChen) 的 vue-admin-template 模板，我是一名java程序员，所以前端的技术不怎么样。vue.js也只是会用一点，非常感谢[PanJiaChen](https://github.com/PanJiaChen) 的模板支持。

如果大家觉得写的不错，就请点亮我[GitHub](https://github.com/thousmile/spring-admin-vue)的心心。非常感谢！

如果大家在搭建过程中遇到问题，欢迎找我

QQ:    932560435

GitHub   ：  https://github.com/thousmile

码云：  https://gitee.com/thousmile

数据库里的用户，password 和 username 都是相同的
```
root_admin  :  root_admin
xiaoxiannv  :  xiaoxiannv
xiannva     :  xiannva
```
简单的问题不要打扰我，如果有问题直接说什么问题，不要问在不在！！！    

## 效果图

![](images/20181030201718.png)

![](images/20181030201808.png)



### [数据库教程](./help/database.md)



### [后台教程](./help/spring.md)



### [前端教程](./help/vue.md)



### Linux 启动脚本 

```
将打包完成的 jar 文件和 spring-boot.sh 放置在服务器同一个目录中， 
修改文件的权限，附加执行权限
sudo chmod 754 spring-boot.sh

#脚本用法: ./脚本名.sh {start|stop|restart|status} {APP_NAME}

#例子(一)：./spring-boot.sh start example.jar 			    # 默认启动，默认是 prod 环境 8090端口

#例子(二)：./spring-boot.sh start example.jar prod 			# 指定环境  默认是 8090端口

#例子(三)：./spring-boot.sh start example.jar prod 8080		 # 指定环境和端口号

```



### maven  打包到docker 容器( 确保本地安装docker )

```
# 打包到本地docker命令
mvn dockerfile:build

# docker 启动 激活 prod 环境 并且将 容器的logs目录 挂载到 当前宿主机的logs目录
sudo docker run -d -p 8090:8090 --name spring-restful-api \
-v $PWD/logs:/logs \
-e "SPRING_PROFILES_ACTIVE=prod" \
xaaef/spring-restful-api

# docker 启动 激活 prod 环境 并且将 容器的 logs , config 目录 挂载到 当前宿主机的 logs , config 目录
# 如果只是修改了 application-prod.yml 文件，无需重新打包镜像，将修改完成后的 application-prod.yml文件
# 放在 config 目录下，spring boot 会覆盖 jar 包内的 application-prod.yml 属性
sudo docker run -d -p 8090:8090 --name spring-restful-api \
-v $PWD/logs:/logs \
-v $PWD/config:/config \
-e "SPRING_PROFILES_ACTIVE=prod" \
xaaef/spring-restful-api

```



### 查看 swagger2 API 文档 ：  http://localhost:8090/doc.html 



### 七牛云要换成自己的配置

```
# 七牛云文件上传配置
qiniu:
  accessKey: 
  secretKey: 
  bucketName: 
  fileDomain: 
```



### 有钱的兄弟捧个钱场，没钱的捧个人场



![](images/20191224164214.png)



































































