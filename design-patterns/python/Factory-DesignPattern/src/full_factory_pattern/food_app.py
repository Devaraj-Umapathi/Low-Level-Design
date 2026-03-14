"""
Full Factory Pattern: abstract factory + concrete factories.
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


class FoodFactory(ABC):
    @abstractmethod
    def create_food(self) -> Food:
        pass


class PizzaFactory(FoodFactory):
    def create_food(self) -> Food:
        return Pizza()


class BurgerFactory(FoodFactory):
    def create_food(self) -> Food:
        return Burger()


def main():
    factory: FoodFactory = PizzaFactory()
    food = factory.create_food()
    print(food.prepare())

    factory = BurgerFactory()
    food2 = factory.create_food()
    print(food2.prepare())


if __name__ == "__main__":
    main()
