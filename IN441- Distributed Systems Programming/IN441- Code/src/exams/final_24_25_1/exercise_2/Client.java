package exams.final_24_25_1.exercise_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        // Step 1: connect to the TCP server.
        Socket socket = new Socket("localhost", 9876);

        BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in)
        );

        BufferedReader fromServer = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);

        // Step 2: send the login credentials.
        System.out.println("Enter your username");
        String username = input.readLine();

        System.out.println("Enter your password");
        String password = input.readLine();

        String authReq = username + ":" + password;

        toServer.println(authReq);

        String res = fromServer.readLine();

        // Step 3: stop if the server rejects the login.
        if (res.equals("fail")) {
            System.out.println("Invalid Credentials, ending connection...");
            socket.close();
            return;
        }

        System.out.println("Connection Successful, please enter your commands");

        // Step 4: send commands until the user enters BYE.
        while (true) {
            System.out.println("Enter command (ADD x y, MAX x y, BYE for ending)");
            String cmd = input.readLine().trim();

            if (cmd.equalsIgnoreCase("BYE")) {
                toServer.println("BYE");
                System.out.println("Server response: " + fromServer.readLine());
                break;
            }

            String request = cmd.replaceAll("\\s+", ":");
            toServer.println(request);

            String operationResult = fromServer.readLine();
            System.out.println("Server response: " + operationResult);
        }

        socket.close();
    }
}
