package WithoutDecorator;

public class Main {
    public static void main(String[] args) {

        // User request coffee
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        // User requested to add milk
        coffee = new MilkCoffee();
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        // User requested to add both milk and sugar :(
        coffee = new MilkSugarCoffee();
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        // What if User request to add Milk, sugar and cream ? You cant keep creating classes for that :(
    }
}
