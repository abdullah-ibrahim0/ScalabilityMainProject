FROM openjdk:23-slim
WORKDIR /app
COPY ./target/SearchService-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
