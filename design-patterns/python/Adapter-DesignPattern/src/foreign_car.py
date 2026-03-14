"""ForeignCar - adaptee with incompatible interface."""


class ForeignCar:
    def __init__(self, car_name: str, car_brand: str, brand_price: int):
        self._car_name = car_name
        self._car_brand = car_brand
        self._brand_price = brand_price

    def get_car_name(self) -> str:
        return self._car_name

    def get_car_brand(self) -> str:
        return self._car_brand

    def get_brand_price(self) -> int:
        return self._brand_price
