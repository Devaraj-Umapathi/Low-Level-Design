"""Card payment strategy."""
from __future__ import annotations
from .payment_strategy import PaymentStrategy


class CardPaymentStrategy(PaymentStrategy):
    def pay(self, fee: float, ticket: "ParkingTicket") -> bool:
        print(f"Paid ₹{fee} for ticket {ticket.get_ticket_id()} via Card.")
        return True
