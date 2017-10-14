package model.book;

import model.library.Library;

public class Book {

    private String title;
    private String author;
    private BookType type;
    private int year;
    private int edition;
    private Library homeLibrary;
    private boolean isOnLoan;

    public Book(String title, String author, BookType type, int year, int ed) {
        //TODO: complete the implementation of this method
    }

    // getters
    public String getTitle() {
        //TODO: complete the implementation of this method
        return null;
    }
    public String getAuthor() {
        //TODO: complete the implementation of this method
        return null;
    }
    public BookType getType() {
        //TODO: complete the implementation of this method
        return null;
    }
    public int getYear() {
        //TODO: complete the implementation of this method
        return 0;
    }
    public int getEdition() {
        //TODO: complete the implementation of this method
        return 0;
    }
    public Library getHomeLibrary() {
        //TODO: complete the implementation of this method
        return null;
    }

    // MODIFIES: this
    // EFFECTS: set the book to be not on loan
    public void notOnLoan() { this.isOnLoan = false; }

    // EFFECTS: return true if this book is on loan, else return false
    public boolean onLoan() {
        //TODO: complete the implementation of this method
        return false;
    }

    // MODIFIES: this
    // EFFECTS: set the book to be on loan
    public void nowOnLoan() {
        //TODO: complete the implementation of this method
    }

    // REQUIRES: home != null
    // MODIFIES: this
    // EFFECTS: sets this book's home library to the one passed as parameter
    public void setHomeLibrary(Library home) {
        //TODO: complete the implementation of this method
    }


}