# Build stage with Maven and Java 21
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /home/app
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn clean package -DskipTests

# Runtime stage with OpenJDK 21
FROM eclipse-temurin:21-jre
COPY target/instagram-0.0.1-SNAPSHOT.jar /usr/local/lib/instagram-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/instagram-0.0.1-SNAPSHOT.jar"]
