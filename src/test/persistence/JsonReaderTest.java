package persistence;

import model.Account;
import model.Book;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account acc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            Account acc = reader.read();
            assertEquals("Youssef", acc.getOwnerName());
            assertEquals(0, acc.getBooks().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            Account acc = reader.read();
            assertEquals("Youssef", acc.getOwnerName());
            List<Book> books = acc.getBooks();
            assertEquals(2, books.size());
            checkBook("It", "Stephen King", Book.Genre.HORROR, true, books.get(0));
            checkBook("Harry Potter", "J.K. Rowling", Book.Genre.FANTASY, true, books.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
