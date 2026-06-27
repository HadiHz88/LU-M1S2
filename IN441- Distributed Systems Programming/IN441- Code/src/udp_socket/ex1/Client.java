package udp_socket.ex1;

import java.io.*;
import java.net.*;

// Ex1 client: sends one message and waits for the reversed response.
public class Client {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the DatagramSocket for the client.
            DatagramSocket clientSocket = new DatagramSocket();

            // Step 2: create a reader for user input.
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: read the message from the user.
            System.out.print("Enter a message to reverse: ");
            String message = inputFromUser.readLine();

            // Step 4: convert the message into bytes.
            byte[] sendBuffer = message.getBytes();

            // Step 5: prepare a byte array for the server response.
            byte[] receiveBuffer = new byte[1024];

            // Step 6: resolve the server address.
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Step 7: build the packet and send it to the server.
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 5000);
            clientSocket.send(sendPacket);

            // Step 8: receive the response packet from the server.
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);

            // Step 9: convert the response bytes back to a string.
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Reversed message: " + response);
            
            // Step 10: close the DatagramSocket.
            clientSocket.close();
        } catch (Exception exception) {
            System.out.println("UDP ex1 client error: " + exception.getMessage());
        }
    }
}
