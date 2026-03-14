"""Bank transfer payment strategy."""
from .payment_strategy import PaymentStrategy


class BankTransferPayment(PaymentStrategy):
    def pay(self, amount: int) -> None:
        print(f"Paid ${amount} using Bank Transfer.")
