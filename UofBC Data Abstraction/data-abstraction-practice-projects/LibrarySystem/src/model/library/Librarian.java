package model.library;

import model.book.Book;

public class Librarian {

    private String name;
    private int age;
    private Library managingLibrary;
    private Book favBook;

    public Librarian(String name, int age, Library lib, Book bk) {
        //TODO: complete the specifications and implementation of this method
    }

    // getters
    public String getName() {
        //TODO: complete the specifications and implementation of this method
        return null;
    }
    public int getAge() {
        //TODO: complete the specifications and implementation of this method
        return 0;
    }
    public Library getManagingLibrary() {
        //TODO: complete the specifications and implementation of this method
        return null;
    }
    public Book getFavBook() {
        //TODO: complete the specifications and implementation of this method
        return null;
    }

    // REQUIRES: lib != null
    // MODIFIES: this
    // EFFECTS: changes this librarian's managing library to the one given.
    //          Look carefully at the implementation of library - does it
    //          also have an associated librarian field? Does it make a
    //          difference in the implementation of this method?
    public boolean changeLibrary(Library lib) {
        //TODO: complete the specifications and implementation of this method
        return false;
    }


}
