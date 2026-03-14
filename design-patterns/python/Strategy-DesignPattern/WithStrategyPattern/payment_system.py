"""Context that uses a payment strategy."""
from typing import Optional
from .payment_strategy import PaymentStrategy


class PaymentSystem:
    def __init__(self) -> None:
        self._strategy: Optional[PaymentStrategy] = None

    def set_payment_strategy(self, strategy: PaymentStrategy) -> None:
        self._strategy = strategy

    def execute_payment(self, amount: int) -> None:
        if self._strategy is None:
            raise RuntimeError("Payment strategy not set")
        self._strategy.pay(amount)
