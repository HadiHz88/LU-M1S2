# Sockets and RMI Cheatsheet

Each code block is a small skeleton. When compiling real Java files, put each `public class` in its own matching `.java` file.

## TCP Socket

TCP is connection-based. The server creates a `ServerSocket`, accepts a `Socket`, then both sides communicate through input/output streams.

### TCP Server Steps

1. Create `ServerSocket` with a port.
2. Accept a client using `accept()`.
3. Create input/output streams from the accepted `Socket`.
4. Read data from the client.
5. Process the request.
6. Send a response.
7. Close the client socket.

### TCP Server Skeleton

```java
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("TCP server waiting...");

            Socket clientSocket = serverSocket.accept();

            BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter toClient = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            String message = fromClient.readLine();
            String response = message.toUpperCase();

            toClient.println(response);

            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
```

### TCP Client Steps

1. Create `Socket` with server host and port.
2. Create input/output streams from the socket.
3. Read user input if needed.
4. Send data to the server.
5. Read server response.
6. Close socket.

### TCP Client Skeleton

```java
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader keyboard = new BufferedReader(
                    new InputStreamReader(System.in));
            BufferedReader fromServer = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter toServer = new PrintWriter(
                    socket.getOutputStream(), true);

            System.out.print("Message: ");
            String message = keyboard.readLine();

            toServer.println(message);

            String response = fromServer.readLine();
            System.out.println("Server replied: " + response);

            socket.close();
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
```

### Threaded TCP Pattern

Use this when the server must handle many clients.

```java
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter toClient = new PrintWriter(
                    socket.getOutputStream(), true);

            String message = fromClient.readLine();
            toClient.println("Echo: " + message);
        } catch (IOException e) {
            System.out.println("Handler error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
```

### TCP Stream Choices

- Text lines: `BufferedReader` + `PrintWriter`
- Primitive values: `DataInputStream` + `DataOutputStream`
- The write order must exactly match the read order.

```java
// Client writes:
toServer.writeInt(a);
toServer.writeInt(b);
toServer.writeChar(operator);
toServer.flush();

// Server reads in the same order:
int a = fromClient.readInt();
int b = fromClient.readInt();
char operator = fromClient.readChar();
```

## UDP Socket

UDP is packet-based. There is no connection. Each message is sent inside a `DatagramPacket`.

### UDP Server Steps

1. Create `DatagramSocket` with a port.
2. Create byte buffer.
3. Create `DatagramPacket` for receiving.
4. Call `receive(packet)`.
5. Convert packet data to text.
6. Process request.
7. Create response packet using client address and port.
8. Send response with `send(packet)`.

### UDP Server Skeleton

```java
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(6000);

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket =
                    new DatagramPacket(receiveBuffer, receiveBuffer.length);

            socket.receive(receivePacket);

            String message = new String(
                    receivePacket.getData(), 0, receivePacket.getLength());
            String response = message.toUpperCase();
            byte[] sendBuffer = response.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer,
                    sendBuffer.length,
                    receivePacket.getAddress(),
                    receivePacket.getPort());

            socket.send(sendPacket);
            socket.close();
        } catch (Exception e) {
            System.out.println("UDP server error: " + e.getMessage());
        }
    }
}
```

### UDP Client Steps

1. Create `DatagramSocket`.
2. Read or build message.
3. Convert message to bytes.
4. Resolve server address with `InetAddress`.
5. Create packet with server address and port.
6. Send packet.
7. Receive response packet.
8. Convert response bytes to text.
9. Close socket.

### UDP Client Skeleton

```java
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            BufferedReader keyboard = new BufferedReader(
                    new InputStreamReader(System.in));

            System.out.print("Message: ");
            String message = keyboard.readLine();
            byte[] sendBuffer = message.getBytes();

            InetAddress serverAddress = InetAddress.getByName("localhost");
            DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer, sendBuffer.length, serverAddress, 6000);

            socket.send(sendPacket);

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket =
                    new DatagramPacket(receiveBuffer, receiveBuffer.length);

            socket.receive(receivePacket);

            String response = new String(
                    receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server replied: " + response);

            socket.close();
        } catch (Exception e) {
            System.out.println("UDP client error: " + e.getMessage());
        }
    }
}
```

