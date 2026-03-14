"""
Factory Method Pattern - Python
Centralized object creation.
"""
from abc import ABC, abstractmethod


class Food(ABC):
    @abstractmethod
    def prepare(self) -> str:
        pass


class Pizza(Food):
    def prepare(self) -> str:
        return "Preparing Pizza 🍕"


class Burger(Food):
    def prepare(self) -> str:
        return "Preparing Burger 🍔"


class FoodFactory:
    @staticmethod
    def get_food(order: str) -> Food:
        order_lower = order.lower()
        if order_lower == "pizza":
            return Pizza()
        if order_lower == "burger":
            return Burger()
        raise ValueError("We don't serve that!")


def main():
    order = "pizza"
    food = FoodFactory.get_food(order)
    print(food.prepare())


if __name__ == "__main__":
    main()
