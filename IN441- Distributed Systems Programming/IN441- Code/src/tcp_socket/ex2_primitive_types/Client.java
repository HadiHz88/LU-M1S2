package tcp_socket.ex2_primitive_types;

import java.io.*;
import java.net.*;
import java.util.Scanner;

// Ex2 primitive client: sends ints and one char operator through DataOutputStream.
public class Client {
    public static void main(String[] args) throws IOException {
        try {
            // Step 1: connect the client socket to the server.
            Socket clientSocket = new Socket("localhost", 4447);

            // Step 2: create a scanner to read user input.
            Scanner inputFromUser = new Scanner(System.in);

            // Step 3: create an input stream to receive the server result.
            DataInputStream fromServer = new DataInputStream(clientSocket.getInputStream());

            // Step 4: create an output stream to send primitive values.
            DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());

            // Step 5: read the first number.
            System.out.print("Enter first number: ");
            int firstNumber = inputFromUser.nextInt();

            // Step 6: read the second number.
            System.out.print("Enter second number: ");
            int secondNumber = inputFromUser.nextInt();

            // Step 7: read the operator.
            System.out.print("Enter operation (+, -, *, /): ");
            char operator = inputFromUser.next().charAt(0);

            // Step 8: send the values in primitive form.
            toServer.writeInt(firstNumber);
            toServer.writeInt(secondNumber);
            toServer.writeChar(operator);
            toServer.flush();

            // Step 9: read and display the result.
            int result = fromServer.readInt();
            System.out.println("Result: " + result);
            
            // Step 10: close the socket.
            clientSocket.close();
        } catch (IOException exception) {
            System.out.println("TCP primitive client error: " + exception.getMessage());
        }
    }
}
