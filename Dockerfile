FROM openjdk:17-alpine
ARG JAR_FILE=target/HW1_OpenSchool-1.0.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]