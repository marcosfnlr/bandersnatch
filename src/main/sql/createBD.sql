/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  raphaelcja
 * Created: 28-Mar-2019
 */

WHENEVER SQLERROR CONTINUE NONE
DROP TABLE Invitation;
DROP TABLE History;
DROP TABLE Choice;
DROP TABLE Paragraph;
DROP TABLE Book;
DROP TABLE Account;

CREATE TABLE Account(
    id_account VARCHAR2(10) PRIMARY KEY NOT NULL,
    password VARCHAR2(100) NOT NULL,
    last_name VARCHAR2(100) NOT NULL,
    first_name VARCHAR2(50) NOT NULL
);

CREATE TABLE Book(
    id_book INT GENERATED ALWAYS as IDENTITY PRIMARY KEY NOT NULL,
    title VARCHAR2(100) NOT NULL,
    open NUMBER(1) NOT NULL,
    published NUMBER(1) NOT NULL,
    fk_book_account VARCHAR2(10) NOT NULL,
    FOREIGN KEY (fk_book_account) REFERENCES Account(id_account)
);

CREATE TABLE Paragraph(
    id_paragraph INT GENERATED ALWAYS as IDENTITY PRIMARY KEY NOT NULL,
    text VARCHAR2(1000) NOT NULL,
    conclusion NUMBER(1) NOT NULL,
    fk_paragraph_book INT NOT NULL,
    fk_paragraph_account VARCHAR2(10) NOT NULL,
    FOREIGN KEY (fk_paragraph_book) REFERENCES Book(id_book),
    FOREIGN KEY (fk_paragraph_account) REFERENCES Account(id_account)
);

CREATE TABLE Choice(
    id_choice INT GENERATED ALWAYS as IDENTITY PRIMARY KEY NOT NULL,
    text VARCHAR2(250) NOT NULL,
    locked NUMBER(1) NOT NULL,
    final NUMBER(1) NOT NULL,
    cond_should_pass NUMBER(1),
    fk_choice_parag_orig INT NOT NULL,
    fk_choice_parag_dest INT NOT NULL,
    fk_choice_parag_cond INT,
    FOREIGN KEY (fk_choice_parag_orig) REFERENCES Paragraph(id_paragraph),
    FOREIGN KEY (fk_choice_parag_dest) REFERENCES Paragraph(id_paragraph),
    FOREIGN KEY (fk_choice_parag_cond) REFERENCES Paragraph(id_paragraph)
);

CREATE TABLE History(
    fk_history_account VARCHAR2(10) NOT NULL,
    fk_history_book INT NOT NULL,
    fk_history_choice INT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    PRIMARY KEY (fk_history_account, fk_history_book, fk_history_choice),
    FOREIGN KEY (fk_history_account) REFERENCES Account(id_account),
    FOREIGN KEY (fk_history_book) REFERENCES Book(id_book),
    FOREIGN KEY (fk_history_choice) REFERENCES Choice(id_choice)
);

CREATE TABLE Invitation(
    fk_invitation_account VARCHAR2(10) NOT NULL,
    fk_invitation_book INT NOT NULL,
    PRIMARY KEY (fk_invitation_account, fk_invitation_book),
    FOREIGN KEY (fk_invitation_account) REFERENCES Account(id_account),
    FOREIGN KEY (fk_invitation_book) REFERENCES Book(id_book)
);
