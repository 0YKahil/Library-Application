package ui;

import model.Account;
import model.Book;
import model.Library;
import persistence.JsonWriter;
import persistence.JsonReader;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Library Application
public class LibraryApp extends JFrame {
    private static final String JSON_STORE = "./data/account.json";
    private Library lib;
    private Account acc;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the library application
    public LibraryApp() throws FileNotFoundException {
        runLibrary();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runLibrary() {
        boolean running = true;
        String command = null;

        init();

        while (running) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                running = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for using our library!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            doDonateBook();
        } else if (command.equals("c")) {
            doViewCatalogue();
        } else if (command.equals("r")) {
            doReturnBook();
        } else if (command.equals("s")) {
            saveAccount();
        } else if (command.equals("l")) {
            loadAccount();
            assignBorrowed();
        } else {
            System.out.println("Selection is not valid...");
        }
    }

    // MODIFIES: this.lib
    // EFFECTS: finds what books are on acc and sets them as borrowed in lib
    private void assignBorrowed() {
        List<Book> borrowed = acc.getBooks();
        for (Book b: borrowed) {
            for (Book l: lib.getLibrary()) {
                if (b.getTitle().equals(l.getTitle())) {
                    l.setBorrowed();
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the library and account with books
    private void init() {
        initJson();
        initSources();
        Book b1 = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", Book.Genre.FANTASY);
        Book b2 = new Book("It", "Stephen King", Book.Genre.HORROR);
        Book b3 = new Book("And Then There Were None", "Agatha Christie", Book.Genre.MYSTERY);
        Book b4 = new Book("Percy Jackson and the Olympians: The Lightning thief", "Rick Riordan",
                Book.Genre.FANTASY);
        Book b5 = new Book("Discrete Mathematics with Applications", "Susanna S. Epp",
                Book.Genre.NON_FICTION);
        Book b6 = new Book("Sapiens: A Brief History of Humankind", "Yuval Noah Harari",
                Book.Genre.NON_FICTION);
        Book b7 = new Book("Frankenstein", "Mary Shelley", Book.Genre.SCI_FI);
        Book b8 = new Book("Pride and Prejudice", "Jane Austen", Book.Genre.ROMANCE);
        Book b9 = new Book("The Hobbit", "J. R. R. Tolkien", Book.Genre.FANTASY);
        Book b10 = new Book("The Lord of the Rings", "J. R. R. Tolkien", Book.Genre.FANTASY);
        Book b11 = new Book("Dracula", "Bram Stoker", Book.Genre.HORROR);
        Book b12 = new Book("The Shining", "Stephen King", Book.Genre.HORROR);
        Book b13 = new Book("Gone Girl", "Gillian Flynn", Book.Genre.MYSTERY);
        Book b14 = new Book("Murder on the Orient Express", "Agatha Christie", Book.Genre.MYSTERY);
        Book b15 = new Book("The Love Hypothesis", "Ali Hazelwood", Book.Genre.ROMANCE);
        lib.addBooks(b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> View Catalogue");
        System.out.println("\td -> Donate a Book");
        System.out.println("\tr -> View owned books/Return a Book");
        System.out.println("\n\ts -> save account to file");
        System.out.println("\tl -> load account from file\n");
        System.out.println("\tq -> Quit application");

    }

    // EFFECTS: displays the catalogue of books in the library with option to filter
    private void doViewCatalogue() {
        System.out.println("\nChoose a filter: ");
        List<String> filtered = chooseFilter();
        if (filtered.size() == 0) {
            System.out.println("\nUnfortunately there are no books available in this filter");
        } else {
            int index = 0;
            index = displayTitles(filtered, index);
            System.out.println("\nEnter the number of the book you wish to Borrow (or 0 to exit): ");
            try {
                int selection = input.nextInt();
                chooseBook(filtered, index, selection);
            } catch (InputMismatchException e) {
                // pass
            } finally {
                System.out.println("\n\nReturning to menu...");
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: starts a book return process
    private void doReturnBook() {
        List<String> owned = acc.getTitles();
        if (owned.size() == 0) {
            System.out.println("\nYour account does not own any books");
        } else {
            int index = 0;
            index = displayTitles(owned, index);
            System.out.println("Enter the number of the book you wish to return (or 0 to exit): ");
            try {
                int selection = input.nextInt();
                chooseReturn(owned, index, selection);
            } catch (InputMismatchException e) {
                // pass
            } finally {
                System.out.println("Returning to menu...");
            }

        }
    }


    // MODIFIES: this
    // EFFECTS: starts a book donation process
    private void doDonateBook() {
        System.out.println("\nThank You for choosing to donate a book!");
        System.out.println("\nPlease enter the title of the book: ");
        String name = input.next();
        System.out.println("\nPlease enter the Author of the book (e.g. Rick Riordan): ");
        String author = input.next();
        System.out.println("\nPlease enter the genre of the book. ONE OF: \n h -> Horror \n f -> Fantasy \n s -> Sci Fi"
                + "\n n -> Non-Fiction \n m -> Mystery \n r -> romance \n o -> other.");
        Book.Genre genre = selectGenre();
        Book donated = new Book(name, author, genre);
        lib.donateBook(donated);
        System.out.println("\nBook successfully donated!");
        System.out.println("\nThank you for donating to the library!");

    }

    // helpers

    // EFFECTS: adds book to acc and sets it as borrowed
    private void borrowBook(Book book) {
        acc.addBook(book);
        book.setBorrowed();
        System.out.println("Enjoy reading " + book.getTitle() + " By: " + book.getAuthor());
    }

    // EFFECTS: prompts the user to select a filter and returns the filtered library
    private List<String> chooseFilter() {
        String selection = "";
        List<String> filtered = lib.fullCatalogue();
        while (!(selection.equals("a") || selection.equals("v") || selection.equals("g") || selection.equals("ga"))) {
            System.out.println("\na for all books");
            System.out.println("\nv for only available books");
            System.out.println("\ng for books in specific genre");
            System.out.println("\nga for available books in specific genre");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("v")) {
            filtered = lib.availableCatalogue();
        } else if (selection.equals("g")) {
            Book.Genre genre = selectGenre();
            filtered = lib.catalogueByGenre(genre);

        } else if (selection.equals("ga")) {
            Book.Genre genre = selectGenre();
            filtered = lib.availableCatalogueByGenre(genre);

        } else {
            filtered = lib.fullCatalogue();
        }
        return filtered;
    }

    // EFFECTS: saves the account to file
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(acc);
            jsonWriter.close();
            System.out.println("Saved " + acc.getOwnerName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account from file
    private void loadAccount() {
        try {
            acc = jsonReader.read();
            System.out.println("Loaded " + acc.getOwnerName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // EFFECTS: prompts the user to select a genre and returns it as a Genre type
    private Book.Genre selectGenre() {
        String selection = "";
        Book.Genre genre = null;
        System.out.println("\nPlease enter the genre of the book. ONE OF: \n h -> Horror \n f -> Fantasy \n s -> Sci Fi"
                + "\n n -> Non-Fiction \n m -> Mystery \n r -> romance \n o -> other.");
        while (!(selection.equals("h") || selection.equals("f") || selection.equals("s") || selection.equals("n")
                || selection.equals("m") || selection.equals("r") || selection.equals("o"))) {
            String entry = input.next();
            entry = entry.toLowerCase();
            if (entry.equals("h") || entry.equals("f") || entry.equals("s") || entry.equals("n")
                    || entry.equals("m") || entry.equals("r") || entry.equals("o")) {

                selection = entry;
            } else {
                System.out.println("invalid option");
                ;
            }
        }
        genre = categorizeGenre(selection);
        return genre;
    }

    // EFFECTS: chooses a book to return given a catalogue of books, a base index, and selection
    private void chooseReturn(List<String> lob, int index, int selection) {
        if (selection >= 1 && selection <= index) {
            String bookName = lob.get(selection - 1);
            acc.removeBook(acc.getBooks().get(selection - 1));
            findBookByTitle(bookName, lib.getLibrary()).setAvailable();
            System.out.println("Thank You for returning " + bookName);
        } else if (selection == 0) {
            System.out.println("\nCancelling return...");
        } else {
            System.out.println("\nNot a valid input");
        }

    }

    private void chooseBook(List<String> lob, int index, int selection) {
        if (selection >= 1 && selection <= index) {
            Book book = titleToBook(lob.get(selection - 1), lib.getLibrary());
            if (!(book.isBorrowed())) {
                borrowBook(book);
            } else {
                System.out.println("\n\nSorry this Book is not Currently available"
                        + " (try filtering by available books)");
            }
        } else if (selection == 0) {
            System.out.println("\nCancelling borrow...");
        } else {
            System.out.println("\nNot a valid input");
        }
    }



    // REQUIRES: input needs to be one of "h", "f", "s", "n", "n", "o".
    // EFFECTS: takes a letter code and returns the genre linked to that letter
    public Book.Genre categorizeGenre(String input) {
        if (input.equals("h")) {
            return Book.Genre.HORROR;
        } else if (input.equals("f")) {
            return Book.Genre.FANTASY;
        } else if (input.equals("s")) {
            return Book.Genre.SCI_FI;
        } else if (input.equals("n")) {
            return Book.Genre.NON_FICTION;
        } else if (input.equals("m")) {
            return Book.Genre.MYSTERY;
        } else if (input.equals("o")) {
            return Book.Genre.OTHER;
        } else if (input.equals("r")) {
            return Book.Genre.ROMANCE;
        }
        return null;
    }

    // REQUIRES: title should be in the exact form "BOOK TITLE | AUTHOR" and needs to be in lob
    // EFFECTS: finds a book given the title and stop
    private Book titleToBook(String title, List<Book> lob) {
        title = title.toLowerCase();
        Book book = null;
        boolean found = false;
        for (Book b: lob) {
            if (title.equals(b.getTitleAndAuthor().toLowerCase())) {
                book = b;
                found = true;
            }

        }
        return book;
    }

    // EFFECTS: finds the book with the given title and returns it
    private Book findBookByTitle(String title, List<Book> lib) {
        for (Book b: lib) {
            if (title.equals(b.getTitle())) {
                return b;
            }
        }
        return null;
    }

    // EFFECTS: displays a numbered list of los and returns index
    private int displayTitles(List<String> los, int index) {
        for (String s: los) {
            index++;
            System.out.println(index + ". " + s + "\n");
        }
        return index;
    }

    // EFFECTS: initializes jsonWriter and jsonReader to directory JSON_STORE
    private void initJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: initializes acc and lib
    private void initSources() {
        acc = new Account("User");
        lib = new Library();
    }
}
