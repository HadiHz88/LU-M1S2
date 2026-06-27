package udp_socket.ex1;

import java.net.*;

// Ex1: receives one UDP message, reverses it, and sends the reply back.
public class Server {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the DatagramSocket on port 5000.
            DatagramSocket serverSocket = new DatagramSocket(5000);

            // Step 2: prepare a byte array to store the received data.
            byte[] receiveBuffer = new byte[1024];

            // Step 3: create a packet object for receiving.
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            // Step 4: wait for a client packet.
            serverSocket.receive(receivePacket);

            // Step 5: convert the packet data to a string.
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // Step 6: reverse the string.
            String reversedMessage = new StringBuilder(message).reverse().toString();

            // Step 7: convert the response to bytes.
            byte[] sendBuffer = reversedMessage.getBytes();

            // Step 8: create a response packet using the client address and port.
            DatagramPacket sendPacket = new DatagramPacket(
                sendBuffer,
                sendBuffer.length,
                receivePacket.getAddress(),
                receivePacket.getPort()
            );

            // Step 9: send the packet back to the client.
            serverSocket.send(sendPacket);
            
            // Step 10: close the DatagramSocket.
            serverSocket.close();
        } catch (Exception exception) {
            System.out.println("UDP ex1 server error: " + exception.getMessage());
        }
    }
}