### Threaded UDP Pattern

The server keeps receiving packets. Each packet is processed by a handler thread.

```java
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(6000);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet =
                        new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);
                new ClientHandler(packet, socket).start();
            }
        } catch (Exception e) {
            System.out.println("UDP server error: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread {
    private final DatagramPacket packet;
    private final DatagramSocket socket;

    public ClientHandler(DatagramPacket packet, DatagramSocket socket) {
        this.packet = packet;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String message = new String(
                    packet.getData(), 0, packet.getLength());
            String response = "Echo: " + message;
            byte[] data = response.getBytes();

            DatagramPacket reply = new DatagramPacket(
                    data,
                    data.length,
                    packet.getAddress(),
                    packet.getPort());

            socket.send(reply);
        } catch (Exception e) {
            System.out.println("Handler error: " + e.getMessage());
        }
    }
}
```

## Java RMI

RMI lets a client call methods on a remote object as if it were a normal Java object.

### RMI File Order

1. Remote interface.
2. Implementation class.
3. Server that registers the object.
4. Client that looks up and calls the object.

### 1. Remote Interface

Rules:
- Extend `Remote`.
- Import `java.rmi.Remote`.
- Import `java.rmi.RemoteException`.
- Every remote method must throw `RemoteException`.

```java
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyService extends Remote {
    String process(String input) throws RemoteException;
}
```

### 2. Implementation Class

Rules:
- Extend `UnicastRemoteObject`.
- Implement the remote interface.
- Constructor throws `RemoteException`.
- Use `synchronized` if many clients modify shared data.

```java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyServiceImpl extends UnicastRemoteObject implements MyService {

    protected MyServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String process(String input) throws RemoteException {
        return input.toUpperCase();
    }
}
```

Shared-state example:

```java
public synchronized String borrowBook(String title, String username)
        throws RemoteException {
    // Check book, update state, return message.
    return username + " borrowed " + title;
}
```

### 3. RMI Server

Rules:
- Start or get registry on port `1099`.
- Create implementation object.
- Bind it using `registry.rebind(name, object)`.

```java
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            Registry registry = startRegistry();

            MyServiceImpl service = new MyServiceImpl();
            registry.rebind("MyService", service);

            System.out.println("RMI server is running...");
        } catch (Exception e) {
            System.out.println("Server error: " + e.toString());
        }
    }

    private static Registry startRegistry() throws RemoteException {
        try {
            return LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            return LocateRegistry.getRegistry(1099);
        }
    }
}
```

### 4. RMI Client

Rules:
- Use `Naming.lookup(...)`.
- Cast result to the remote interface.
- Call remote methods normally.

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            MyService service = (MyService) Naming.lookup(
                    "rmi://localhost:1099/MyService");

            BufferedReader keyboard = new BufferedReader(
                    new InputStreamReader(System.in));

            System.out.print("Input: ");
            String input = keyboard.readLine();

            String result = service.process(input);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Client error: " + e.toString());
        }
    }
}
```

### RMI Examples In This Repo

- `TextAnalysisService extends Remote`
- `TextAnalysisServiceImpl extends UnicastRemoteObject implements TextAnalysisService`
- Server binds with: `registry.rebind("analysis", service)`
- Client looks up: `Naming.lookup("rmi://localhost:1099/analysis")`

- `LibraryService extends Remote`
- `LibraryServiceImpl extends UnicastRemoteObject implements LibraryService`
- Methods are `synchronized` because clients change shared book state.
- Server binds with: `registry.rebind("LibraryServer", service)`
- Client looks up: `Naming.lookup("rmi://localhost:1099/LibraryServer")`

## Run Order

Compile from project root:

```bash
javac -d out $(find src -name "*.java")
```

Then:

1. Start the server first.
2. Start the client second.
3. For threaded servers, keep the server running and launch multiple clients.

## Quick Memory Hooks

- TCP = connect first, then streams.
- UDP = no connection, only packets.
- RMI = interface, implementation, registry, lookup.
- `extends Thread` for per-client handlers.
- `extends Remote` for RMI interfaces.
- `extends UnicastRemoteObject` for RMI implementations.
- Sender write order must match receiver read order.
