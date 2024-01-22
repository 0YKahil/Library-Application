package persistence;

import model.Account;
import model.Book;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Account acc = new Account("Youssef");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            Account acc = new Account("Youssef");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            acc = reader.read();
            assertEquals("Youssef", acc.getOwnerName());
            assertEquals(0, acc.getBooks().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccount() {
        try {
            Account acc = new Account("Youssef");
            acc.addBook(new Book("It", "Stephen King", Book.Genre.HORROR));
            acc.addBook(new Book("Harry Potter", "J.K. Rowling", Book.Genre.FANTASY));
            assertEquals(2, acc.getBooks().size());
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            acc = reader.read();
            assertEquals("Youssef", acc.getOwnerName());
            List<Book> books = acc.getBooks();
            assertEquals(2, books.size());
            checkBook("It", "Stephen King", Book.Genre.HORROR, true, books.get(0));
            checkBook("Harry Potter", "J.K. Rowling", Book.Genre.FANTASY, true,
                    acc.getBooks().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
