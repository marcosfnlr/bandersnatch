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
    
    private final int idParagraph;
    private final String text;
    private final boolean conclusion;
    private final Book book;
    private final Account author;

    public Paragraph(int idParagraph, String text, boolean conclusion, Book book, Account author) {
        this.idParagraph = idParagraph;
        this.text = text;
        this.conclusion = conclusion;
        this.book = book;
        this.author = author;
    }

    public int getIdParagraph() {
        return idParagraph;
    }

    public String getText() {
        return text;
    }

    public boolean isConclusion() {
        return conclusion;
    }

    public Book getBook() {
        return book;
    }

    public Account getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Paragraph{" + "idParagraph=" + idParagraph + ", text=" + text + ", conclusion=" + conclusion + ", book=" + book + ", author=" + author + '}';
    }
    
    
}
