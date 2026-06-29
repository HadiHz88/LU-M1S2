package exams.final_24_25_2.exercise_3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            // Step 1: find the remote library service.
            LibraryService library =
                    (LibraryService) Naming.lookup("rmi://localhost:1099/LibraryServer");

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            // Step 2: show the menu until the user exits.
            while (true) {
                System.out.println("1. Add book");
                System.out.println("2. Borrow book");
                System.out.println("3. Return book");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                String choice = input.readLine();

                if (choice.equals("1")) {
                    System.out.print("Title: ");
                    String title = input.readLine();

                    System.out.print("Author: ");
                    String author = input.readLine();

                    System.out.print("Year: ");
                    int year = Integer.parseInt(input.readLine());

                    System.out.println(library.addBook(title, author, year));
                } else if (choice.equals("2")) {
                    System.out.print("Title: ");
                    String title = input.readLine();

                    System.out.print("Username: ");
                    String username = input.readLine();

                    System.out.println(library.borrowBook(title, username));
                } else if (choice.equals("3")) {
                    System.out.print("Title: ");
                    String title = input.readLine();

                    System.out.print("Username: ");
                    String username = input.readLine();

                    System.out.println(library.returnBook(title, username));
                } else if (choice.equals("4")) {
                    System.out.println("Goodbye");
                    break;
                } else {
                    System.out.println("Invalid option");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
