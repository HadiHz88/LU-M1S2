package java_threads.ex6;

// Ex6 runner: both threads share the same Counter instance.
public class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        // Step 1: create one shared counter object.
        Counter counter = new Counter();

        // Step 2: create two threads that use the same counter.
        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);

        // Step 3: start both threads.
        t1.start();
        t2.start();

        // Step 4: wait until both threads finish.
        t1.join();
        t2.join();

        // Step 5: print the final result.
        System.out.println("Counter result: " + counter.getCount());
    }
}
