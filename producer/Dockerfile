# Use a lightweight JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine AS build

# Set working directory in the container
WORKDIR /app

# Copy Gradle wrapper files and other dependencies
COPY gradlew settings.gradle build.gradle /app/
COPY gradle /app/gradle/

# Download dependencies to speed up builds
RUN ./gradlew dependencies --no-daemon

# Copy the project source code
COPY src /app/src

# Build the Spring Boot application
RUN ./gradlew build --no-daemon -x test -x contractTest -x generateContractTests -x generateClientStubs

# Second stage: runtime image
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# RUN groupadd -g 1000 producer && \
#    useradd -u 1000  -g producer -m producer && \
#    chown -R 755 /app

# Expose the application port
EXPOSE 8081

#USER producer

# Run the Spring Boot application
ENTRYPOINT ["sh", "-c", "java", "-jar app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]