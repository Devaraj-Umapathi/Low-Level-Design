"""Milk coffee - separate class (no decorator)."""
from .coffee import Coffee


class MilkCoffee(Coffee):
    def get_description(self) -> str:
        return "Simple Coffee + Milk"

    def get_cost(self) -> float:
        return 5.0 + 1.5
