# Start with a Maven image to build the app
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Now use a lightweight JDK image to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Run the app
CMD ["java", "-jar", "app.jar"]
