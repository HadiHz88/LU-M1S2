package udp_socket.ex3;

import java.net.*;

// Ex3: one-shot UDP calculator that adds two integers separated by '#'.
public class Server {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the DatagramSocket on port 4000.
            DatagramSocket serverSocket = new DatagramSocket(4000);

            // Step 2: prepare the byte array for incoming data.
            byte[] receiveBuffer = new byte[1024];

            // Step 3: create the packet object for receiving.
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            // Step 4: wait for a client packet.
            serverSocket.receive(receivePacket);

            // Step 5: convert the packet data to text.
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            String[] parts = message.split("#");

            // Step 6: parse the two integers.
            int firstNumber = Integer.parseInt(parts[0]);
            int secondNumber = Integer.parseInt(parts[1]);

            // Step 7: calculate the sum.
            int sum = firstNumber + secondNumber;

            // Step 8: convert the result into bytes.
            byte[] sendBuffer = String.valueOf(sum).getBytes();

            // Step 9: create the response packet.
            DatagramPacket sendPacket = new DatagramPacket(
                sendBuffer,
                sendBuffer.length,
                receivePacket.getAddress(),
                receivePacket.getPort()
            );

            // Step 10: send the result to the client.
            serverSocket.send(sendPacket);
            
            // Step 11: close the DatagramSocket.
            serverSocket.close();
        } catch (Exception exception) {
            System.out.println("UDP ex3 server error: " + exception.getMessage());
        }
    }
}
