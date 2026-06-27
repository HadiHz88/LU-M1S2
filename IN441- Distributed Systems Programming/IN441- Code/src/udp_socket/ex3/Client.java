package udp_socket.ex3;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the DatagramSocket for the client.
            DatagramSocket clientSocket = new DatagramSocket();

            // Step 2: create a reader for user input.
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // Step 3: prepare the byte array for the response.
            byte[] receiveBuffer = new byte[1024];

            // Step 4: read the first integer.
            System.out.print("Enter 1st integer: ");
            String firstNumber = inputFromUser.readLine();

            // Step 5: read the second integer.
            System.out.print("Enter 2nd integer: ");
            String secondNumber = inputFromUser.readLine();

            // Step 6: join the values with # so the server can split them.
            String message = firstNumber + "#" + secondNumber;
            byte[] sendBuffer = message.getBytes();

            // Step 7: resolve the server address.
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Step 8: create the packet and send it.
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 4000);
            clientSocket.send(sendPacket);

            // Step 9: receive the result packet.
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);

            // Step 10: convert the result to text and print it.
            String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("a + b = " + result);
            
            // Step 11: close the DatagramSocket.
            clientSocket.close();
        } catch (Exception exception) {
            System.out.println("UDP ex3 client error: " + exception.getMessage());
        }
    }
}
