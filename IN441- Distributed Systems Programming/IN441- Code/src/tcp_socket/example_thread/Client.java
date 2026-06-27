package tcp_socket.example_thread;

import java.io.*;
import java.net.*;

// Threaded echo client: same idea as the single-client example, but on port 8001.
public class Client {
    public static void main(String[] args) throws IOException {
        try {
            // Step 1: connect the client socket to the threaded server.
            Socket clientSocket = new Socket("localhost", 8001);

            // Step 2: create a reader for user input.
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: create a reader to receive the echoed message.
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Step 4: create an output stream to send the message.
            DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());

            // Step 5: read the message from the user.
            String message = inputFromUser.readLine();

            // Step 6: send the message to the server.
            toServer.writeBytes(message + "\n");

            // Step 7: read the echoed response.
            String echoedMessage = fromServer.readLine();

            // Step 8: display the response.
            System.out.println("Echo message: " + echoedMessage);
            
            // Step 9: close the socket.
            clientSocket.close();
        } catch (IOException exception) {
            System.out.println("TCP threaded client error: " + exception.getMessage());
        }
    }
}
