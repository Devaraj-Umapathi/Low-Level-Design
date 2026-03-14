"""Delegates payment to a configured strategy (used at exit)."""
from __future__ import annotations
from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from model.parking_ticket import ParkingTicket
    from strategy.payment.payment_strategy import PaymentStrategy


class PaymentService:
    def __init__(self, strategy: "PaymentStrategy") -> None:
        self._strategy = strategy

    def pay(self, ticket: "ParkingTicket", fee: float) -> bool:
        success = self._strategy.pay(fee, ticket)
        if not success:
            print(f"Payment failed for ticket: {ticket.get_ticket_id()}")
        return success
