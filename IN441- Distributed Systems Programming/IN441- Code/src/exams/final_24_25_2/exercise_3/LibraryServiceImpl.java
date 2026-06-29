package exams.final_24_25_2.exercise_3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class LibraryServiceImpl extends UnicastRemoteObject implements LibraryService {
    private final Map<String, Book> books = new HashMap<>();

    protected LibraryServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized String addBook(String title, String author, int year) throws RemoteException {
        String key = normalizeTitle(title);

        if (key.isEmpty() || author == null || author.trim().isEmpty()) {
            return "ERROR: Title and author are required";
        }

        if (books.containsKey(key)) {
            return "ERROR: Book already exists";
        }

        books.put(key, new Book(title.trim(), author.trim(), year));
        return "Book added: " + title.trim();
    }

    @Override
    public synchronized String borrowBook(String title, String username) throws RemoteException {
        Book book = books.get(normalizeTitle(title));

        if (book == null) {
            return "ERROR: Book not found";
        }

        if (book.borrowedBy() != null) {
            return "ERROR: Book is already borrowed by " + book.borrowedBy();
        }

        book.borrow(username.trim());
        return username.trim() + " borrowed " + book.title();
    }

    @Override
    public synchronized String returnBook(String title, String username) throws RemoteException {
        Book book = books.get(normalizeTitle(title));

        if (book == null) {
            return "ERROR: Book not found";
        }

        if (book.borrowedBy() == null) {
            return "ERROR: Book is not currently borrowed";
        }

        if (!book.borrowedBy().equalsIgnoreCase(username.trim())) {
            return "ERROR: This book was borrowed by " + book.borrowedBy();
        }

        book.returnBook();
        return username.trim() + " returned " + book.title();
    }

    private static String normalizeTitle(String title) {
        if (title == null) {
            return "";
        }

        return title.trim().toLowerCase();
    }
}
