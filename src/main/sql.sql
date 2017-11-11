/* COMANDOS UTEIS */
/* desabilitar safe mode caso precise */
SET SQL_SAFE_UPDATES = 0;


/* limpar dps dos commit destruidor */
DELETE FROM rel_sin_pro;

DELETE FROM rel_usu_art;

DELETE FROM rel_usu_pro;

DELETE FROM rel_arq_pro;

DELETE FROM pesquisa;

DELETE FROM projeto;

/* BANCO DE DADOS */

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

CREATE TABLE Rel_Arq_Pro (
    pro_id INT,
    arq_caminho VARCHAR(500),

    FOREIGN KEY (pro_id) REFERENCES Projeto (id)
);

CREATE TABLE Rel_Sin_Pro (
    pro_id INT,
    sinonimo VARCHAR(50),
    segmento INT,

    FOREIGN KEY (pro_id) REFERENCES Projeto (id)
);

ALTER TABLE Rel_Arq_Pro ADD id INT AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE Rel_Arq_Pro ADD arq_nome VARCHAR(250);

CREATE TABLE Rel_Usu_Pro (

    pro_id INT,
    usu_email VARCHAR(100),



    PRIMARY KEY (pro_id, usu_email),

    FOREIGN KEY (pro_id) REFERENCES Projeto (id),
    FOREIGN KEY (usu_email) REFERENCES Usuario (email)

);

CREATE TABLE Pesquisa (
	usuario VARCHAR(100),
    projeto INT,
    lista LONGBLOB,
    termos_relevantes LONGBLOB,
    sinonimo_objetivo VARCHAR(500),
    sinonimo_metodologia VARCHAR(500),
    sinonimo_resultado VARCHAR(500),
    
    FOREIGN KEY (usuario) REFERENCES Usuario (email),
    FOREIGN KEY (projeto) REFERENCES Projeto (id)
);

CREATE TABLE Rel_Usu_Art (
    art_id INT,
    usu_email VARCHAR(100),
    observacoes VARCHAR(250),

    PRIMARY KEY (art_id, usu_email),
    FOREIGN KEY (art_id) REFERENCES Rel_Arq_Pro (id),
    FOREIGN KEY (usu_email) REFERENCES Usuario (email)
);