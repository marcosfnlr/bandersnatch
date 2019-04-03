package model;

import java.sql.Timestamp;

public class History {

    private final User user;
    private final Book book;
    private final Choice choice;
    private Timestamp  dateCreated;

    public History(User user, Book book, Choice choice, Timestamp  dateCreated) {
        this.user = user;
        this.book = book;
        this.choice = choice;
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
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
        return "History{" + "user=" + user + ", book=" + book + ", choice=" + choice + ", dateCreated=" + dateCreated +'}';
    }
}
