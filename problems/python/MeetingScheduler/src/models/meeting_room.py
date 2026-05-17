from threading import Lock

from models.interval import Interval


class MeetingRoom:
    def __init__(self, room_id: int, capacity: int) -> None:
        self.id = room_id
        self.capacity = capacity
        self._blocked_intervals: list[Interval] = []
        self._lock = Lock()

    def check_and_book_interval(self, requested_capacity: int, interval: Interval) -> bool:
        with self._lock:
            if self._is_available(requested_capacity, interval):
                self._blocked_intervals.append(interval)
                self._blocked_intervals.sort(key=lambda value: (value.start_time, value.end_time))
                return True
            return False

    def _is_available(self, requested_capacity: int, interval: Interval) -> bool:
        if requested_capacity > self.capacity:
            return False

        for current in self._blocked_intervals:
            if current.start_time >= interval.end_time:
                break
            if current.is_overlap(interval):
                return False
        return True

    def release_interval(self, interval: Interval) -> None:
        with self._lock:
            for idx, current in enumerate(self._blocked_intervals):
                if current == interval:
                    del self._blocked_intervals[idx]
                    break

    def get_blocked_intervals(self) -> list[Interval]:
        with self._lock:
            return list(self._blocked_intervals)
