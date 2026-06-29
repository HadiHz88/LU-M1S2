package exams.final_24_25_2.exercise_2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static final Map<String, Account> accounts = new HashMap<>();

    static {
        accounts.put("hadi", new Account("1234", 1000.00));
        accounts.put("hanine", new Account("2222", 1500.00));
        accounts.put("messi", new Account("1010", 2000.00));
    }

    public static void main(String[] args) throws Exception {
        // Step 1: create the TCP banking server socket.
        ServerSocket serverSocket = new ServerSocket(9877);
        System.out.println("Banking server is running on port 9877...");

        while (true) {
            // Step 2: accept a client connection.
            Socket client = serverSocket.accept();
            System.out.println("Accepting " + client.getInetAddress());

            // Step 3: handle each client in its own thread.
            new BankServerHandler(client).start();
        }
    }

    public static class Account {
        private final String pin;
        private double balance;

        Account(String pin, double balance) {
            this.pin = pin;
            this.balance = balance;
        }

        boolean checkPin(String pin) {
            return this.pin.equals(pin);
        }

        synchronized double balance() {
            return balance;
        }

        synchronized double deposit(double amount) {
            balance += amount;
            return balance;
        }

        synchronized double withdrawIfPossible(double amount) {
            if (balance < amount) {
                return -1;
            }

            balance -= amount;
            return balance;
        }
    }
}
