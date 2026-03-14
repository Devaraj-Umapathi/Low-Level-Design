package OpenClosedWithoutViolation;

// Now, new shapes can be added without modifying the existing code
interface Shape {
    double calculateArea();
}

class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements Shape {
    private double length, breadth;

    public Rectangle(double length, double breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    public double calculateArea() {
        return length * breadth;
    }
}

class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.calculateArea();
    }
}

public class OCPExample {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(4, 6);
        AreaCalculator calculator = new AreaCalculator();

        System.out.println("Circle Area: " + calculator.calculateArea(circle));
        System.out.println("Rectangle Area: " + calculator.calculateArea(rectangle));
    }
}

