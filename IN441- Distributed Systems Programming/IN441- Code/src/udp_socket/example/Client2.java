package udp_socket.example;

import java.io.*;
import java.net.*;

// Client2 does the same job as Client, but keeps the send/receive byte arrays separate.
public class Client2 {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the DatagramSocket for the client.
            DatagramSocket clientSocket = new DatagramSocket();

            // Step 2: create a reader for the user input.
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: read one message from the user.
            String message = inputFromUser.readLine();

            // Step 4: convert the message into bytes.
            byte[] sendBuffer = message.getBytes();

            // Step 5: prepare the byte array for the server response.
            byte[] receiveBuffer = new byte[1024];

            // Step 6: resolve the server address.
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Step 7: create the packet and send it.
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 5001);
            clientSocket.send(sendPacket);

            // Step 8: receive the response packet.
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);

            // Step 9: display the received response.
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Uppercase reply: " + response);
            
            // Step 10: close the DatagramSocket.
            clientSocket.close();
        } catch (Exception exception) {
            System.out.println("UDP example client2 error: " + exception.getMessage());
        }
    }
}
