FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app

COPY --from=build /app/target/shared-restaurant-*.jar shared-restaurant.jar

COPY ./docker/wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shared-restaurant.jar"]