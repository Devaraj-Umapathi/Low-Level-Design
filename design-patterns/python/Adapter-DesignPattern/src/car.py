"""Car interface (target)."""
from abc import ABC, abstractmethod


class Car(ABC):
    @abstractmethod
    def get_name(self) -> str:
        pass

    @abstractmethod
    def get_brand(self) -> str:
        pass

    @abstractmethod
    def get_price(self) -> int:
        pass
