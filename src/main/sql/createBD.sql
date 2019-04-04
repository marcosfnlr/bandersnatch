/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  raphaelcja
 * Created: 28-Mar-2019
 */

/*WHENEVER SQLERROR CONTINUE NONE*/
DROP TABLE Invitation CASCADE CONSTRAINTS;
DROP TABLE History CASCADE CONSTRAINTS;
DROP TABLE Choice CASCADE CONSTRAINTS;
DROP TABLE Paragraph CASCADE CONSTRAINTS;
DROP TABLE Book CASCADE CONSTRAINTS;
DROP TABLE Account CASCADE CONSTRAINTS;

CREATE TABLE Account(
    id_account VARCHAR2(10) PRIMARY KEY NOT NULL,
    password VARCHAR2(100) NOT NULL,
    last_name VARCHAR2(100) NOT NULL,
    first_name VARCHAR2(50) NOT NULL
);

CREATE TABLE Book(
    id_book INT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    title VARCHAR2(100) NOT NULL,
    open_write NUMBER(1) NOT NULL,
    published NUMBER(1) NOT NULL,
    fk_account VARCHAR2(10) NOT NULL,
    FOREIGN KEY (fk_account) REFERENCES Account(id_account)
);

CREATE TABLE Paragraph(
    id_paragraph INT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    text VARCHAR2(1000) NOT NULL,
    beginning NUMBER(1) NOT NULL,
    conclusion NUMBER(1) NOT NULL,
    fk_book INT NOT NULL,
    fk_account VARCHAR2(10) NOT NULL,
    FOREIGN KEY (fk_book) REFERENCES Book(id_book),
    FOREIGN KEY (fk_account) REFERENCES Account(id_account)
);

CREATE TABLE Choice(
    id_choice INT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    text VARCHAR2(250) NOT NULL,
    locked NUMBER(1) NOT NULL,
    only_choice NUMBER(1) NOT NULL,
    cond_should_pass NUMBER(1),
    fk_parag_orig INT NOT NULL,
    fk_parag_dest INT NOT NULL,
    fk_parag_cond INT,
    FOREIGN KEY (fk_parag_orig) REFERENCES Paragraph(id_paragraph),
    FOREIGN KEY (fk_parag_dest) REFERENCES Paragraph(id_paragraph),
    FOREIGN KEY (fk_parag_cond) REFERENCES Paragraph(id_paragraph)
);

CREATE TABLE History(
    fk_account VARCHAR2(10) NOT NULL,
    fk_book INT NOT NULL,
    fk_choice INT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    PRIMARY KEY (fk_account, fk_book, fk_choice),
    FOREIGN KEY (fk_account) REFERENCES Account(id_account),
    FOREIGN KEY (fk_book) REFERENCES Book(id_book),
    FOREIGN KEY (fk_choice) REFERENCES Choice(id_choice)
);

CREATE TABLE Invitation(
    fk_account VARCHAR2(10) NOT NULL,
    fk_book INT NOT NULL,
    PRIMARY KEY (fk_account, fk_book),
    FOREIGN KEY (fk_account) REFERENCES Account(id_account),
    FOREIGN KEY (fk_book) REFERENCES Book(id_book)
);
