"""Without strategy pattern demo."""


def main():
    try:
        from .payment_system import PaymentSystem
    except ImportError:
        from payment_system import PaymentSystem

    system = PaymentSystem()
    system.process_payment("CREDIT_CARD", 100)
    system.process_payment("PAYPAL", 50)


if __name__ == "__main__":
    main()
