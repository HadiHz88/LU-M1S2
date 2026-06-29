# Exercise 2:

Create a client-server application using TCP sockets in Java where multiple clients can connect to a central server to
perform authenticated operations.

## Client side:

1. The client connects to the server using the server's IP address and port number.
2. Upon connection, the client is prompted to enter a username and password.
3. If authentication succeeds (based on a predefined user list on the server), the client can issue the following
   commands:
    - ADD x y → Server returns the sum of x and y.
    - MAX x y → Server returns the maximum of x and y.
    - BYE → Ends the session.
4. The client receives and displays the server's response for each command.
5. Invalid commands should return an error message from the server.

## Server side:

1. Maintains a predefined list of valid usernames and passwords.
2. Accepts multiple client connections concurrently using threads.
3. Authenticates each client upon connection. If authentication fails, the server sends a rejection
   message and closes the connection.
4. Upon successful login, the server handles commands from the client as described above.
5. Closes the connection when the client sends BYE