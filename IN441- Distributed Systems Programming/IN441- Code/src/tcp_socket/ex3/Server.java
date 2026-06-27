package tcp_socket.ex3;

import java.io.*;
import java.net.*;

// Ex3: one-shot TCP calculator that adds two integers.
public class Server {
    public static void main(String[] args) throws IOException {
        try {
            // Step 1: create the server socket.
            ServerSocket serverSocket = new ServerSocket(4000);

            // Step 2: accept one client connection.
            Socket clientSocket = serverSocket.accept();

            // Step 3: create a reader to receive the two numbers.
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Step 4: create a writer to send the sum.
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);

            // Step 5: read the first and second numbers.
            int firstNumber = Integer.parseInt(fromClient.readLine());
            int secondNumber = Integer.parseInt(fromClient.readLine());

            // Step 6: calculate the result.
            int sum = firstNumber + secondNumber;

            // Step 7: send the result back to the client.
            toClient.println(sum);
            
            // Step 8: close the opened sockets.
            clientSocket.close();
            serverSocket.close();
        } catch (IOException exception) {
            System.out.println("TCP ex3 server error: " + exception.getMessage());
        }
    }
}
