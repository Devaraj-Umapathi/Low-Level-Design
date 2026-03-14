"""Demo: Adapter pattern - unified Car interface."""
from .car_dealer_client import CarDealerClient


def main():
    client = CarDealerClient()
    cars = client.get_car_list()
    for car in cars:
        print(f"{car.get_name()} -> {car.get_brand()} -> {car.get_price()}")


if __name__ == "__main__":
    main()
