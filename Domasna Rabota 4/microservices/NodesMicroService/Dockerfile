FROM openjdk:17
WORKDIR /NodesMicroService
COPY target/*.jar /app.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "/app.jar"]