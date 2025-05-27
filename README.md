# FIAP Project

Sistema desenvolvido em Java com arquitetura baseada no padrão MVC (Model-View-Controller).

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Maven
- MySQL
- Docker
- Flyway

## Arquitetura do Projeto

O projeto adota a arquitetura MVC e está organizado nos seguintes pacotes:

- Controller: Define os endpoints expostos pela aplicação.
- DTO: Objetos de transferência de dados (input e output).
- Exception: Tratamento de exceções.
- Mapper: Conversão entre entidades e DTOs.
- Model: Representação das entidades no banco de dados.
- Repository: Interfaces responsáveis pela persistência de dados.
- Service: Implementa as regras de negócio.

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