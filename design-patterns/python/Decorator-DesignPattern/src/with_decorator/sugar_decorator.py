"""Sugar decorator."""
from .coffee import Coffee
from .coffee_decorator import CoffeeDecorator


class SugarDecorator(CoffeeDecorator):
    def __init__(self, coffee: Coffee):
        super().__init__(coffee)

    def get_description(self) -> str:
        return self._coffee.get_description() + ", Sugar"

    def get_cost(self) -> float:
        return self._coffee.get_cost() + 0.5
