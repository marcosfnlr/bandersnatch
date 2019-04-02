/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raphaelcja
 */
public class Book {
    
    private final int idBook;
    private final String title;
    private final boolean open;
    private final boolean published;
    private final String creator;
    private List<String> authors = new ArrayList<String>();

    public Book(int idBook, String title, boolean open, boolean published, String creator) {
        this.idBook = idBook;
        this.title = title;
        this.open = open;
        this.published = published;
        this.creator = creator;
        authors.add(creator);
    }

    public int getIdBook() {
        return idBook;
    }

    public String getTitle() {
        return title;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isPublished() {
        return published;
    }

    public String getCreator() {
        return creator;
    }

    public List<String> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Book{" + "idBook=" + idBook + ", title=" + title + ", open=" + open + ", published=" + published + ", creator=" + creator + ", authors=" + authors + '}';
    }
    
    
}
