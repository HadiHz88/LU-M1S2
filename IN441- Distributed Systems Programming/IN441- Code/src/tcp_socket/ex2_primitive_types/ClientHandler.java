package tcp_socket.ex2_primitive_types;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Step 4: create an input stream to receive primitive values.
            DataInputStream fromClient = new DataInputStream(socket.getInputStream());

            // Step 5: create an output stream to send the result.
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());

            // Step 6: read the numbers and the operator.
            int firstNumber = fromClient.readInt();
            int secondNumber = fromClient.readInt();
            char operator = fromClient.readChar();

            // Step 7: perform the arithmetic operation.
            int result;
            switch (operator) {
                case '+':
                    result = firstNumber + secondNumber;
                    break;
                case '-':
                    result = firstNumber - secondNumber;
                    break;
                case '*':
                    result = firstNumber * secondNumber;
                    break;
                case '/':
                    if (secondNumber == 0) {
                        throw new IllegalArgumentException("Division by zero is not allowed.");
                    }
                    result = firstNumber / secondNumber;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }

            // Step 8: send the result to the client.
            toClient.writeInt(result);
            toClient.flush();
        } catch (Exception exception) {
            System.out.println("Error while handling primitive client: " + exception.getMessage());
        } finally {
            try {
                // Step 9: close the client socket.
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
