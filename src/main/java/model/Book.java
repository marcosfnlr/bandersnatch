package model;

public class Book {

    private int idBook;
    private String title;
    private boolean openToWrite;
    private boolean published;
    private Account creator;
    private boolean finished;
    private boolean canUserWrite;
    private boolean canUserRead;
    private boolean publishable;
    private boolean unpublishable;
    private boolean invitable;

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

    public void setPublishable(boolean publishable) {
        this.publishable = publishable;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }


    // methods to help in the view

    public boolean isPublishable() {
        return publishable;
    }

    public boolean isUnpublishable() {
        return unpublishable;
    }

    public void setUnpublishable(boolean unpublishable) {
        this.unpublishable = unpublishable;
    }

    public boolean isInvitable() {
        return invitable;
    }

    public void setInvitable(boolean invitable) {
        this.invitable = invitable;
    }

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
