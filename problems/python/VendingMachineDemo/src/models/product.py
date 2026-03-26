"""Product entity for vending machine."""
from dataclasses import dataclass
from enums.product_type import ProductType


@dataclass(frozen=True)
class Product:
    product_id: int
    name: str
    price: float
    product_type: ProductType
