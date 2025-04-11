package AbstractFactory;

// Product Interface
interface MainItem {
    String prepare();
}

interface SideItem {
    String prepare();
}


// Concrete Products
// Pizza Meal
class Pizza implements MainItem {
    public String prepare() {
        return "Preparing Pizza 🍕";
    }
}

class GarlicBread implements SideItem {
    public String prepare() {
        return "Preparing Garlic Bread 🧄🍞";
    }
}

// Burger Meal
class Burger implements MainItem {
    public String prepare() {
        return "Preparing Burger 🍔";
    }
}

class Fries implements SideItem {
    public String prepare() {
        return "Preparing Fries 🍟";
    }
}


// Factory class
abstract class MealFactory {
    abstract MainItem createMainItem();
    abstract SideItem createSideItem();
}


// Concrete Factories
class PizzaMealFactory extends MealFactory {
    public MainItem createMainItem() {
        return new Pizza();
    }

    public SideItem createSideItem() {
        return new GarlicBread();
    }
}

class BurgerMealFactory extends MealFactory {
    public MainItem createMainItem() {
        return new Burger();
    }

    public SideItem createSideItem() {
        return new Fries();
    }
}


// Client Code
public class FoodApp {
    public static void main(String[] args) {
        MealFactory factory = new PizzaMealFactory(); // or BurgerMealFactory

        MainItem main = factory.createMainItem();
        SideItem side = factory.createSideItem();

        System.out.println(main.prepare());
        System.out.println(side.prepare());
    }
}

