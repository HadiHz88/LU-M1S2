package exams.final_24_25_2.exercise_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        // Step 1: connect to the TCP banking server.
        Socket socket = new Socket("localhost", 9877);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);

        // Step 2: send login credentials.
        System.out.print("Enter username: ");
        String username = input.readLine();

        System.out.print("Enter PIN: ");
        String pin = input.readLine();

        toServer.println(username + ":" + pin);

        String loginResponse = fromServer.readLine();
        if (loginResponse.equals("fail")) {
            System.out.println("Invalid username or PIN");
            socket.close();
            return;
        }

        System.out.println("Login successful");

        // Step 3: send banking commands until EXIT.
        while (true) {
            System.out.println("Enter command (BALANCE, DEPOSIT <amount>, WITHDRAW <amount>, EXIT):");
            String command = input.readLine();

            toServer.println(command);
            String response = fromServer.readLine();
            System.out.println("Server response: " + response);

            if (command.equalsIgnoreCase("EXIT")) {
                break;
            }
        }

        socket.close();
    }
}
