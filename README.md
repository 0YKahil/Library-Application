# Personal Project

## Library Application

**Description**:
- The application will act as a library's system which can keep track of books from a 
    Book class (containing the name, genre, and initially empty rating of the book)
    and lend them out for a set amount of time before the book is due to return. The app will also
    ask for a rating once a book is returned and store that in the Book then re-add the book into the Library.
    The library will also separate the books by genre allowing duplicate books. 
    *The application will not connect to a library or any website and books will manually have to be added 
    or generated using the Book class*.


- The application is meant for anyone who enjoys reading or wants to access a library with books to borrow or return. 
Whether it is a student, worker, or any reader, a library app is useful for everyone to easily manage 
and navigate to books to borrow.

### User Stories
- As a user I want to be able to "donate" (add) books to the library and make them available to borrow.
- As a user I want to be able to list all the books in the library and an option to display only available books 
(not including already borrowed books).
- As a user I want to be able to select a book from the library, check its status, and borrow it if it is available.
- As a user I want to be able to return a book and give it a rating that will be stored.

- As a user I want to have the option save the state of my account and the books currently on it, and the library's state
- As a user I want to have the option to load my account and the library's state, and
add to it and return books as needed.

  
### Instructions for User
  - Donating a book through the donate book panel by inputting a book name, author, and genre to add it to the library. 
  - Borrowing a book using the View Available Books and Borrow button which adds it to the account.
  - Viewing the available books button which will no longer display the books you borrowed
  (unlike the view all books, which shows all books, even borrowed ones).
  - By clicking the Return Books button which will display the books you currently own and give you the options to return them.
- You can locate my visual component by:
  - Clicking the info button on the bottom left of the main menu which opens up an 
info panel with the libraries logo (obtained from a stock image).
- You can save the state of my application by:
  - Clicking the save button on the bottom left of the main menu. 
- You can reload the state of my application by:
  - Clicking yes when prompted to check for and load a saved state on application start.
