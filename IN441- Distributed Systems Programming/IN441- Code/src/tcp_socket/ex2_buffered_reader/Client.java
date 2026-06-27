package tcp_socket.ex2_buffered_reader;

import java.io.*;
import java.net.*;

// Ex2 client: sends numbers as text lines, so parsing happens on the server.
public class Client {
    public static void main(String[] args) throws IOException {
        try {
            // Step 1: connect to the server socket.
            Socket clientSocket = new Socket("localhost", 4445);

            // Step 2: create a reader for user input.
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: create a reader to receive the result.
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Step 4: create a writer to send values to the server.
            PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Step 5: read the first number.
            System.out.print("Enter first number: ");
            String firstNumber = inputFromUser.readLine();

            // Step 6: read the second number.
            System.out.print("Enter second number: ");
            String secondNumber = inputFromUser.readLine();

            // Step 7: read the operator.
            System.out.print("Enter operation (+, -, *, /): ");
            String operator = inputFromUser.readLine();

            // Step 8: send the values as text lines.
            toServer.println(firstNumber);
            toServer.println(secondNumber);
            toServer.println(operator);

            // Step 9: read and display the result.
            String result = fromServer.readLine();
            System.out.println("Result: " + result);
            
            // Step 10: close the socket.
            clientSocket.close();
        } catch (IOException exception) {
            System.out.println("TCP buffered client error: " + exception.getMessage());
        }
    }
}
