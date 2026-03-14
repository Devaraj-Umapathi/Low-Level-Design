"""Decorator pattern demo."""
from .coffee import Coffee
from .simple_coffee import SimpleCoffee
from .milk_decorator import MilkDecorator
from .sugar_decorator import SugarDecorator


def main():
    coffee: Coffee = SimpleCoffee()
    print(f"{coffee.get_description()} ${coffee.get_cost()}")

    coffee = MilkDecorator(coffee)
    print(f"{coffee.get_description()} ${coffee.get_cost()}")

    coffee = SugarDecorator(coffee)
    print(f"{coffee.get_description()} ${coffee.get_cost()}")


if __name__ == "__main__":
    main()
