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
    published NUMBER(1) DEFAULT 0,
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
    locked NUMBER(1) DEFAULT 0,
    only_choice NUMBER(1) NOT NULL,
    cond_should_pass NUMBER(1),
    fk_parag_orig INT NOT NULL,
    fk_parag_dest INT DEFAULT NULL,
    fk_parag_cond INT DEFAULT NULL,
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

INSERT INTO Account (id_account, password, last_name, first_name) VALUES ('a','a','Amarante','Raphael');
INSERT INTO Account (id_account, password, last_name, first_name) VALUES ('b','b','Oliveira','Marcio');
INSERT INTO Account (id_account, password, last_name, first_name) VALUES ('c','c','Nunes','Marcos');
INSERT INTO Account (id_account, password, last_name, first_name) VALUES ('d','d','Meyer','Thibault');


--first book : idBook->1 ; idParag->1 to 6 ; idChoice->1 to 10
INSERT INTO Book (title, open_write, published, fk_account) VALUES ('Book1',1,1,'a');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p1b1',1,0,1,'a');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p2b1',0,0,1,'b');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p3b1',0,0,1,'c');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p4b1',0,1,1,'d');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p5b1',0,0,1,'a');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('p6b1',0,0,1,'a');
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c1p1b1',1,0,0,1,2);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c2p1b1',0,0,0,1,null);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c1p2b1',0,0,0,2,null);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c2p2b1',1,0,0,2,3);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c1p3b1',1,0,0,3,4);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c2p3b1',1,0,0,3,5);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c1p5b1',1,0,0,5,6);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c2p5b1-restart',1,0,0,5,1);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c1p6b1-restart',1,0,0,6,1);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('c2p6b1-restart',1,0,0,6,1);


--second book : idBook->2 ; idParag->7 to 11 ; idChoice->11 to 16
INSERT INTO Book (title, open_write, published, fk_account) VALUES ('Fairy Tail',1,0,'c');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('Once upon a time there was a',1,0,2,'c');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('boy who met his friends and they',0,0,2,'a');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('had lots of fun. And in the end',0,0,2,'d');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('they felt awesome',0,1,2,'d');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('they were so full they slept',0,1,2,'b');
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('boy',1,1,0,7,8);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('girl',0,0,0,7,null);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('did a web project and',1,0,0,8,9);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('did a road trip and',1,0,0,8,9);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('they high-fived and',1,0,0,9,10);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('they ate a lot and',1,0,0,9,11);


--third book : idBook->3 ; idParag->12 to 17 ; idChoice->17 to 24
INSERT INTO Book (title, open_write, published, fk_account) VALUES ('The Great Book',0,0,'b');
INSERT INTO Invitation (fk_account, fk_book) VALUES ('a',3);
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('This book is the greatest book there is.',1,0,3,'b');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('In this second chapter, this book is still great.',0,0,3,'b');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('You have only one choice here so you wont even realize there is a choice.',0,0,3,'a');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('Here you have to make an important decision.',0,0,3,'b');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('You reached the end. You are awesome!',0,1,3,'a');
INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES ('This is the early end. Goodbye.',0,1,3,'b');
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('go to first chapter',0,0,0,12,null);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('go to second chapter',1,0,0,12,13);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('go to third chapter',0,0,0,12,null);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('go to next chapter',1,0,0,13,14);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('go to early end',1,0,0,13,17);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('this is the only choice',1,1,0,14,15);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('Continue to ending.',1,0,0,15,16);
INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, fk_parag_dest) VALUES ('Go back to previous chapter.',1,0,0,15,12);
