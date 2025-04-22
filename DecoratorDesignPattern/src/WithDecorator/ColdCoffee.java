package WithDecorator;

public class ColdCoffee implements Coffee {
    public String getDescription() {
        return "Cold Coffee";
    }

    public double getCost() {
        return 10.0;
    }
}