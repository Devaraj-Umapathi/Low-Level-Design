package BasicSingletonImplementation;

class Singleton {
    // Step 1: Declare static variable
    private static Singleton instance;

    // Step 2: Private constructor – restricts object creation
    private Singleton() {  // Private constructor
        System.out.println("Singleton instance created!");
    }

    // Step 3: Provide static method to get an instance
    public static Singleton getInstance() {
        if (instance == null) {  // Create instance only if it doesn't exist
            instance = new Singleton();
        }
        return instance;
    }
}

public class SingletonDemo {
    public static void main(String[] args) {
        Singleton obj1 = Singleton.getInstance();
        Singleton obj2 = Singleton.getInstance();

        System.out.println(obj1 == obj2);  // Output: true (Same instance)
    }
}


/**
 * Issues with This Approach
 * ❌ Not Thread-safe: Multiple threads may create multiple instances simultaneously.
 */