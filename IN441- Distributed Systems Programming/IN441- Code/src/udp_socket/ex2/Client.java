package udp_socket.ex2;

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

            // Step 4: read the first number.
            System.out.print("Enter first number: ");
            String firstNumber = inputFromUser.readLine();

            // Step 5: read the second number.
            System.out.print("Enter second number: ");
            String secondNumber = inputFromUser.readLine();

            // Step 6: read the operator.
            System.out.print("Enter operation (+, -, *, /): ");
            String operator = inputFromUser.readLine();

            // Step 7: join the values in one message separated by commas.
            String message = firstNumber + "," + secondNumber + "," + operator;
            byte[] sendBuffer = message.getBytes();

            // Step 8: resolve the server address.
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Step 9: create the packet and send it.
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 6000);
            clientSocket.send(sendPacket);

            // Step 10: receive the result packet.
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);

            // Step 11: convert the result to text and print it.
            String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Result = " + result);
            
            // Step 12: close the DatagramSocket.
            clientSocket.close();
        } catch (Exception exception) {
            System.out.println("UDP ex2 client error: " + exception.getMessage());
        }
    }
}
