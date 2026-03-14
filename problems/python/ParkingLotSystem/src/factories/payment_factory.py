"""Factory for payment strategy by payment mode."""
from enums.payment_mode import PaymentMode
from strategy.payment.payment_strategy import PaymentStrategy
from strategy.payment.cash_payment_strategy import CashPaymentStrategy
from strategy.payment.card_payment_strategy import CardPaymentStrategy


class PaymentFactory:
    @staticmethod
    def get(mode: PaymentMode) -> PaymentStrategy:
        if mode is None:
            raise ValueError("PaymentMode cannot be null")
        if mode == PaymentMode.CASH:
            return CashPaymentStrategy()
        if mode == PaymentMode.CARD:
            return CardPaymentStrategy()
        raise ValueError(f"Unknown PaymentMode: {mode}")
