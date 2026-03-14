"""Adapter: makes ForeignCar work as Car."""
from .car import Car
from .foreign_car import ForeignCar


class ForeignCarAdapter(Car):
    def __init__(self, foreign_car: ForeignCar):
        self._foreign_car = foreign_car

    def get_name(self) -> str:
        return self._foreign_car.get_car_name()

    def get_brand(self) -> str:
        return self._foreign_car.get_car_brand()

    def get_price(self) -> int:
        return self._foreign_car.get_brand_price()
