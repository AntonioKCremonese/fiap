FROM openjdk:21
COPY target/shared-restaurant-0.0.1-SNAPSHOT.jar shared-restaurant.jar

# Copiar o script wait-for-it.sh para o container
COPY ./docker/wait-for-it.sh /wait-for-it.sh

# Garantir permissão de execução
RUN chmod +x /wait-for-it.sh

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shared-restaurant.jar"]
