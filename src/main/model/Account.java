package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a Library Account connected to a library "card" having an owner name, and current books borrowed
// An account can only borrow one copy of a book
public class Account implements Writable {
    private String name;    // account owner's name
    private List<Book> borrowedBooks;


    // EFFECTS: Constructs an account with 0 books borrowed and an id unique to it
    public Account(String name) {
        this.borrowedBooks = new ArrayList<>();
        this.name = name;
    }


    // MODIFIES: this
    // EFFECTS: adds a book to the list of borrowed books
    public void addBook(Book book) {
        if (!book.isBorrowed()) {
            this.borrowedBooks.add(book);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a book to the list of borrowed books regardless of its borrowed state
    public void unrestrictedAddBook(Book book) {
        // NOTE: this method is only used when adding already borrowed books to an account after loading a saved state
        this.borrowedBooks.add(book);
    }

    // REQUIRES: borrowedBooks has at least one Book element in it,
    // and book is in the list
    // MODIFIES: this
    // EFFECTS: removes a book from the list
    public void removeBook(Book book) {
        this.borrowedBooks.remove(book);
    }


    // getters

    // EFFECTS: returns the name of the account owner
    public String getOwnerName() {
        return this.name;
    }

    // EFFECTS: returns the names of the books owned on this account
    public List<String> getTitles() {
        List<String> names = new ArrayList<>();
        for (Book b: this.getBooks()) {
            names.add(b.getTitle());
        }
        return names;
    }

    // EFFECTS: returns the names of the books and their authors owned on this account
    public List<String> getTitlesAndAuthors() {
        List<String> names = new ArrayList<>();
        for (Book b: this.getBooks()) {
            names.add(b.getTitleAndAuthor());
        }
        return names;
    }

    // EFFECTS: returns the list of books currently borrowed, or returns an empty list if no books are borrowed
    public List<Book> getBooks() {
        return this.borrowedBooks;
    }

    @Override
    // EFFECTS: convert account state to JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("books", booksToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : borrowedBooks) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }

}
