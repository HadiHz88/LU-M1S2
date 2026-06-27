package udp_socket.ex2;

import java.net.*;

// Ex2: multi-client UDP calculator. Each request is handled in its own thread.
public class Server {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the DatagramSocket on port 6000.
            DatagramSocket serverSocket = new DatagramSocket(6000);

            // Step 2: keep receiving requests from different clients.
            while (true) {
                // Step 3: prepare a byte array for the incoming data.
                byte[] receiveBuffer = new byte[1024];

                // Step 4: create the packet object that will receive the request.
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Step 5: wait for a request packet.
                serverSocket.receive(receivePacket);

                // Step 6: handle the request in a separate thread.
                new ClientHandler(receivePacket, serverSocket).start();
            }
        } catch (Exception exception) {
            System.out.println("UDP ex2 server error: " + exception.getMessage());
        }
    }
}
