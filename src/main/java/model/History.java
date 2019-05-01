package model;

import java.sql.Timestamp;

public class History {

    private final Account account;
    private final Book book;
    private final Choice choice;
    private Timestamp dateCreated;

    public History(Account account, Book book, Choice choice, Timestamp dateCreated) {
        this.account = account;
        this.book = book;
        this.choice = choice;
        this.dateCreated = dateCreated;
    }

    public Account getAccount() {
        return account;
    }

    public Book getBook() {
        return book;
    }

    public Choice getChoice() {
        return choice;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "History{" + "account=" + account + ", book=" + book + ", choice=" + choice + ", dateCreated=" + dateCreated + '}';
    }
}
