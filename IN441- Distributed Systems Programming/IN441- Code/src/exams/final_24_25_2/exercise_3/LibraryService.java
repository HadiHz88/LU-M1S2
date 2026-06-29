package exams.final_24_25_2.exercise_3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LibraryService extends Remote {
    String addBook(String title, String author, int year) throws RemoteException;

    String borrowBook(String title, String username) throws RemoteException;

    String returnBook(String title, String username) throws RemoteException;
}
