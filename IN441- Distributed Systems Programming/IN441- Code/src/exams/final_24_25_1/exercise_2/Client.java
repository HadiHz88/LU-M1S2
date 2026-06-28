package exams.final_24_25_1.exercise_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9876);

        BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in)
        );

        BufferedReader fromServer = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Enter your username");
        String username = input.readLine();

        System.out.println("Enter your password");
        String password = input.readLine();

        String authReq = username + ":" + password;

        toServer.println(authReq);

        String res = fromServer.readLine();

        if (res.equals("fail")) {
            System.out.println("Invalid Credentials, ending connection...");
            socket.close();
            return;
        }

        System.out.println("Connection Successful, please enter your command and input");

        while (true) {
            System.out.println("Enter command (ADD, MAX, BYE for ending)");
            String cmd = input.readLine();

            if (cmd.equalsIgnoreCase("BYE")) {
                toServer.println("BYE");
                System.out.println("Server response: " + fromServer.readLine());
                break;
            }

            System.out.println("Enter X");
            String x = input.readLine();

            System.out.println("Enter Y");
            String y = input.readLine();

            String request = cmd + ":" + x + ":" + y;

            toServer.println(request);

            String operationResult = fromServer.readLine();
            System.out.println("Server response: " + operationResult);
        }

        socket.close();
    }
}