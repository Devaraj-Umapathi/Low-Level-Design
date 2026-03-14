"""IndianCar - implements Car interface."""
from .car import Car


class IndianCar(Car):
    def __init__(self, name: str, brand: str, price: int):
        self._name = name
        self._brand = brand
        self._price = price

    def get_name(self) -> str:
        return self._name

    def get_brand(self) -> str:
        return self._brand

    def get_price(self) -> int:
        return self._price
