FROM openjdk:18-ea-11-jdk-alpine

ARG JAR_FILE=target/logoutput1-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]