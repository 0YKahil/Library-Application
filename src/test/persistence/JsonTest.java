package persistence;

import model.Book;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkBook(String name, String author, Book.Genre genre, boolean borrowed, Book book) {
        assertEquals(name, book.getTitle());
        assertEquals(genre, book.getGenre());
        assertEquals(borrowed, book.isBorrowed());
    }
}
