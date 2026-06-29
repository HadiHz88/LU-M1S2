# Exercise 3

Write a program to design and implement a **Distributed Library Management System using Java RMI**, where multiple
clients can connect to a remote server to manage and borrow books concurrently.

1. **Define a remote interface `LibraryService` with the following methods:**
    - `addBook(String title, String author, int year)`: Adds a new book to the library.
    - `borrowBook(String title, String username)`: Allows a user to borrow a book if it is available.
    - `returnBook(String title, String username)`: Allows a user to return a borrowed book.

2. **Server Implementation:**
    - Implement the `LibraryService` interface in a class named `LibraryServiceImpl`.
    - Register the `LibraryServiceImpl` object with the RMI registry under the name `"LibraryServer"`.

3. **Client Application:**
    - Create a client program that connects to the RMI registry to look up `LibraryService`.
    - The client should present a menu to the user with the following operations:
        - Add a new book.
        - Borrow or return a book.
