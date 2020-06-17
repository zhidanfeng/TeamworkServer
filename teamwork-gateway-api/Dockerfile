#使用jdk8作为基础镜像
FROM java:8
#指定作者
MAINTAINER zhidanfeng
#暴露容器的8189端口
EXPOSE 8189
#将复制指定的xxl-job-admin-2.1.0.jar为容器中的job.jar，相当于拷贝到容器中取了个别名
ADD target/teamwork-gateway-api-0.0.1-SNAPSHOT.jar /teamwork-gateway-api.jar
#创建一个新的容器并在新的容器中运行命令
RUN bash -c 'touch /teamwork-gateway-api.jar'
#设置时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#相当于在容器中用cmd命令执行jar包  指定外部配置文件
#ENTRYPOINT ["java","-jar","/teamwork-eureka-server.jar","--spring.config.location=/usr/local/project/docker/teamwork-eureka-server/config/application.application.properties"]
ENTRYPOINT ["java","-jar","/teamwork-gateway-api.jar"]