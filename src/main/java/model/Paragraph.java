package model;

import java.util.List;

public class Paragraph {

    private int idParagraph;
    private String text;
    private boolean beginning;
    private boolean conclusion;
    private Book book;
    private Account author;
    private String finalText;
    private List<Choice> choices;
    private List<Choice> finalChoices;
    private boolean editable;
    private boolean choiceAddable;

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

    public void setBook(Book book) {
        this.book = book;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isChoiceAddable() {
        return choiceAddable;
    }

    public void setChoiceAddable(boolean choiceAddable) {
        this.choiceAddable = choiceAddable;
    }

    // methods to help in the view

    public String getLabelText() {
        if (getText().length() > 15) {
            return getText().substring(0, 12) + "...";
        }
        return getText();
    }

    public String getFinalText() {
        return finalText;
    }

    public void setFinalText(String finalText) {
        this.finalText = finalText;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getFinalChoices() {
        return finalChoices;
    }

    public void setFinalChoices(List<Choice> finalChoices) {
        this.finalChoices = finalChoices;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idParagraph;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Paragraph other = (Paragraph) obj;
        if (this.idParagraph != other.idParagraph) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Paragraph{" + "idParagraph=" + idParagraph + ", text=" + text +
                ", beginning=" + beginning + ", conclusion=" + conclusion +
                ", book=" + book + ", author=" + author + '}';
    }
}