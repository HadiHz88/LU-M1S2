package java_threads.ex5;

public class MyThread extends Thread {
    @Override
    public void run() {
        // Step 5: print the current thread name and its priority value.
        System.out.println(getName() + " priority: " + getPriority());
    }
}
