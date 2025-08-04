# FIAP Project

Sistema desenvolvido em Java com arquitetura baseada na Clean Architecture.

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Maven
- MySQL
- Docker
- Flyway
- Lombok
- Bean Validation
- ModelMapper
- JUnit 5
- H2 Database (testes)

## Arquitetura do Projeto

O projeto adota a Clean Architecture e está organizado nas seguintes camadas:

### Application Layer (Camada de Aplicação)
- **Controller**: Define os endpoints expostos pela API REST.
- **UseCases**: Implementa as regras de negócio e casos de uso.
- **Mapper**: Conversão entre entidades, DTOs e modelos.

### Core Layer (Camada de Domínio)
- **Entities**: Entidades de negócio com as regras do domínio.
- **DTOs**: Objetos de transferência de dados (input e output).
- **Gateways**: Interfaces para acesso a dados externos.
- **Presenters**: Interfaces para formatação de saída.
- **Exceptions**: Exceções específicas do domínio.

### Infrastructure Layer (Camada de Infraestrutura)
- **Gateways**: Implementações concretas dos gateways.
- **Repository**: Interfaces do Spring Data JPA.
- **Model**: Entidades JPA para persistência no banco de dados.

## Endpoints

### Recurso: /users

- POST /users: Cria um novo usuário.
- GET /users/{userId}: Retorna os dados de um usuário pelo ID.
- PUT /users/{userId}: Atualiza os dados de um usuário existente.
- DELETE /users/{userId}: Remove um usuário do sistema.
- PUT /users/{userId}/password-update: Atualiza a senha de um usuário.
- PUT /users/{userId}/address: Atualiza o endereço de um usuário.

### Recurso: /auth

- POST /auth: Realiza login e autenticação do usuário.

### Recurso: /cuisine-types

- POST /cuisine-types: Cria um novo tipo de culinária.
- GET /cuisine-types/{cuisineTypeId}: Retorna os dados de um tipo de culinária pelo ID.
- PUT /cuisine-types/{cuisineTypeId}: Atualiza os dados de um tipo de culinária existente.
- DELETE /cuisine-types/{cuisineTypeId}: Remove um tipo de culinária do sistema.

### Recurso: /restaurants

- POST /restaurants: Cria um novo restaurante.
- GET /restaurants/{restaurantId}: Retorna os dados de um restaurante pelo ID.
- PUT /restaurants/{restaurantId}: Atualiza os dados de um restaurante existente.
- DELETE /restaurants/{restaurantId}: Remove um restaurante do sistema.
- GET /restaurants/search: Busca restaurantes por nome, localização ou tipo de culinária.

### Recurso: /menu-items

- POST /menu-items: Cria um novo item de menu.
- GET /menu-items/{menuItemId}: Retorna os dados de um item de menu pelo ID.
- PUT /menu-items/{menuItemId}: Atualiza os dados de um item de menu existente.
- DELETE /menu-items/{menuItemId}: Remove um item de menu do sistema.
- GET /menu-items/restaurant/{restaurantId}: Lista todos os itens de menu de um restaurante.

## Configuração do Ambiente

### Pré-requisitos

- Java 21
- Docker
- Maven

### Configuração do Banco de Dados

Crie um arquivo .env na raiz do projeto com as seguintes variáveis:

MYSQL_ROOT_PASSWORD=root  
MYSQL_DATABASE=fiap-project  
MYSQL_USER=dev  
MYSQL_PASSWORD=dev

SPRING_DATASOURCE_USERNAME=dev  
SPRING_DATASOURCE_PASSWORD=dev

### Gerar o Executável

Execute o comando abaixo para compilar o projeto e gerar o arquivo .jar:

`mvn clean package -DskipTests`

### Executar a Aplicação

Para iniciar a aplicação e os serviços necessários, utilize o comando:

`docker-compose up --build`

Os serviços serão iniciados nas seguintes portas:

- MySQL: 3306
- Aplicação Spring Boot: 8080