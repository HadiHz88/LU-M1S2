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
        try {
            System.out.println("Starting operation for client " + client.getInetAddress());

            BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(client.getInputStream())
            );

            PrintWriter output = new PrintWriter(client.getOutputStream(), true);

            String loginInput = fromClient.readLine();

            if (loginInput == null) {
                client.close();
                return;
            }

            System.out.println("Reading from client: " + loginInput);

            String[] usernameAndPass = formatInput(loginInput);

            if (usernameAndPass.length != 2 || !authenticate(usernameAndPass[0], usernameAndPass[1])) {
                output.println("fail");
                client.close();
                return;
            }

            output.println("success");

            String input;

            while ((input = fromClient.readLine()) != null) {
                String[] parts = formatInput(input);

                String cmd = parts[0].toUpperCase();

                if (cmd.equals("BYE")) {
                    output.println("bye");
                    break;
                }

                if (parts.length != 3) {
                    output.println("Invalid input format");
                    continue;
                }

                int response = doOperation(cmd, parts[1], parts[2]);

                output.println(response);
            }

            client.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] formatInput(String input) {
        return input.split(":");
    }

    private int doOperation(String cmd, String x, String y) {
        int numX = Integer.parseInt(x);
        int numY = Integer.parseInt(y);

        return switch (cmd) {
            case "ADD" -> numX + numY;
            case "MAX" -> Math.max(numX, numY);
            default -> Integer.MIN_VALUE;
        };
    }

    private boolean authenticate(String username, String password) {
        return Server.userList.contains(new Server.User(username, password));
    }
}