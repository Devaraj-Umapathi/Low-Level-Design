package WithoutDecorator;

public class SugarCreamCoffee implements Coffee {
    public String getDescription() {
        return "Simple Coffee + Sugar + Cream";
    }

    public double getCost() {
        return 5.0 + 0.5 + 1.0;
    }
}