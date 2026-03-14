package InterfaceSegregationWithViolation;

// Violates ISP: Robot is forced to implement eat() even though it doesn't need it.
interface Worker {
    void work();
    void eat();
}
class Human implements Worker {
    public void work() {
        System.out.println("Robot working...");
    }

    public void eat() {
        System.out.println("Humans eating...");
    }
}
class Robot implements Worker {
    public void work() {
        System.out.println("Robot working...");
    }

    public void eat() {
        throw new UnsupportedOperationException("Robots do not eat!");
    }
}

public class ISPViolation {
    public static void main(String[] args) {
        Human human = new Human();
        human.work();
        human.eat();
        Robot robot = new Robot();
        robot.work();
        robot.eat(); // Causes error
    }
}


