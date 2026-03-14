"""Cash payment strategy."""
from __future__ import annotations
from .payment_strategy import PaymentStrategy


class CashPaymentStrategy(PaymentStrategy):
    def pay(self, fee: float, ticket: "ParkingTicket") -> bool:
        print(f"Paid ₹{fee} for ticket {ticket.get_ticket_id()} via Cash.")
        return True
