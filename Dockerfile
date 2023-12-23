FROM maven:3.8.3-openjdk-17 AS build

WORKDIR "/app"

COPY "." .

WORKDIR "/Domasna Rabota 3/CultureCompassDIANS"

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR "/Domasna Rabota 3/CultureCompassDIANS"
COPY --from=build "/target/demo-0.0.1-SNAPSHOT.jar" "CultureCompassDIANS.jar"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "CultureCompassDIANS.jar"]

