package LiskovSubstitutionWithoutViolation;

abstract class Bird {
    abstract void makeSound();
}

interface FlyingBird {
    void fly();
}

class Sparrow extends Bird implements FlyingBird {
    public void makeSound() {
        System.out.println("Chirp Chirp!");
    }

    public void fly() {
        System.out.println("Sparrow is flying...");
    }
}

class Penguin extends Bird {
    public void makeSound() {
        System.out.println("Penguin sound...");
    }
}

public class LSPExample {
    public static void main(String[] args) {
        Bird sparrow = new Sparrow();
        Bird penguin = new Penguin();

        sparrow.makeSound();
        penguin.makeSound();

        FlyingBird flyingBird = new Sparrow();
        flyingBird.fly();  // Works without breaking substitution
    }
}


