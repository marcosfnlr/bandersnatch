package model;

public class Invitation {
    private final Account account;
    private final Book book;

    public Invitation(Account account, Book book) {
        this.account = account;
        this.book = book;
    }

    public Account getAccount() {
        return account;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return "Invitation{" + "account=" + account + ", book=" + book +'}';
    }
}
