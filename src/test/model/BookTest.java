package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    private Book testBook;

    @BeforeEach
    void runBefore() {
        testBook = new Book("It", "Stephen King", Book.Genre.HORROR);
    }

    @Test
    void testGetTitleAndAuthor() {
        assertEquals("It | Stephen King", testBook.getTitleAndAuthor());
    }

    @Test
    void testSetBorrowed() {
        assertFalse(testBook.isBorrowed());
        testBook.setBorrowed();
        assertTrue(testBook.isBorrowed());
        testBook.setBorrowed();
        assertTrue(testBook.isBorrowed());
    }

    @Test
    void testSetAvailable() {
        testBook.setBorrowed();
        assertTrue(testBook.isBorrowed());
        testBook.setAvailable();
        assertFalse(testBook.isBorrowed());
        testBook.setAvailable();
        assertFalse(testBook.isBorrowed());
    }

    @Test
    void testSetRating() {
        assertEquals(0, testBook.getRating());
        testBook.setRating(8);
        assertEquals(8, testBook.getRating());
    }

    @Test
    void testGetGenreSymbol() {
        assertEquals("h", testBook.getGenreSymbol());
    }


}
