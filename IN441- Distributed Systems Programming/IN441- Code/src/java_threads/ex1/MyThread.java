package java_threads.ex1;

// Ex1: the simplest example of creating a thread by extending Thread.
public class MyThread extends Thread {
    @Override
    public void run() {
        // Step 1: the code inside run() is executed by the new thread.
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello from worker thread iteration " + i);
        }
    }

    public static void main(String[] args) {
        // Step 2: create an object from the thread class.
        MyThread thread = new MyThread();

        // Step 3: start the thread so Java calls run() automatically.
        thread.start();
    }
}
