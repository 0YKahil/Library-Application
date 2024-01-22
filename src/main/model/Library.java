package model;

import model.Book;

import java.util.ArrayList;
import java.util.List;


// Library Represents a Library having a stock of Books
public class Library {
    private List<Book> library;

    // EFFECTS: Creates a library with an empty list of Books
    public Library() {
        this.library = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a book to the library
    public void donateBook(Book book) {
        EventLog.getInstance().logEvent(new Event("Donated Book: " + book.getTitleAndAuthor()
                + " To library"));
        this.library.add(book);
    }

    // REQUIRES: library has at least one Book element in it,
    // and book is in the list
    // MODIFIES: this
    // EFFECTS: removes a book from the list
    public void removeBook(Book book) {
        this.library.remove(book);
    }

    // REQUIRES: library has at least one Book element in it
    // EFFECTS: returns a list of the names of the books in the library
    public List<String> fullCatalogue() {
        List<String> names = new ArrayList<>();
        for (Book b: this.library) {
            names.add(b.getTitleAndAuthor());
        }
        return names;
    }

    // REQUIRES: library has at least one Book element in it
    // EFFECTS: returns a list of the names of the non borrowed books in the library
    public List<String> availableCatalogue() {
        List<String> names = new ArrayList<>();
        for (Book b: this.library) {
            if (!(b.isBorrowed())) {
                names.add(b.getTitleAndAuthor());
            }
        }
        return names;
    }

    // REQUIRES: library has at least one Book element in it
    // EFFECTS: returns a list of the names of the books in library by given genre
    public List<String> catalogueByGenre(Book.Genre genre) {
        List<String> names = new ArrayList<>();
        for (Book b: this.library) {
            if (b.getGenre() == genre) {
                names.add(b.getTitleAndAuthor());
            }
        }
        return names;
    }

    // REQUIRES: library has at least one Book element in it
    // EFFECTS: returns a list of the names of the books in library by given genre
    public List<String> availableCatalogueByGenre(Book.Genre genre) {
        List<String> names = new ArrayList<>();
        for (Book b: this.library) {
            if (b.getGenre() == genre && !(b.isBorrowed())) {
                names.add(b.getTitleAndAuthor());
            }
        }
        return names;
    }

    // MODIFIES: this
    // EFFECTS: adds variable amount of books to library
    public void addBooks(Book... books) {
        for (Book b: books) {
            this.library.add(b);
        }
        EventLog.getInstance().logEvent(new Event("Initialized books"));
    }

    // getters

    public List<Book> getLibrary() {
        return this.library;
    }




}



