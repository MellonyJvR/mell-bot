FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM mcr.microsoft.com/playwright/java:v1.44.0-jammy
WORKDIR /app
COPY --from=build /app/target/*-jar-with-dependencies.jar bot.jar
CMD ["java", "-jar", "bot.jar"]
