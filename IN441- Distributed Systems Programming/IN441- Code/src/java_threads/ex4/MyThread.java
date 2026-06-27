package java_threads.ex4;

public class MyThread extends Thread {
    @Override
    public void run() {
        // Step 5: each thread prints its name and then gives another thread a chance to run.
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " running at index " + i);
            Thread.yield();
        }
    }
}
