"""Concrete Observer."""
from .customer import Customer


class AmazonUser(Customer):
    def __init__(self, name: str):
        self._name = name

    def notify(self, product_name: str) -> None:
        print(f'{self._name}, your requested product "{product_name}" is now live on Amazon!')
