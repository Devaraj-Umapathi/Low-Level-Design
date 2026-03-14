package LiskovSubstitutionWithViolation;

// Violates LSP: Penguin cannot fly, but it inherits a fly() method.
class Bird {
    public void fly() {
        System.out.println("Flying...");
    }
}

class Sparrow extends Bird {
    @Override
    public void fly() {
        System.out.println("Sparrow can fly");
    }
}

class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}

public class LSPViolation {
    public static void main(String[] args) {
        Bird penguin = new Penguin();
        Bird sparrow = new Sparrow();
        sparrow.fly();
        penguin.fly(); // This will break at runtime
    }
}


