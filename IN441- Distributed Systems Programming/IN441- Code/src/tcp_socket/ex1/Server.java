package tcp_socket.ex1;

import java.io.*;
import java.net.*;

// Ex1: single-request TCP server that reverses one line sent by the client.
public class Server {
    public static void main(String[] args) throws IOException {
        try {
            // Step 1: create the server socket.
            ServerSocket serverSocket = new ServerSocket(4446);

            // Step 2: accept one client connection.
            Socket clientSocket = serverSocket.accept();

            // Step 3: create a reader to receive the client message.
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Step 4: create a writer to send the response.
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);

            // Step 5: read the message sent by the client.
            String message = fromClient.readLine();
            if (message == null) {
                System.out.println("Client disconnected before sending data.");
                clientSocket.close();
                serverSocket.close();
                return;
            }

            // Step 6: reverse the message.
            System.out.println("Received from client: " + message);
            String reversedMessage = new StringBuilder(message).reverse().toString();

            // Step 7: send the reversed message back.
            toClient.println(reversedMessage);

            // Step 8: close the opened sockets.
            clientSocket.close();
            serverSocket.close();
        } catch (IOException exception) {
            System.out.println("TCP ex1 server error: " + exception.getMessage());
        }
    }
}
