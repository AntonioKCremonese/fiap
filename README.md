# **TODO**
## Criação das classes base
- [x] User
- [x] Address
## Enums
- [x] UserTypeEnum
## Persistência
- [x] Adicionar JPA
- [x] Criar entities
- [x] Alterar driver de postgres para Mysql
## Endpoints
- [x] User Controller
- [x] GET User
- [x] POST User
- [x] PUT User
- [x] DELETE User
- [x] Login
## Docker
- [x] docker-compose e dockerfile
## Outras pendências
- [x] Adicionar validação para criação/atualização de usuário (validar se já existe email e login)
- [x] Adicionar validação para confirmação de senha do usuário durante o cadastro
- [x] Melhorar exceção de usuário não encontrado / usuário já existente
- [x] Adicionar validação do spring nos campos de DTO do UserInput
- [x] Adicionar implentação para criação do endereço (junto do usuário? separado? ao editar usuário, editar também o endereço? endpoint separado para editar endereço?)