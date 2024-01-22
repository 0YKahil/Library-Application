package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibraryTest {
    private Library testLibrary;
    private Book b1;
    private Book b2;
    private Book b3;
    private Book b4;
    
    @BeforeEach
    void runBefore() {
        b1 = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", Book.Genre.FANTASY);
        b2 = new Book("It", "Stephen King", Book.Genre.HORROR);
        b3 = new Book("And Then There Were None", "Agatha Christie", Book.Genre.MYSTERY);
        b4 = new Book("Percy Jackson and the Olympians: The Lightning thief", "Rick Riordan", Book.Genre.FANTASY);

        testLibrary = new Library();
    }
    
    @Test
    void testConstructor() {
        assertEquals(0, testLibrary.getLibrary().size());
        testLibrary.donateBook(b1);
        assertEquals(1, testLibrary.getLibrary().size());
        testLibrary.donateBook(b2);
        assertEquals(2, testLibrary.getLibrary().size());
    }

    @Test
    void testDonateBookOnce() {
        assertEquals(0, testLibrary.getLibrary().size());
        testLibrary.donateBook(b1);
        assertEquals(1, testLibrary.getLibrary().size());
    }

    @Test
    void testDonateBookMultiple() {
        testLibrary.donateBook(b1);
        testLibrary.donateBook(b2);
        testLibrary.donateBook(b3);
        assertEquals(3, testLibrary.getLibrary().size());
    }

    @Test
    void testRemoveOneBook() {
        testLibrary.donateBook(b1);
        assertEquals(1, testLibrary.getLibrary().size());
        testLibrary.removeBook(b1);
        assertEquals(0, testLibrary.getLibrary().size());
    }
    
    @Test
    void testRemoveMultipleBooks() {
        testLibrary.donateBook(b1);
        testLibrary.donateBook(b3);
        testLibrary.donateBook(b2);
        assertEquals(3, testLibrary.getLibrary().size());
        testLibrary.removeBook(b1);
        assertEquals(2, testLibrary.getLibrary().size());
        assertEquals("And Then There Were None", (testLibrary.getLibrary().get(0).getTitle()));
        testLibrary.removeBook(b3);
        assertEquals(1, testLibrary.getLibrary().size());
        assertEquals("It", (testLibrary.getLibrary().get(0).getTitle()));
    }

    @Test
    void testAddBooksOneBook() {
        testLibrary.addBooks(b1);
        assertEquals(1, testLibrary.getLibrary().size());
    }

    @Test
    void testAddBooksMultipleBooks() {
        testLibrary.addBooks(b1, b2, b3);
        assertEquals(3, testLibrary.getLibrary().size());
    }


    @Test
    void testFullCatalogue() {
        List<String> catalogue = testLibrary.fullCatalogue();
        assertEquals(0, catalogue.size());
        testLibrary.donateBook(b1);
        catalogue = testLibrary.fullCatalogue();
        assertEquals(1, catalogue.size());
        assertEquals("Harry Potter and the Sorcerer's Stone | J.K. Rowling", catalogue.get(0));
        testLibrary.donateBook(b2);
        testLibrary.donateBook(b3);
        catalogue = testLibrary.fullCatalogue();
        assertEquals(3, catalogue.size());
        assertEquals("Harry Potter and the Sorcerer's Stone | J.K. Rowling", catalogue.get(0));
        assertEquals("It | Stephen King", catalogue.get(1));
        assertEquals("And Then There Were None | Agatha Christie", catalogue.get(2));


    }
    @Test
    void testAvailableCatalogueNoAvailable() {
        List<String> catalogue;
        b1.setBorrowed();
        testLibrary.donateBook(b1);
        catalogue = testLibrary.availableCatalogue();
        assertEquals(0, catalogue.size());
    }

    @Test
    void testAvailableCatalogueMultipleAvailable() {
        List<String> catalogue;
        b1.setBorrowed();
        testLibrary.donateBook(b1);
        testLibrary.donateBook(b2);
        testLibrary.donateBook(b3);
        catalogue = testLibrary.availableCatalogue();
        assertEquals(2, catalogue.size());
        assertEquals("It | Stephen King", catalogue.get(0));
        assertEquals("And Then There Were None | Agatha Christie", catalogue.get(1));
        b2.setBorrowed();
        catalogue = testLibrary.availableCatalogue();
        assertEquals(1, catalogue.size());
    }

    @Test
    void testCatalogueByGenreOneBook() {
        List<String> catalogue;
        testLibrary.donateBook(b1);
        catalogue = testLibrary.catalogueByGenre(Book.Genre.NON_FICTION);
        assertEquals(0, catalogue.size());
        catalogue = testLibrary.catalogueByGenre(Book.Genre.FANTASY);
        assertEquals(1, catalogue.size());
        assertEquals("Harry Potter and the Sorcerer's Stone | J.K. Rowling", catalogue.get(0));
    }

    @Test
    void testCatalogueByGenreMultipleBooks() {
        List<String> catalogue;
        testLibrary.donateBook(b1);
        testLibrary.donateBook(b2);
        testLibrary.donateBook(b3);
        testLibrary.donateBook(b4);
        catalogue = testLibrary.catalogueByGenre(Book.Genre.HORROR);
        assertEquals(1, catalogue.size());
        assertEquals("It | Stephen King", catalogue.get(0));
        catalogue = testLibrary.catalogueByGenre(Book.Genre.FANTASY);
        assertEquals(2, catalogue.size());
        assertEquals("Harry Potter and the Sorcerer's Stone | J.K. Rowling", catalogue.get(0));
        assertEquals("Percy Jackson and the Olympians: The Lightning thief | Rick Riordan", catalogue.get(1));
    }

    @Test
    void testAvailableCatalogueByGenre() {
        List<String> catalogue;
        testLibrary.donateBook(b1);
        testLibrary.donateBook(b2);
        testLibrary.donateBook(b3);
        testLibrary.donateBook(b4);
        b4.setBorrowed();
        catalogue = testLibrary.availableCatalogueByGenre(Book.Genre.HORROR);
        assertEquals("It | Stephen King", catalogue.get(0));
        catalogue = testLibrary.availableCatalogueByGenre(Book.Genre.FANTASY);
        assertEquals(1, catalogue.size());
        assertEquals("Harry Potter and the Sorcerer's Stone | J.K. Rowling", catalogue.get(0));
    }


}
