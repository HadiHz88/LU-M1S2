package udp_socket.example;

import java.net.*;

// Example: single-request UDP server that converts a message to uppercase.
public class Server {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the DatagramSocket on port 5001.
            DatagramSocket serverSocket = new DatagramSocket(5001);

            // Step 2: prepare the byte array for the incoming data.
            byte[] receiveBuffer = new byte[1024];

            // Step 3: create the packet object for receiving.
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            // Step 4: wait for a packet from the client.
            serverSocket.receive(receivePacket);

            // Step 5: convert the packet data to a string.
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // Step 6: convert the message to uppercase.
            byte[] sendBuffer = message.toUpperCase().getBytes();

            // Step 7: create the response packet with the client address and port.
            DatagramPacket sendPacket = new DatagramPacket(
                sendBuffer,
                sendBuffer.length,
                receivePacket.getAddress(),
                receivePacket.getPort()
            );

            // Step 8: send the result back to the client.
            serverSocket.send(sendPacket);

            // Step 9: close the DatagramSocket.
            serverSocket.close();
        } catch (Exception exception) {
            System.out.println("UDP example server error: " + exception.getMessage());
        }
    }
}
