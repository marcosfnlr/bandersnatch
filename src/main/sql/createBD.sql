/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  raphaelcja
 * Created: 28-Mar-2019
 */

DROP TABLE Account;

CREATE TABLE Account(
    login varchar2(10) PRIMARY KEY,
    password varchar2(100) NOT NULL,
    nom varchar2(100) NOT NULL,
    prenom varchar2(50) NOT NULL
);
INSERT INTO Account VALUES ('toto', 'toto', 'Hey', 'Ho');
INSERT INTO Account VALUES ('titi', 'titi', 'Hi', 'Ha');
