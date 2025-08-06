# Use an official Java runtime as base image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar to the container
COPY target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
