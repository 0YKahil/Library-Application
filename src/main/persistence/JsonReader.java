package persistence;

import model.Account;
import model.Book;
import model.Event;
import model.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.LibraryApp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Account from JSON data stored in file
public class JsonReader {
    private  String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: acc
    // EFFECTS: parses Books from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Account acc = new Account(name);
        addBooks(acc, jsonObject);
        return acc;

    }

    // MODIFIES: acc
    // EFFECTS: parses books from JSON object and adds them to account
    private void addBooks(Account acc, JSONObject jsonObject) {
        EventLog.getInstance().logEvent(new Event("Loaded saved books to account"));
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(acc, nextBook);
        }
    }

    // MODIFIES: acc
    // EFFECTS: parses book from JSON object and adds it to account
    private void addBook(Account acc, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        int rating = jsonObject.getInt("rating");
        Book.Genre genre = Book.Genre.valueOf(jsonObject.getString("genre"));
        Book book = new Book(title, author, genre);
        book.setBorrowed();
        book.setRating(rating);
        acc.unrestrictedAddBook(book);
    }

}
