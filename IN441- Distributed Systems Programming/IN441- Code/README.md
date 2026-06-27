# Distributed Systems Java Study Sheet

This repository is organized as a small exam-prep reference for three topics:

- `src/java_threads`: basic Java thread creation, scheduling hints, and synchronization.
- `src/tcp_socket`: TCP client/server examples, including echo servers and calculator exercises.
- `src/udp_socket`: UDP request/response examples, including string processing and simple calculators.

## Running an Example

Compile everything from the project root:

```bash
javac -d out $(find src -name "*.java")
```

Run any class with its package name, for example:

```bash
java -cp out java_threads.ex6.RaceConditionDemo
java -cp out tcp_socket.ex1.Server
java -cp out udp_socket.ex1.Client
```

## Notes

- Package names now match the folder structure, so IDEs and `javac` can resolve them correctly.
- TCP and UDP examples use different ports to avoid accidental clashes when you experiment with several exercises.
- The comments are intentionally short and study-oriented: they explain the concept each file demonstrates without hiding the actual networking or threading steps.
