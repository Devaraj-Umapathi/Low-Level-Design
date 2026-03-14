"""Subject interface."""
from abc import ABC, abstractmethod


class ProductLaunchNotifier(ABC):
    @abstractmethod
    def subscribe(self, customer: "Customer") -> None:
        pass

    @abstractmethod
    def unsubscribe(self, customer: "Customer") -> None:
        pass

    @abstractmethod
    def notify_customers(self) -> None:
        pass
