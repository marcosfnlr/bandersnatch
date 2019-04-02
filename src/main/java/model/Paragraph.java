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
public class Paragraph {
    
    private final int idParag;
    private final String text;
    private final boolean conclusion;
    private final int idBook;
    private final String author;

    public Paragraph(int idParag, String text, boolean conclusion, int idBook, String author) {
        this.idParag = idParag;
        this.text = text;
        this.conclusion = conclusion;
        this.idBook = idBook;
        this.author = author;
    }

    public int getIdParag() {
        return idParag;
    }

    public String getText() {
        return text;
    }

    public boolean isConclusion() {
        return conclusion;
    }

    public int getIdBook() {
        return idBook;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Paragraph{" + "idParag=" + idParag + ", text=" + text + ", conclusion=" + conclusion + ", idBook=" + idBook + ", author=" + author + '}';
    }
    
    
}
