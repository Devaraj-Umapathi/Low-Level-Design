"""Inventory holds fixed racks."""
from __future__ import annotations
from models.rack import Rack


class Inventory:
    def __init__(self, rack_count: int, max_units_per_rack: int) -> None:
        if rack_count <= 0:
            raise ValueError("rack_count must be positive")
        self._racks: dict[int, Rack] = {
            i: Rack(i, max_units_per_rack) for i in range(1, rack_count + 1)
        }

    def get_rack_count(self) -> int:
        return len(self._racks)

    def get_rack(self, rack_number: int) -> Rack | None:
        return self._racks.get(rack_number)
