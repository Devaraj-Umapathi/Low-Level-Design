"""Strategy pattern demo."""
from .payment_system import PaymentSystem
from .credit_card_payment import CreditCardPayment
from .paypal_payment import PayPalPayment
from .bank_transfer_payment import BankTransferPayment


def main():
    system = PaymentSystem()

    system.set_payment_strategy(CreditCardPayment())
    system.execute_payment(100)

    system.set_payment_strategy(PayPalPayment())
    system.execute_payment(50)

    system.set_payment_strategy(BankTransferPayment())
    system.execute_payment(200)


if __name__ == "__main__":
    main()
