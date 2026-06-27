package tcp_socket.ex1;

import java.io.*;
import java.net.*;

// Ex1 client: sends one line to the server and prints the reversed reply.
public class Client {
    public static void main(String[] args) throws IOException {
        try {
            // Step 1: create the client socket and connect to the server.
            Socket clientSocket = new Socket("localhost", 4446);

            // Step 2: create a reader for the user input.
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: create a reader to receive the server response.
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Step 4: create a writer to send the message to the server.
            PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Step 5: read one message from the user.
            System.out.print("Enter a message: ");
            String message = inputFromUser.readLine();

            // Step 6: send the message to the server.
            toServer.println(message);

            // Step 7: read the reversed message from the server.
            String reversedMessage = fromServer.readLine();

            // Step 8: display the returned message.
            System.out.println("Reversed message: " + reversedMessage);
            
            // Step 9: close the socket.
            clientSocket.close();
        } catch (IOException exception) {
            System.out.println("TCP ex1 client error: " + exception.getMessage());
        }
    }
}
