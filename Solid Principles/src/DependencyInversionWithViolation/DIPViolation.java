package DependencyInversionWithViolation;

class WiredKeyboard {
    public void connect() {
        System.out.println("Wired keyboard connected");
    }
}

// Violates DIP: Computer is tightly coupled to WiredKeyboard.
class Computer {
    private WiredKeyboard keyboard = new WiredKeyboard(); // Tight coupling

    public void start() {
        keyboard.connect();
    }
}

public class DIPViolation {
    public static void main(String[] args) {
        Computer pc = new Computer();
        pc.start();
    }
}

