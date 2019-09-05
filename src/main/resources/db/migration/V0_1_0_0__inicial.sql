CREATE SCHEMA mskt;

CREATE TABLE mskt.contato (
    id serial NOT NULL,
    nome character varying NOT NULL,
    telefone_comercial varchar(20),
    telefone_residencial varchar(20),
    telefone_celular varchar(20),
    email_comercial character varying,
    email_pessoal character varying,
    data_nascimento timestamp,
    favorito boolean not null default false,
    CONSTRAINT pk_contato PRIMARY KEY (id)
);