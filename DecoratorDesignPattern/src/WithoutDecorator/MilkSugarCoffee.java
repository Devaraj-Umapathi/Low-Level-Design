package WithoutDecorator;

public class MilkSugarCoffee implements Coffee {
    public String getDescription() {
        return "Simple Coffee + Milk + Sugar";
    }

    public double getCost() {
        return 5.0 + 1.5 + 0.5;
    }
}
