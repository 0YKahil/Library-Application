package model;


import org.json.JSONObject;
import persistence.JsonWriter;
import persistence.Writable;

// Represents a book with its title, author, genre, and borrow status
public class Book implements Writable {
    private String title;
    private String author;
    private Genre genre;
    private int rating;
    private boolean borrowed;

    public enum Genre {
        HORROR,
        FANTASY,
        SCI_FI,
        NON_FICTION,
        MYSTERY,
        ROMANCE,
        OTHER
    }

    // REQUIRES: genre has to be one of Genre enumeration in exact form
    // EFFECTS: constructs an available book
    public Book(String title, String author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        borrowed = false;
        rating = 0;
    }

    // getters

    // EFFECTS: returns the title of the book
    public String getTitle() {
        return this.title;
    }

    // EFFECTS: returns the author of the book
    public String getAuthor() {
        return this.author;
    }

    // EFFECTS: returns the genre of the book
    public Genre getGenre() {
        return this.genre;
    }

    // EFFECTS: returns the first letter of the genre as a char
    public String getGenreSymbol() {
        String symbol = String.valueOf(genre.name().toLowerCase().charAt(0));
        return symbol;
    }

    public String getTitleAndAuthor() {
        return (this.title + " | " + this.author);
    }

    public int getRating() {
        return this.rating;
    }

    // EFFECTS: returns true if borrowed, false otherwise
    public boolean isBorrowed() {
        return this.borrowed;
    }

    // REQUIRES: num MUST be an integer bigger than 0 and less than 11
    // MODIFIES: this
    // EFFECTS: sets a rating from 1 to 10
    public void setRating(int num) {
        this.rating = num;
    }

    //REQUIRES: Borrowed status is false
    //MODIFIES: this
    //EFFECTS: sets the borrowed status to true
    public void setBorrowed() {
        if (!(this.borrowed)) {
            this.borrowed = true;
        }
        EventLog.getInstance().logEvent(new Event("Borrowed Book: " + this.getTitleAndAuthor()));
    }

    // REQUIRES: Borrowed status is false
    // MODIFIES: this
    // EFFECTS: sets the borrowed status to true
    public void setAvailable() {
        if (this.borrowed) {
            this.borrowed = false;
        }
        EventLog.getInstance().logEvent(new Event("Returned Book: " + this.getTitleAndAuthor()));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("rating", rating);
        json.put("genre", genre);
        return json;
    }


}
