package exams.final_24_25_1.exercise_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws Exception {

        DatagramSocket clientSocket = new DatagramSocket();

        BufferedReader input =
                new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose one of the following operations:");
        System.out.println("UPPER");
        System.out.println("REVERSE");
        System.out.println("LENGTH");

        System.out.print("Enter a string: ");
        String word = input.readLine();

        System.out.print("Enter your choice: ");
        String cmd = input.readLine();

        String msg = word + ":" + cmd;

        byte[] toServer = msg.getBytes();

        DatagramPacket req = new DatagramPacket(
                toServer,
                toServer.length,
                InetAddress.getByName("localhost"),
                6789);

        clientSocket.send(req);

        byte[] fromServer = new byte[1024];

        DatagramPacket response = new DatagramPacket(fromServer, fromServer.length);

        clientSocket.receive(response);

        String result = new String(response.getData(), 0, response.getLength());

        System.out.println("Your result is: " + result);

        clientSocket.close();
    }
}
