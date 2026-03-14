"""Milk and sugar coffee - separate class (no decorator)."""
from .coffee import Coffee


class MilkSugarCoffee(Coffee):
    def get_description(self) -> str:
        return "Simple Coffee + Milk + Sugar"

    def get_cost(self) -> float:
        return 5.0 + 1.5 + 0.5
