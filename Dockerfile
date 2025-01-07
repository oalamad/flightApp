#Multi Stage Docker Build
# Stage 1: Build Stage
FROM registry.access.redhat.com/ubi8/openjdk-17:1.21-1.1733300809 AS build
# Copy source code and build the application
WORKDIR /app
COPY ./ /app
RUN mvn clean install

# Stage 2: Runtime Stage
FROM registry.access.redhat.com/ubi8/openjdk-17:1.21-1.1733300809 AS runtime
# Copy the application JAR from the build stage
WORKDIR /app
COPY --from=build /app/target/fa-0.0.1-SNAPSHOT.jar app.jar
# Set the default command to run the application
CMD ["java", "-jar", "/app/app.jar"]
