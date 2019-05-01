/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  raphaelcja
 * Created: 28-Mar-2019
 */


SELECT * FROM Account;
SELECT * FROM Book;
SELECT * FROM Paragraph;
SELECT * FROM Choice;

INSERT INTO Account (id_account, password, last_name, first_name) VALUES ('a','a','a','a');
INSERT INTO Account (id_account, password, last_name, first_name) VALUES ('b','b','b','b');
INSERT INTO Account (id_account, password, last_name, first_name) VALUES ('c','c','c','c');

INSERT INTO Book (title, open_write, published, fk_account) VALUES ('h1',1,0,'a');
INSERT INTO Book (title, open_write, published, fk_account) VALUES ('h2',1,0,'b');
INSERT INTO Book (title, open_write, published, fk_account) VALUES ('h3',1,0,'b');
INSERT INTO Book (title, open_write, published, fk_account) VALUES ('h4',1,0,'b');

INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p1h1',1,0,1,'a');

INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p1h2',1,0,2,'b');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p2h2',0,0,2,'a');

INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p1h3',1,0,3,'b'); --4
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p2h3',0,0,3,'a'); --5
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p3h3',0,0,3,'a'); --6
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p4h3',0,1,3,'a'); --7
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p5h3',0,0,3,'b'); --8

INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p1h4',1,0,4,'b');

/*text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest, fk_parag_cond*/
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c1p1h3',0,0,0,4,5);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig) VALUES ('c2p1h3',0,0,0,4);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c1p2h3',0,0,0,5,6);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c2p2h3',0,0,0,5,6);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c1p3h3',0,0,0,6,7);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c2p3h3',0,0,0,6,8);

INSERT INTO Invitation (fk_account, fk_book) VALUES ('a',4);

SELECT * FROM Book WHERE id_book IN (SELECT DISTINCT fk_book FROM Paragraph WHERE fk_account='a');
SELECT * FROM Book WHERE id_book IN (SELECT DISTINCT fk_book FROM Invitation WHERE fk_account='a');

SELECT * FROM Choice WHERE fk_parag_orig IN (SELECT id_paragraph FROM Paragraph WHERE fk_book=3);

SELECT COUNT (id_paragraph) AS conclusions FROM Paragraph WHERE conclusion=1 AND fk_book=3;

SELECT * FROM Account WHERE id_account IN (SELECT DISTINCT fk_account FROM Paragraph WHERE fk_book=3);