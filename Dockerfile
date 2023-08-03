FROM openjdk:18-jdk-slim
WORKDIR /app
COPY /target/*.jar main.jar
CMD ["java", "-jar", "main.jar"]