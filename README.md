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


- This project is an interest to me because during my early teens I wsa an avid reader and while first I used to go to 
the physical library, as I grew older and realized the library did not have all the books I was looking for, which led 
me to finding an app called overdrive that connects to the library using your library card and displays a much wider 
selection of books in electronic for, while still keeping the libraries borrowing aspect. This drove me to decide to 
make something along those lines using the knowledge and skills I have now.

### User Stories
- As a user I want to be able to "donate" (add) books to the library and make them available to borrow.
- As a user I want to be able to list all the books in the library and an option to display only available books 
(not including already borrowed books).
- As a user I want to be able to select a book from the library, check its status, and borrow it if it is available.
- As a user I want to be able to return a book and give it a rating that will be stored.

- As a user I want to have the option save the state of my account and the books currently on it, and the library's state
- As a user I want to have the option to load my account and the library's state, and
add to it and return books as needed.

  
### Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by either:
  - Donating a book through the donate book panel by inputting a book name, author, and genre to add it to the library. 
  - Borrowing a book using the View Available Books and Borrow button which adds it to the account.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by either:
  - Viewing the available books button which will no longer display the books you borrowed 
  (unlike the view all books, which shows all books, even borrowed ones).
  - By clicking the Return Books button
which will display the books you currently own and give you the options to return them.
- You can locate my visual component by:
  - Clicking the info button on the bottom left of the main menu which opens up an 
info panel with the libraries logo (obtained from a stock image).
- You can save the state of my application by:
  - Clicking the save button on the bottom left of the main menu. 
- You can reload the state of my application by:
  - Clicking yes when prompted to check for and load a saved state on application start.


### Phase 4: Task 2
Thu Nov 30 23:05:13 PST 2023
Initialized books

Thu Nov 30 23:05:26 PST 2023
Borrowed Book: The Shining | Stephen King

Thu Nov 30 23:05:26 PST 2023
Borrowed Book: Gone Girl | Gillian Flynn

Thu Nov 30 23:05:26 PST 2023
Borrowed Book: Murder on the Orient Express | Agatha Christie

Thu Nov 30 23:05:26 PST 2023
Borrowed Book: The Love Hypothesis | Ali Hazelwood

Thu Nov 30 23:05:43 PST 2023
Donated Book: NewBook | NewName To library

Thu Nov 30 23:05:49 PST 2023
Returned Book: Gone Girl | Gillian Flynn

Thu Nov 30 23:05:49 PST 2023
Returned Book: Murder on the Orient Express | Agatha Christie

Thu Nov 30 23:05:53 PST 2023
Borrowed Book: NewBook | NewName


### Phase 4: Task 3
If I had the time to refactor some aspects of the project, I would first try to make the ui classes individual,
where I would have separate classes for different tabs instead of everything on the main menu. A second thing would be
to make the ui stay on the same window instead of using popups as I have used. I would also finish the Book class and library
to allow a rating method which is partially completed in the code but is never used. Another I would have liked to refactor is
to have the main application use multiple users as originally intended instead of 1 user which can be selected 
at the start and individually make use of the library's
borrowed function where right now if a book is borrowed nothing will really be affected since there is only one user, and
allowing multiple users would make that function much more realistic.