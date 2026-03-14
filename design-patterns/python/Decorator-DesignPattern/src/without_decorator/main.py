"""Without decorator: a new class for each combination."""
from .simple_coffee import SimpleCoffee
from .milk_coffee import MilkCoffee
from .milk_sugar_coffee import MilkSugarCoffee


def main():
    coffee = SimpleCoffee()
    print(f"{coffee.get_description()} ${coffee.get_cost()}")

    coffee = MilkCoffee()
    print(f"{coffee.get_description()} ${coffee.get_cost()}")

    coffee = MilkSugarCoffee()
    print(f"{coffee.get_description()} ${coffee.get_cost()}")


if __name__ == "__main__":
    main()

