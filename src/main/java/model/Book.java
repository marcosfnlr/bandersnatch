/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author raphaelcja
 */
public class Book {
    
    private final int idBook;
    private final String title;
    private final boolean openToWrite;
    private final boolean published;
    private final Account creator;

    public Book(int idBook, String title, boolean openToWrite, boolean published, Account creator) {
        this.idBook = idBook;
        this.title = title;
        this.openToWrite = openToWrite;
        this.published = published;
        this.creator = creator;
    }

    public int getIdBook() {
        return idBook;
    }

    public String getTitle() {
        return title;
    }

    public boolean isOpenToWrite() {
        return openToWrite;
    }

    public boolean isPublished() {
        return published;
    }

    public Account getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "Book{" + "idBook=" + idBook + ", title=" + title + ", openToWrite=" + openToWrite + ", published=" + published + ", creator=" + creator +'}';
    }
    
    
}
