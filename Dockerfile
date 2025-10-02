FROM maven:3.9.6-amazoncorretto-17 AS build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

COPY src src

RUN chmod +x ./mvnw

RUN ./mvnw package -DskipTests

FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY --from=build /app/target/currencyConvertor-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]