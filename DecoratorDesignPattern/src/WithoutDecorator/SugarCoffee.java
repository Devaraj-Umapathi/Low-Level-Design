package WithoutDecorator;

public class SugarCoffee implements Coffee {
    public String getDescription() {
        return "Simple Coffee + Sugar";
    }

    public double getCost() {
        return 5.0 + 0.5;
    }
}
