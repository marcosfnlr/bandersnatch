package model;

public class Invitation {
    private final User user;
    private final Book book;

    public Invitation(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return "Invitation{" + "user=" + user + ", book=" + book +'}';
    }
}
