FROM maven:3.6.3-openjdk-17-slim

RUN apt-get update && \
	apt-get upgrade && \
    apt-get install -y mrtg snmp snmpd && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn package
EXPOSE 8080
CMD ["java", "-jar", "target/java-spring-backend-app.jar"]