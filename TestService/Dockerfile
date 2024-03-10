FROM openjdk:17-alpine
ARG JAR_FILE=target/TestService-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app2.jar

ENTRYPOINT ["java","-jar","app2.jar"]