package ThreadSafeSingleton;

public class Singleton {
    private static Singleton instance;

    private Singleton() { }

    public static synchronized Singleton getInstance() {  // Synchronization added
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

class Main {
    public static void main(String[] args) {
        Runnable task = () -> {
            Singleton instance = Singleton.getInstance();
            System.out.println(Thread.currentThread().getName() + " → " + instance.hashCode());
        };

        // Start two threads at the same time
        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();
    }
}


/**
 * Downside:
 * 🔴 Performance Overhead: Every call to getInstance() is synchronized, which slows down execution.
 */