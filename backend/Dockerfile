FROM maven:3.6.3-openjdk-17-slim
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn package
EXPOSE ${PORT_BACKEND}
CMD ["java", "-jar", "target/java-spring-backend-app.jar"]