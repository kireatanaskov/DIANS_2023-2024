# Use an official Maven runtime as a parent image
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container at /app
COPY . .

# Change to the directory of the specific project
WORKDIR /app/Domasna\ Rabota\ 3

# Build the project and package the JAR file
RUN mvn clean package -DskipTests

# Use a smaller base image for the final runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the new stage
COPY --from=build /app/Domasna\ Rabota\ 3/target/demo-0.0.1-SNAPSHOT.jar CultureCompassDIANS.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "CultureCompassDIANS.jar"]
