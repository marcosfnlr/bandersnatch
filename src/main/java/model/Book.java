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
    private final User creator;
    private final Paragraph firstParagraph;

    public Book(int idBook, String title, boolean openToWrite, boolean published, User creator, Paragraph firstParagraph) {
        this.idBook = idBook;
        this.title = title;
        this.openToWrite = openToWrite;
        this.published = published;
        this.creator = creator;
        this.firstParagraph = firstParagraph;
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

    public User getCreator() {
        return creator;
    }

    public Paragraph getFirstParagraph() {
        return firstParagraph;
    }

    @Override
    public String toString() {
        return "Book{" + "idBook=" + idBook + ", title=" + title + ", openToWrite=" + openToWrite + ", published=" + published + ", creator=" + creator + ", fistParagraph=" + firstParagraph +'}';
    }
    
    
}
