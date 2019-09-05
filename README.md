# Tecnologias utilizadas
* Spring Boot 2.1.7
* Java 8
* Docker
* PostgreSQL
* Flyway (para evoluções do banco de dados)

# Instruções para executar o projeto

#### Criar volume para armazenar os dados do Postgresql
`docker create -v /var/lib/postgresql/data --name PostgresData alpine`

#### Executar o container do banco
`docker run -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=admin -d --volumes-from PostgresData postgres`

#### Criar a imagem do docket para a aplicação ("contatosapp")
`docker build . -t contatosapp`

#### Subir a aplicação com o docker-compose (banco + aplicacao)
`docker-compose up`