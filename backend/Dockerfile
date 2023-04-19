FROM maven:3.6.3-openjdk-17-slim

RUN DEBIAN_FRONTEND=noninteractive apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y mrtg snmp snmpd

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/

RUN mvn package

EXPOSE 8080
CMD ["java", "-jar", "target/java-spring-backend-app.jar"]
