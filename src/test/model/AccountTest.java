package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account testAccount;
    private Book b1;
    private Book b2;
    private Book b3;
    @BeforeEach
    void runBefore() {
        b1 = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", Book.Genre.FANTASY);
        b2 = new Book("It", "Stephen King", Book.Genre.HORROR);
        b3 = new Book("And Then There Were None", "Agatha Christie", Book.Genre.MYSTERY);
        testAccount = new Account("Rian");
    }

    @Test
    void testConstructor() {
        assertEquals("Rian", testAccount.getOwnerName());
        assertEquals(0, testAccount.getBooks().size());

    }

    @Test
    void testAddOneBook() {
        testAccount.addBook(b1);
        assertEquals(1, testAccount.getBooks().size());
        assertEquals("Harry Potter and the Sorcerer's Stone", (testAccount.getBooks().get(0).getTitle()));
        assertEquals(Book.Genre.FANTASY, testAccount.getBooks().get(0).getGenre());
        assertEquals("J.K. Rowling", testAccount.getBooks().get(0).getAuthor());
    }
    @Test
    void testAddMultipleBooks() {
        testAccount.addBook(b1);
        testAccount.addBook(b2);
        assertEquals(2, testAccount.getBooks().size());
        assertEquals("Harry Potter and the Sorcerer's Stone", (testAccount.getBooks().get(0).getTitle()));
        assertEquals("It", (testAccount.getBooks().get(1).getTitle()));
        testAccount.addBook(b3);
        assertEquals(3, testAccount.getBooks().size());
        assertEquals("And Then There Were None", (testAccount.getBooks().get(2).getTitle()));
        assertEquals(Book.Genre.FANTASY, testAccount.getBooks().get(0).getGenre());
        assertEquals(Book.Genre.MYSTERY, testAccount.getBooks().get(2).getGenre());
    }
    @Test
    void testAddOneBookBorrowed() {
        b1.setBorrowed();
        testAccount.addBook(b1);
        assertEquals(0, testAccount.getBooks().size());
        b1.setAvailable();
        testAccount.addBook(b1);
        assertEquals(1, testAccount.getBooks().size());

    }
    @Test
    void testRemoveOneBook() {
        testAccount.addBook(b1);
        assertEquals(1, testAccount.getBooks().size());
        testAccount.removeBook(b1);
        assertEquals(0, testAccount.getBooks().size());
    }
    @Test
    void testRemoveMultipleBooks() {
        testAccount.addBook(b1);
        testAccount.addBook(b3);
        testAccount.addBook(b2);
        assertEquals(3, testAccount.getBooks().size());
        testAccount.removeBook(b1);
        assertEquals(2, testAccount.getBooks().size());
        assertEquals("And Then There Were None", (testAccount.getBooks().get(0).getTitle()));
        testAccount.removeBook(b3);
        assertEquals(1, testAccount.getBooks().size());
        assertEquals("It", (testAccount.getBooks().get(0).getTitle()));
    }

    @Test
    void testGetTitles() {
        assertEquals(0, testAccount.getTitles().size());
        testAccount.addBook(b1);
        testAccount.addBook(b3);
        testAccount.addBook(b2);
        assertEquals(3, testAccount.getTitles().size());
        assertEquals("Harry Potter and the Sorcerer's Stone", testAccount.getTitles().get(0));
    }

    @Test
    void testGetTitleAndAuthors() {
        testAccount.addBook(b1);
        testAccount.addBook(b3);
        assertEquals("Harry Potter and the Sorcerer's Stone | J.K. Rowling",
                testAccount.getTitlesAndAuthors().get(0));
    }






}