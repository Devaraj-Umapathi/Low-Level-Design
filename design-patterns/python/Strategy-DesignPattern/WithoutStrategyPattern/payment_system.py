"""Without strategy: all logic in one place (if/else)."""


class PaymentSystem:
    def process_payment(self, payment_type: str, amount: int) -> None:
        t = payment_type.upper()
        if t == "CREDIT_CARD":
            print(f"Processing Credit Card payment of ${amount}")
        elif t == "PAYPAL":
            print(f"Processing PayPal payment of ${amount}")
        elif t == "BANK_TRANSFER":
            print(f"Processing Bank Transfer payment of ${amount}")
        elif t == "GPAY":
            print(f"Processing Gpay payment of ${amount}")
        else:
            print("Payment method not supported.")
