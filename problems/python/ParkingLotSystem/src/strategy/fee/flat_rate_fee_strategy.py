"""Flat rate fee strategy."""
from model.parking_ticket import ParkingTicket
from strategy.fee.fee_strategy import FeeStrategy


class FlatRateFeeStrategy(FeeStrategy):
    def __init__(self, rate_per_hour: float = 5.0):
        self._rate_per_hour = rate_per_hour

    def calculate_fee(self, ticket: ParkingTicket) -> float:
        hours = ticket.get_duration_in_hours()
        return max(0, hours * self._rate_per_hour)
