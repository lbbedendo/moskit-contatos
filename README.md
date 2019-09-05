# Tecnologias utilizadas
* Spring Boot 2.1.7
* Java 8
* Docker
* PostgreSQL
* Flyway (para evoluções do banco de dados)

# Instruções para executar o projeto

#### Criar volume para armazenar os dados do Postgresql
`docker create -v /var/lib/postgresql/data --name PostgresData alpine`

#### Executar o container do banco (Obs: estou mapeando para a porta 5532 na máquina host pois já tenho uma instância do PostgreSQL instalada ocupando a porta 5432)
`docker run -p 5532:5432 --name postgres -e POSTGRES_PASSWORD=admin -d --volumes-from PostgresData postgres`

