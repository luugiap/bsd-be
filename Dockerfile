FROM maven:3.8.4-openjdk-17 as build

WORKDIR /app
COPY  . /app

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=build /app/target/bds-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java" , "-jar", "/app/app.jar"]
