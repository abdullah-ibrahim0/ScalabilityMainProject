FROM openjdk:23-jdk-slim

WORKDIR /app

COPY target/Transaction-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar","app.jar"]