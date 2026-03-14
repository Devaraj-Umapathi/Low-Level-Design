package WithFactoryPattern;

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

// Factory Class
class FoodFactory {
    public static Food getFood(String order) {
        Food food;
        if (order.equalsIgnoreCase("pizza")) {
            food = new Pizza();
        } else if (order.equalsIgnoreCase("burger")) {
            food = new Burger();
        } else {
            throw new IllegalArgumentException("We don't serve that!");
        }
        return food;
    }
}

// Client Code
public class FoodApp {
    public static void main(String[] args) {
        String order = "pizza";
        Food food = FoodFactory.getFood(order);
        System.out.println(food.prepare());
    }
}

/**
 * 🟡 Improvements:
 * Client (Restaurant) no longer creates objects
 * Centralized creation logic
 * Still violates Open/Closed Principle — factory must be modified when a new item is added
 */