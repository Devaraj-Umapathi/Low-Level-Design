"""Observer interface."""
from abc import ABC, abstractmethod


class Customer(ABC):
    @abstractmethod
    def notify(self, product_name: str) -> None:
        pass
