# Use a minimal JDK base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy everything to container
COPY . .

# Build the app (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# Run the jar
CMD ["java", "-jar", "target/ecommerce-0.0.1-SNAPSHOT.jar"]
