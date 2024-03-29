
#指定基础镜像，在其上进行定制
FROM openjdk:15

#维护者信息
MAINTAINER yingdatech <yingdatech@yingdatech.com>

#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层。
VOLUME /tmp

#创建应用目录
RUN bash -c "mkdir -p /usr/local/yingdatech/opc-server/logs"
RUN bash -c "mkdir -p /usr/local/yingdatech/opc-server/upload"

#复制上下文目录下的target/powerplantetms-1.0.0.jar 到容器里
ADD target/opc-1.0.0.jar /usr/local/yingdatech/opc-server/opc-server-1.0.0.jar

#bash方式执行，larkerp-server-1.0.0.jar可访问
#RUN新建立一层，在其上执行这些命令，执行结束后， commit 这一层的修改，构成新的镜像。
RUN bash -c "touch /usr/local/yingdatech/opc-server/opc-server-1.0.0.jar"

#声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
#EXPOSE 80 11005
EXPOSE 9001
#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/local/yingdatech/opc-server/opc-server-1.0.0.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=11005","/usr/local/yingdatech/bee-server/bee-server-1.0.0.jar"]

#设置时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
