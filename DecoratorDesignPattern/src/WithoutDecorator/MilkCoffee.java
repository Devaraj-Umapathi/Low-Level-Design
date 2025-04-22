package WithoutDecorator;

public class MilkCoffee implements Coffee {
    public String getDescription() {
        return "Simple Coffee + Milk";
    }

    public double getCost() {
        return 5.0 + 1.5;
    }
}
