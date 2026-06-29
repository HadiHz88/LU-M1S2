package exams.final_24_25_2.exercise_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

public class BankServerHandler extends Thread {
    private final Socket client;

    BankServerHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (
                Socket socket = client;
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Step 4: authenticate the client.
            String loginInput = fromClient.readLine();
            Server.Account account = authenticate(loginInput);

            if (account == null) {
                toClient.println("fail");
                return;
            }

            toClient.println("success");

            // Step 5: process banking commands until EXIT.
            String input;
            while ((input = fromClient.readLine()) != null) {
                if (input.trim().equalsIgnoreCase("EXIT")) {
                    toClient.println("Goodbye");
                    break;
                }

                toClient.println(processCommand(account, input));
            }
        } catch (IOException e) {
            System.out.println("Error while handling bank client: " + e.getMessage());
        }
    }

    static String processCommand(Server.Account account, String input) {
        String[] parts = input.trim().split("\\s+");
        String command = parts[0].toUpperCase();

        try {
            switch (command) {
                case "BALANCE":
                    if (parts.length != 1) {
                        return "ERROR: BALANCE does not take an amount";
                    }
                    return "Balance: " + formatAmount(account.balance());

                case "DEPOSIT":
                    if (parts.length != 2) {
                        return "ERROR: Use DEPOSIT <amount>";
                    }
                    double depositAmount = parsePositiveAmount(parts[1]);
                    return "Deposit successful. Balance: " + formatAmount(account.deposit(depositAmount));

                case "WITHDRAW":
                    if (parts.length != 2) {
                        return "ERROR: Use WITHDRAW <amount>";
                    }
                    double withdrawAmount = parsePositiveAmount(parts[1]);
                    double newBalance = account.withdrawIfPossible(withdrawAmount);
                    if (newBalance < 0) {
                        return "ERROR: Insufficient funds";
                    }
                    return "Withdraw successful. Balance: " + formatAmount(newBalance);

                default:
                    return "ERROR: Invalid command";
            }
        } catch (
                NumberFormatException e) {
            return "ERROR: Amount must be a number";
        } catch (
                IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    private static Server.Account authenticate(String loginInput) {
        if (loginInput == null) {
            return null;
        }

        String[] parts = loginInput.split(":", 2);
        if (parts.length != 2) {
            return null;
        }

        Server.Account account = Server.accounts.get(parts[0].trim().toLowerCase());
        if (account == null || !account.checkPin(parts[1].trim())) {
            return null;
        }

        return account;
    }

    private static double parsePositiveAmount(String value) {
        double amount = Double.parseDouble(value);
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return amount;
    }

    private static String formatAmount(double amount) {
        return String.format(Locale.US, "%.2f", amount);
    }
}
