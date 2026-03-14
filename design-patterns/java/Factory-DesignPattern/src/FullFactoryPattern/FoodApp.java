package FullFactoryPattern;

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

// Factory class
abstract class FoodFactory {
    abstract Food createFood();
}

// Concrete Factories
class PizzaFactory extends FoodFactory {
    public Food createFood() {
        return new Pizza();
    }
}

class BurgerFactory extends FoodFactory {
    public Food createFood() {
        return new Burger();
    }
}

// Client Code
public class FoodApp {
    public static void main(String[] args) {
        FoodFactory factory = new PizzaFactory(); // can swap to BurgerFactory, etc.
        Food food = factory.createFood();
        System.out.println(food.prepare());

        factory = new BurgerFactory();
        Food food2 = factory.createFood();
        System.out.println(food2.prepare());
    }
}

