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
    
    private int idParagraph;
    private String text;
    private boolean beginning;
    private boolean conclusion;
    private Book book;
    private Account author;

    public Paragraph(int idParagraph, String text, boolean beginning, boolean conclusion, Book book, Account author) {
        this.idParagraph = idParagraph;
        this.text = text;
        this.beginning = beginning;
        this.conclusion = conclusion;
        this.book = book;
        this.author = author;
    }

    public Paragraph(String text, boolean beginning, boolean conclusion, Book book, Account author) {
        this.text = text;
        this.beginning = beginning;
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
    
    public boolean isBeginning() {
        return beginning;
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
        return "Paragraph{" + "idParagraph=" + idParagraph + ", text=" + text + 
                ", beginning=" + beginning + ", conclusion=" + conclusion + 
                ", book=" + book + ", author=" + author + '}';
    }
    
    
}
