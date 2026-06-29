package exams.final_24_25_1.exercise_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler extends Thread {

    private final Socket client;

    ServerHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (
                Socket socket = client;
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Starting operation for client " + client.getInetAddress());

            // Step 4: read and validate the login request.
            String loginInput = fromClient.readLine();

            if (loginInput == null) {
                return;
            }

            System.out.println("Reading from client: " + loginInput);

            String[] usernameAndPass = formatLogin(loginInput);

            if (usernameAndPass.length != 2 || !authenticate(usernameAndPass[0], usernameAndPass[1])) {
                output.println("fail");
                return;
            }

            output.println("success");

            // Step 5: process commands until the client sends BYE.
            String input;
            while ((input = fromClient.readLine()) != null) {
                if (isBye(input)) {
                    output.println("bye");
                    break;
                }

                output.println(processCommand(input));
            }

        } catch (IOException e) {
            System.out.println("Error while handling client: " + e.getMessage());
        }
    }

    static String processCommand(String input) {
        String[] parts = formatCommand(input);

        if (parts.length != 3) {
            return "ERROR: Invalid input format";
        }

        try {
            int response = doOperation(parts[0], parts[1], parts[2]);
            return String.valueOf(response);
        } catch (NumberFormatException e) {
            return "ERROR: x and y must be integers";
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    private static boolean isBye(String input) {
        return input.trim().equalsIgnoreCase("BYE");
    }

    private static String[] formatLogin(String input) {
        return input.split(":", 2);
    }

    private static String[] formatCommand(String input) {
        return input.trim().split("[\\s:]+");
    }

    private static int doOperation(String cmd, String x, String y) {
        int numX = Integer.parseInt(x);
        int numY = Integer.parseInt(y);

        return switch (cmd.trim().toUpperCase()) {
            case "ADD" -> numX + numY;
            case "MAX" -> Math.max(numX, numY);
            default -> throw new IllegalArgumentException("Invalid command");
        };
    }

    private boolean authenticate(String username, String password) {
        return password.equals(Server.users.get(username));
    }
}
