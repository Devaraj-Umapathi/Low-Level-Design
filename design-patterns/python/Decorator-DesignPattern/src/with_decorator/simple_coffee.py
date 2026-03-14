"""Concrete component."""
from .coffee import Coffee


class SimpleCoffee(Coffee):
    def get_description(self) -> str:
        return "Simple Coffee"

    def get_cost(self) -> float:
        return 5.0
