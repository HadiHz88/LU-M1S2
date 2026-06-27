package tcp_socket.example;

import java.io.*;
import java.net.*;

// Example client: reads one line from the console, sends it, and prints the echo.
public class Client {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the client socket on port 8000.
            Socket clientSocket = new Socket("localhost", 8000);

            // Step 2: create a BufferedReader to read the user input.
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: create a BufferedReader to read the server response.
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Step 4: create a DataOutputStream to send the message.
            DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());

            // Step 5: read one line from the user.
            String message = inputFromUser.readLine();

            // Step 6: send the message to the server.
            toServer.writeBytes(message + "\n");

            // Step 7: read the echoed message from the server.
            String echoedMessage = fromServer.readLine();

            // Step 8: display the returned message.
            System.out.println("Echo message: " + echoedMessage);

            // Step 9: close the socket.
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("TCP example client error: " + e.getMessage());
        }
    }
}
