#Multi Stage Docker Build
# Stage 1: Build Stage
FROM ubuntu:24.04 AS build
RUN apt update && apt install -y \
    maven \
    wget \
    tar

# Install JDK
RUN apt-get purge openjdk-\* -y && \
    wget https://builds.openlogic.com/downloadJDK/openlogic-openjdk/17.0.13+11/openlogic-openjdk-17.0.13+11-linux-x64.tar.gz && \
    tar -xvf openlogic-openjdk-17.0.13+11-linux-x64.tar.gz && \
    mv openlogic-openjdk-17.0.13+11-linux-x64 /usr/local/openjdk-17

ENV JAVA_HOME=/usr/local/openjdk-17
ENV PATH=$PATH:$JAVA_HOME/bin

# Copy source code and build the application
WORKDIR /app
COPY ./ /app
RUN mvn clean install

# Stage 2: Runtime Stage
FROM ubuntu:24.04 AS runtime
RUN apt update && apt install -y wget tar
COPY --from=build /usr/local/openjdk-17 /usr/local/openjdk-17
ENV JAVA_HOME=/usr/local/openjdk-17
ENV PATH=$PATH:$JAVA_HOME/bin
# Copy the application JAR from the build stage
WORKDIR /app
COPY --from=build /app/target/fa-0.0.1-SNAPSHOT.jar app.jar
# Set the default command to run the application
CMD ["java", "-jar", "/app/app.jar"]