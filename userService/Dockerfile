FROM openjdk:23-slim
WORKDIR /app
COPY ./target/UserService.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
