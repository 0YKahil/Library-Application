package ui;

import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.EventLog;
import persistence.*;
import model.Account;
import model.Library;

// Library Gui with working library system

public class LibraryUI extends JFrame {
    private static final String JSON_STORE = "./data/account.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JButton viewFullCatalogueButton;
    private JButton viewAvailableCatalogueButton;
    private JTextField authorField;
    private JTextField nameField;
    private JComboBox comboBox1;
    private JButton donateButton;
    private JButton accButton;
    private JButton imageButton;
    private JButton saveButton;
    private JLabel welcomeLabel;
    private Library lib;
    private Account acc;
    private ImageIcon image;


    // EFFECTS: Constructs a Library GUI
    public LibraryUI() {
        lib = new Library();
        acc = new Account("User");

        init();

        setContentPane(mainPanel);
        setTitle("00 Library");
        setSize(900, 960);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        promptLoad();
        setupButtons();
        windowListenerSetup();

    }

    // MODIFIES: this, acc, lib
    // EFFECTS: initializes all the buttons and sets them up with their actions
    private void setupButtons() {
        donateTabSetup();
        returnBookSetup();
        viewFullCatalogueSetup();
        viewAvailableCatalogueSetup();
        imageButtonSetup();
        saveButtonSetup();
    }

    // EFFECTS: sets up image button
    private void imageButtonSetup() {
        imageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame infoFrame = new JFrame("Info");
                infoFrame.setSize(510, 480);
                infoFrame.setLayout(new BorderLayout());
                infoFrame.setVisible(true);

                image = new ImageIcon("./data/library_icon.png");
                JLabel imageLabel = new JLabel(image);

                JPanel infoPanel = new JPanel();
                JLabel info = new JLabel("00 Library | info@olib.ca | XXX-XXX-XXXX");
                infoPanel.add(info);

                infoFrame.add(imageLabel);
                infoFrame.add(infoPanel, BorderLayout.SOUTH);
            }
        });
    }

    // MODIFIES: this, acc, lib
    // EFFECTS: sets up the save button
    private void saveButtonSetup() {
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAccount();
            }
        });
    }

    // EFFECTS: saves the library and accounts state to file
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(acc);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this.mainPanel, "Saved " + acc.getOwnerName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this.mainPanel, "Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, acc
    private void loadAccount() {
        try {
            acc = jsonReader.read();
            JOptionPane.showMessageDialog(this.mainPanel, "Loaded " + acc.getOwnerName() + " from "
                    + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this.mainPanel, "Unable to read from file: " + JSON_STORE);
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

    // MODIFIES: this, lib
    // EFFECTS: sets up the donate tab
    private void donateTabSetup() {
        donateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doDonate();
            }
        });

    }

    // EFFECTS: sets up the return book button
    private void returnBookSetup() {
        accButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ownedTitles = acc.getTitles().toString();
                if (!ownedTitles.equals("[]")) {
                    setupReturnWindow();
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "No books to return.");
                }

            }
        });
    }

    // MODIFIES: acc, lib
    // EFFECTS: sets up the Available books buttons and functionality
    private void viewAvailableCatalogueSetup() {
        viewAvailableCatalogueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setupBorrowWindow();
            }
        });
    }

    // MODIFIES: acc, lib
    // EFFECTS: sets up the Return book window and functionality
    private void setupReturnWindow() {
        JFrame window = new JFrame();
        window.setTitle("Return Books");
        window.setSize(400, 500);
        window.setLayout(new BorderLayout());
        window.setVisible(true);

        JPanel menuPanel = new JPanel();
        List<JCheckBox> items = generateCheckBoxItems(acc.getTitlesAndAuthors());

        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        for (JCheckBox i : items) {
            menuPanel.add(i);
        }

        JPanel buttonPanel = new JPanel();
        JButton select = new JButton("Select Book(s) to return");

        buttonPanel.add(select);

        JScrollPane scrollPane = setupScrollPane(menuPanel, 400, 400);
        window.add(scrollPane, BorderLayout.NORTH);
        window.add(buttonPanel, BorderLayout.SOUTH);

        select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doReturnBook(window, items);
            }
        });

    }

    private void windowListenerSetup() {
        this.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                ConsolePrinter printer = new ConsolePrinter();
                printer.printLog(EventLog.getInstance());
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Ended");
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    // MODIFIES: acc, lib
    // EFFECTS: sets up the Borrow book window and functionality
    private void setupBorrowWindow() {
        JFrame window = new JFrame("Available Books");
        window.setSize(600, 800);
        window.setLayout(new BorderLayout());
        window.setVisible(true);

        JPanel menuPanel = new JPanel();
        List<JCheckBox> items = generateCheckBoxItems(lib.availableCatalogue());

        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        for (JCheckBox i : items) {
            menuPanel.add(i);
        }

        JPanel buttonPanel = new JPanel();
        JButton select = new JButton("Select Book(s)");

        buttonPanel.add(select);

        JScrollPane scrollPane = setupScrollPane(menuPanel, 400, 400);

        window.add(scrollPane, BorderLayout.NORTH);
        window.add(buttonPanel, BorderLayout.SOUTH);

        select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doBorrowBook(window, items);
            }
        });
    }

    // EFFECTS: creates a scrollable menu from a panel with given width and height
    private JScrollPane setupScrollPane(JPanel panel, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(width, height));
        return scrollPane;

    }

    // MODIFIES: this, lib, book
    // EFFECTS: Identifies selected books to borrow and adds them to acc
    private void doBorrowBook(JFrame window, List<JCheckBox> items) {
        List<String> selected = new ArrayList<>();
        boolean state = false;
        for (JCheckBox item : items) {
            if (item.isSelected()) {
                selected.add(item.getText());
                if (!state) {
                    state = true;
                }
            }
        }
        if (!state) {
            JOptionPane.showMessageDialog(window, "No books selected, Return to menu");
            window.setVisible(false);
        }
        List<Book> toBorrow = new ArrayList<>();
        for (String s : selected) {
            Book book = titleToBook(s, lib.getLibrary());
            acc.addBook(book);
            book.setBorrowed();

            toBorrow.add(book);
        }
        doBookMessage(window, toBorrow, "added to your account");
        window.setVisible(false);
    }

    // MODIFIES: this, acc, book
    // EFFECTS: Identifies selected books to return and returns them to library from account
    private void doReturnBook(JFrame window, List<JCheckBox> items) {
        List<String> selected = new ArrayList<>();
        boolean state = false;
        for (JCheckBox item : items) {
            if (item.isSelected()) {
                selected.add(item.getText());
                if (!state) {
                    state = true;
                }
            }
        }
        if (!state) {
            JOptionPane.showMessageDialog(window, "No books selected, Return to menu");
            window.setVisible(false);
        }

        List<Book> toReturn = new ArrayList<>();
        for (String s : selected) {
            Book book = titleToBook(s, acc.getBooks());
            acc.removeBook(book);
            findBookByTitle(s, lib.getLibrary()).setAvailable();
            toReturn.add(book);
        }
        doBookMessage(window, toReturn, "removed from your account");
        window.setVisible(false);

    }

    // EFFECTS: finds the book with the given title and returns it
    private Book findBookByTitle(String title, List<Book> lib) {
        for (Book b: lib) {
            if (title.equals(b.getTitleAndAuthor())) {
                return b;
            }
        }
        return null;
    }


    // EFFECTS: creates a pop-up message of the names of the books of interest acted on with a message of your choice
    private void doBookMessage(JFrame window, List<Book> booksOfInterest, String actionMessage) {
        List<String> toActionNames = new ArrayList<>();
        for (Book b : booksOfInterest) {
            toActionNames.add(b.getTitle());
        }
        if (!toActionNames.toString().equals("[]")) {

            JOptionPane.showMessageDialog(window, toActionNames.toString()
                    + " " + actionMessage);
        }
    }

    // MODIFIES: this, lib
    // EFFECTS: obtains text from donate panel and creates a book from it and adds it to the library
    private void doDonate() {
        String name = nameField.getText();
        String author = authorField.getText();
        String selectedItem = (String) comboBox1.getSelectedItem();
        try {
            assert selectedItem != null;
            Book.Genre genre = convertToGenre(selectedItem);
            JOptionPane.showMessageDialog(mainPanel, "Added " + name + " by " + author
                    + " to " + genre.toString());
            lib.donateBook(new Book(name, author, genre));
        } catch (NullPointerException n) {
            JOptionPane.showMessageDialog(mainPanel, "Please input the Book name, Author,"
                    + "and select the genre");
        }
    }

    // EFFECTS: sets up the view all books button to show a text menu of all the books (available or not)
    private void viewFullCatalogueSetup() {
        viewFullCatalogueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame window = new JFrame("All Books");
                window.setSize(600, 800);
                window.setLayout(new BorderLayout());
                window.setVisible(true);

                JPanel menuPanel = new JPanel();
                List<JLabel> items = generateItemsViewOnly(lib.fullCatalogue());

                menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
                for (JLabel i : items) {
                    menuPanel.add(i);
                }

                JPanel buttonPanel = new JPanel();
                JButton select = new JButton("Select Book(s)");

                JScrollPane scrollPane = new JScrollPane(menuPanel);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setPreferredSize(new Dimension(400,600));

                window.add(scrollPane, BorderLayout.NORTH);
                window.add(buttonPanel, BorderLayout.SOUTH);

            }
        });
    }

    // EFFECTS: generates a list of checkbox objects from a given list of string prompts
    private List<JCheckBox> generateCheckBoxItems(List<String> prompts) {
        List<JCheckBox> items = new ArrayList<>();
        for (String b : prompts) {
            items.add(new JCheckBox(b));
        }
        return items;


    }

    // EFFECTS: generates a list of label objects from a given list of string prompts
    private List<JLabel> generateItemsViewOnly(List<String> prompts) {
        List<JLabel> items = new ArrayList<>();
        for (String b : prompts) {
            items.add(new JLabel(b));
        }
        return items;
    }

    // REQUIRES: title should be in the exact form "BOOK TITLE | AUTHOR" and needs to be in lob
    // EFFECTS: finds a book id given the title and stop
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

    // REQUIRES: input MUST be one of HORROR, FANTASY, SCI-FI, NON-FICTION, MYSTERY, ROMANCE, OTHER.
    // EFFECTS: categorizes given text genre into corresponding enumeration and returns it
    public Book.Genre convertToGenre(String input) {
        if (input.equals("HORROR")) {
            return Book.Genre.HORROR;
        } else if (input.equals("FANTASY")) {
            return Book.Genre.FANTASY;
        } else if (input.equals("SCI-FI")) {
            return Book.Genre.SCI_FI;
        } else if (input.equals("NON-FICTION")) {
            return Book.Genre.NON_FICTION;
        } else if (input.equals("MYSTERY")) {
            return Book.Genre.MYSTERY;
        } else if (input.equals("OTHER")) {
            return Book.Genre.OTHER;
        } else if (input.equals("ROMANCE")) {
            return Book.Genre.ROMANCE;
        } else {
            return null;
        }
    }

    // EFFECTS: initializes jsonWriter and jsonReader to directory JSON_STORE
    private void initJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initializes library with books and Json writers and readers
    private void init() {
        initJson();
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

    }

    private void promptLoad() {
        int result = JOptionPane.showConfirmDialog(this,
                "Would you like to check for a saved state and load it?", "00 Library: Load Account?",
                 JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            loadAccount();
            assignBorrowed();
        }

    }

}
