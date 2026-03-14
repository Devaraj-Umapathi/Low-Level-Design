"""Concrete Subject."""
from .product_launch_notifier import ProductLaunchNotifier
from .customer import Customer


class IPhoneLaunchNotifier(ProductLaunchNotifier):
    def __init__(self, product_name: str):
        self._subscribers: list[Customer] = []
        self._product_name = product_name

    def subscribe(self, customer: Customer) -> None:
        self._subscribers.append(customer)

    def unsubscribe(self, customer: Customer) -> None:
        self._subscribers.remove(customer)

    def notify_customers(self) -> None:
        print(f"\n🔔 Amazon: {self._product_name} is now available for purchase!")
        for customer in self._subscribers:
            customer.notify(self._product_name)
