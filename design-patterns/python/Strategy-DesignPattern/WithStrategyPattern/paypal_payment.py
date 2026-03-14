"""PayPal payment strategy."""
from .payment_strategy import PaymentStrategy


class PayPalPayment(PaymentStrategy):
    def pay(self, amount: int) -> None:
        print(f"Paid ${amount} using PayPal.")
