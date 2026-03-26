"""Rack stores one product type and quantity."""
from __future__ import annotations
from typing import Optional
from models.product import Product


class Rack:
    def __init__(self, rack_number: int, max_capacity: int) -> None:
        if max_capacity <= 0:
            raise ValueError("max_capacity must be positive")
        self._rack_number = rack_number
        self._max_capacity = max_capacity
        self._product: Optional[Product] = None
        self._quantity = 0

    def load_product(self, product: Product, quantity: int) -> None:
        self._product = product
        self._quantity = quantity

    def restock(self, restock_quantity: int) -> bool:
        if restock_quantity + self._quantity > self._max_capacity:
            return False
        self._quantity += restock_quantity
        return True

    def dispense_one(self) -> None:
        if self._quantity > 0:
            self._quantity -= 1

    def get_rack_number(self) -> int:
        return self._rack_number

    def get_max_capacity(self) -> int:
        return self._max_capacity

    def get_product(self) -> Optional[Product]:
        return self._product

    def get_quantity(self) -> int:
        return self._quantity
