# Use an official Maven image to build the app
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy the project files to the container
COPY pom.xml .
COPY src ./src

# Package the application (creates the .jar file)
RUN mvn clean package -DskipTests

# Use an official OpenJDK image to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the .jar file from the Maven build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port (important for Render to detect)
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
