package tcp_socket.ex3;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        try {
            // Step 1: connect to the server.
            Socket clientSocket = new Socket("localhost", 4000);

            // Step 2: create a reader for keyboard input.
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: create a reader to receive the server result.
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Step 4: create a writer to send the two numbers.
            PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Step 5: read the first number.
            System.out.print("Enter first number: ");
            String firstNumber = inputFromUser.readLine();

            // Step 6: read the second number.
            System.out.print("Enter second number: ");
            String secondNumber = inputFromUser.readLine();

            // Step 7: send both numbers to the server.
            toServer.println(firstNumber);
            toServer.println(secondNumber);

            // Step 8: receive and display the result.
            String result = fromServer.readLine();
            System.out.println("Result: " + result);
            
            // Step 9: close the socket.
            clientSocket.close();
        } catch (IOException exception) {
            System.out.println("TCP ex3 client error: " + exception.getMessage());
        }
    }
}
