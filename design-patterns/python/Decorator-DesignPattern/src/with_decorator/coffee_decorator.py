"""Base decorator for Coffee."""
from abc import ABC
from .coffee import Coffee


class CoffeeDecorator(Coffee, ABC):
    def __init__(self, coffee: Coffee):
        self._coffee = coffee

    def get_description(self) -> str:
        return self._coffee.get_description()

    def get_cost(self) -> float:
        return self._coffee.get_cost()
