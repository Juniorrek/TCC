CREATE DATABASE TCC;

USE TCC;

CREATE TABLE Usuario (
    email VARCHAR(100),
    nome VARCHAR(100),
    senha VARCHAR(100),
    confirmacao INT,

    PRIMARY KEY (email)
);

CREATE TABLE Projeto (
    id INT AUTO_INCREMENT,
    nome VARCHAR(100),
    descricao VARCHAR(200),
    email VARCHAR(100),
    
    PRIMARY KEY (id),
    FOREIGN KEY (email) REFERENCES Usuario (email)
);