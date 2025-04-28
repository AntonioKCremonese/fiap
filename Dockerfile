FROM openjdk:21
COPY target/shared-restaurant-0.0.1-SNAPSHOT.jar shared-restaurant.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shared-restaurant.jar"]
