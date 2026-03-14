"""Coffee component interface."""
from abc import ABC, abstractmethod


class Coffee(ABC):
    @abstractmethod
    def get_description(self) -> str:
        pass

    @abstractmethod
    def get_cost(self) -> float:
        pass
