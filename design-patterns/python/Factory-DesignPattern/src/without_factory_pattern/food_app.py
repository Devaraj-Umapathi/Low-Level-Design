"""
Without Factory: client creates objects directly. Violates Open/Closed.
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


def main():
    order = "pizza"
    if order.lower() == "pizza":
        food = Pizza()
    elif order.lower() == "burger":
        food = Burger()
    else:
        raise ValueError("We don't serve that!")

    print(food.prepare())


if __name__ == "__main__":
    main()
