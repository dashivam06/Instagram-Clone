# Stage 1: Build the application
FROM eclipse-temurin:21-jdk as build
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre
ARG PORT=8080
ENV PORT=${PORT}
COPY --from=build /app/target/instagram-0.0.1-SNAPSHOT.jar /app/app.jar
RUN useradd -ms /bin/bash runtime
USER runtime
EXPOSE ${PORT}
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/app/app.jar"]
