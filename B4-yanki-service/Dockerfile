FROM openjdk:11.0-oracle
VOLUME /tmp
EXPOSE 8099
ADD ./target/yanki-service-0.0.1-SNAPSHOT.jar yanki-service.jar
ENTRYPOINT ["java","-jar","/yanki-service.jar"]