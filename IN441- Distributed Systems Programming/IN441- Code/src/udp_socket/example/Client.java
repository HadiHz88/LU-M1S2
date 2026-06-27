package udp_socket.example;

import java.io.*;
import java.net.*;

// Example client: sends a message and prints the uppercase reply.
public class Client {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the DatagramSocket for the client.
            DatagramSocket clientSocket = new DatagramSocket();

            // Step 2: create a reader for user input.
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: prepare the byte array for the response.
            byte[] receiveBuffer = new byte[1024];

            // Step 4: read one message from the user.
            String message = inputFromUser.readLine();

            // Step 5: convert the message to bytes.
            byte[] sendBuffer = message.getBytes();

            // Step 6: resolve the server address.
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Step 7: create the packet and send it.
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 5001);
            clientSocket.send(sendPacket);

            // Step 8: receive the response packet.
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);

            // Step 9: convert the result back to text and print it.
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Uppercase reply: " + response);
            
            // Step 10: close the DatagramSocket.
            clientSocket.close();
        } catch (Exception exception) {
            System.out.println("UDP example client error: " + exception.getMessage());
        }
    }
}
