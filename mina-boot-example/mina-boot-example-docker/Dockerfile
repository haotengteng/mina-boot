#FROM java:8
FROM anolis-registry.cn-zhangjiakou.cr.aliyuncs.com/openanolis/openjdk:8-8.6
ADD target/mina-boot-example-docker-0.0.3-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]