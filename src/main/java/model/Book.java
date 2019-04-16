/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * @author raphaelcja
 */
public class Book {

    private int idBook;
    private String title;
    private boolean openToWrite;
    private boolean published;
    private Account creator;
    private boolean finished;
    private boolean canUserWrite;
    private boolean canUserRead;

    public Book(int idBook, String title, boolean openToWrite, boolean published, Account creator) {
        this.idBook = idBook;
        this.title = title;
        this.openToWrite = openToWrite;
        this.published = published;
        this.creator = creator;
    }

    public Book(String title, boolean openToWrite, boolean published, Account creator) {
        this.title = title;
        this.openToWrite = openToWrite;
        this.published = published;
        this.creator = creator;
    }

    public int getIdBook() {
        return idBook;
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
    
    
    // methods to help in the view

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isCanUserWrite() {
        return canUserWrite;
    }

    public boolean isCanUserRead() {
        return canUserRead;
    }

    public void setCanUserWrite(boolean canUserWrite) {
        this.canUserWrite = canUserWrite;
    }

    public void setCanUserRead(boolean canUserRead) {
        this.canUserRead = canUserRead;
    }
    
    public String getTitle() {
        return title;
    }

    public String getLabelTitle() {
        if (getTitle().length() > 15) {
            return getTitle().substring(0, 12) + "...";
        }
        return getTitle();
    }
    
    
    
    

    @Override
    public String toString() {
        return "Book{" + "idBook=" + idBook + ", title=" + title + ", openToWrite=" + openToWrite + ", published=" + published + ", creator=" + creator + '}';
    }


}
