# Tecnologias utilizadas
* Spring Boot 2.1.7
* Java 8
* Docker
* PostgreSQL
* Flyway (para evoluções do banco de dados)
* H2 (banco em memória para testes de integração)
* Swagger (documentação da API)

# Instruções para executar o projeto

#### Criar volume para armazenar os dados do Postgresql
`docker create -v /var/lib/postgresql/data --name PostgresData alpine`

#### Compilar a aplicação para gerar o *.jar na pasta /target
`mvn compile`

#### Criar a imagem do docker para a aplicação ("contatosapp") utilizando o *.jar gerado no passo anterior
`docker build . -t contatosapp`

#### Subir a aplicação com o docker-compose (banco + aplicação)
`docker-compose up`

#### Para abrir a documentação do Swagger, navegar para o seguinte endereço
`http://localhost:8080/swagger-ui.html`

#### Opcional: Nesse repositório está incluído o seguinte arquivo para ser importado no Postman:
`contatos.postman_collection.json`