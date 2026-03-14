"""Client that works with Car interface (Indian and adapted Foreign)."""
from .car import Car
from .indian_car import IndianCar
from .foreign_car import ForeignCar
from .foreign_car_adapter import ForeignCarAdapter


class CarDealerClient:
    def get_car_list(self) -> list[Car]:
        car_list: list[Car] = []

        indian_car1 = IndianCar("Punch", "Tata", 1_000_000)
        foreign_car1 = ForeignCarAdapter(ForeignCar("Spectre", "Rolls", 10_000_000))

        car_list.append(indian_car1)
        car_list.append(foreign_car1)

        return car_list
