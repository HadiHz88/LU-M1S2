package java_threads.ex2;

// Ex2: starts two threads so their output can interleave.
public class Main {
    public static void main(String[] args) {
        // Step 1: create the first thread.
        ThreadA threadA = new ThreadA();

        // Step 2: create the second thread.
        ThreadB threadB = new ThreadB();

        // Step 3: start both threads.
        threadA.start();
        threadB.start();
    }
}
