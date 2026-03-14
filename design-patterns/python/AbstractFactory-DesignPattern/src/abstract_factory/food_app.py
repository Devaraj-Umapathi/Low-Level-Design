"""
Abstract Factory Pattern - Python
Product interfaces and concrete products, factory and client.
"""

from abc import ABC, abstractmethod


# Product interfaces
class MainItem(ABC):
    @abstractmethod
    def prepare(self) -> str:
        pass


class SideItem(ABC):
    @abstractmethod
    def prepare(self) -> str:
        pass


# Concrete Products - Pizza Meal
class Pizza(MainItem):
    def prepare(self) -> str:
        return "Preparing Pizza 🍕"


class GarlicBread(SideItem):
    def prepare(self) -> str:
        return "Preparing Garlic Bread 🧄🍞"


# Concrete Products - Burger Meal
class Burger(MainItem):
    def prepare(self) -> str:
        return "Preparing Burger 🍔"


class Fries(SideItem):
    def prepare(self) -> str:
        return "Preparing Fries 🍟"


# Factory class
class MealFactory(ABC):
    @abstractmethod
    def create_main_item(self) -> MainItem:
        pass

    @abstractmethod
    def create_side_item(self) -> SideItem:
        pass


# Concrete Factories
class PizzaMealFactory(MealFactory):
    def create_main_item(self) -> MainItem:
        return Pizza()

    def create_side_item(self) -> SideItem:
        return GarlicBread()


class BurgerMealFactory(MealFactory):
    def create_main_item(self) -> MainItem:
        return Burger()

    def create_side_item(self) -> SideItem:
        return Fries()


# Client Code
def main():
    factory: MealFactory = PizzaMealFactory()  # or BurgerMealFactory()

    main_item = factory.create_main_item()
    side_item = factory.create_side_item()

    print(main_item.prepare())
    print(side_item.prepare())


if __name__ == "__main__":
    main()
