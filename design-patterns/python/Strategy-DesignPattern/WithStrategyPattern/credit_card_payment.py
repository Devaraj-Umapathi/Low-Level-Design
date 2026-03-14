"""Credit card payment strategy."""
from .payment_strategy import PaymentStrategy


class CreditCardPayment(PaymentStrategy):
    def pay(self, amount: int) -> None:
        print(f"Paid ${amount} using Credit Card.")
