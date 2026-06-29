# Exercise 2

Write a Java program using **TCP sockets** to implement a "Multi-Client Online Banking Simulation Server".

## Server Side

1. **TCP Multi-threaded Server:** Listens for incoming client connections on a TCP port and spawns a separate thread for
   each client.
2. **Account Management:** Each client logs in with a unique username and a predefined PIN stored in the server's
   memory. Each account has a balance initialized by the server.
3. **Supported Commands:**
    - **`BALANCE`**: Returns the current balance.
    - **`DEPOSIT <amount>`**: Adds the specified amount to the balance.
    - **`WITHDRAW <amount>`**: Withdraws the specified amount if sufficient funds exist.
    - **`EXIT`**: Logs the client out and closes the connection.

## Client Side

1. Connects to the server and logs in using a username and PIN.
2. Displays a menu of operations (`BALANCE`, `DEPOSIT`, `WITHDRAW`, `EXIT`).
3. Sends commands to the server and displays the server's responses.
