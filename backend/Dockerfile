FROM ubuntu:latest AS mariadb

# Install MariaDB server and client
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y mariadb-server mariadb-client \

# Start MariaDB server and create a user and a database
RUN service mariadb start && \
    mysql -e "SET PASSWORD FOR 'root'@localhost = PASSWORD('qwertyrootpass123123');" && \
    mysql -p qwertyrootpass123123 -e "CREATE DATABASE IF NOT EXISTS monitoring_backend_app;" && \
    mysql -p qwertyrootpass123123 -e "GRANT ALL PRIVILEGES ON monitoring-backend-app.* TO 'root'@'localhost';"
#    mysql -u root -p qwertyrootpass123123 -e "CREATE DATABASE monitoring-backend-app;" && \
#


EXPOSE 3306

CMD ["mysqld_safe"]

# Build the application
FROM maven:3.8.1-openjdk-19 AS build

# set working directory
WORKDIR /usr/app

# copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn -e -B dependency:resolve

# copy the project files and build
COPY src src
RUN mvn -e -B clean package

# set the startup command to run the JAR file
CMD ["java", "-jar", "target/java-spring-backend-app.jar"]

# expose the port
EXPOSE 8080


RUN apt-get install nodejs npm -y

FROM node:16-alpine AS build
WORKDIR /src/main/javascript/frontend
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:1.21-alpine
COPY --from=build /src/main/javascript/frontend/build /usr/share/nginx/html
COPY /src/main/javascript/frontend/default.conf /etc/nginx/conf.d/default.conf