package WithoutFactoryPattern;

// Product Interface
interface Food {
    String prepare();
}

// Concrete Products
class Pizza implements Food {
    public String prepare() {
        return "Preparing Pizza 🍕";
    }
}

class Burger implements Food {
    public String prepare() {
        return "Preparing Burger 🍔";
    }
}

// Client Code – tightly coupled
public class FoodApp {
    public static void main(String[] args) {
        String order = "pizza";
        Food food;

        if (order.equalsIgnoreCase("pizza")) {
            food = new Pizza();
        } else if (order.equalsIgnoreCase("burger")) {
            food = new Burger();
        } else {
            throw new IllegalArgumentException("We don't serve that!");
        }

        System.out.println(food.prepare());
    }
}


/**
 * 🔴 Problems:
 * Restaurant class is doing object creation (not its job!)
 * Adding new food (Pasta) needs modifying this code. Violates Open/Closed Principle
 * Code is harder to test and extend
 */
