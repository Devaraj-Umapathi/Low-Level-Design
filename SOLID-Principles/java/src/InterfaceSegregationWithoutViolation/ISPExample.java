package InterfaceSegregationWithoutViolation;

interface worker {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements worker, Eatable {
    public void work() {
        System.out.println("Developer is coding...");
    }

    public void eat() {
        System.out.println("Developer is eating...");
    }
}

class Robot implements worker {
    public void work() {
        System.out.println("Robot is working...");
    }
}

public class ISPExample {
    public static void main(String[] args) {
        Human human = new Human();
        human.work();
        human.eat();

        Robot robot = new Robot();
        robot.work();
    }
}


