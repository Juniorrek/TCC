CREATE DATABASE TCC;



USE TCC;



CREATE TABLE Usuario (

    email VARCHAR(100),

    nome VARCHAR(100),

    senha VARCHAR(100),

    confirmacao INT,

    PRIMARY KEY (email)

);
