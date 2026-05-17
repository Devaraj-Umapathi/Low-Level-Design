from threading import Lock

from models.interval import Interval
from models.meeting import Meeting


class Calendar:
    def __init__(self) -> None:
        self._meetings: list[Meeting] = []
        self._lock = Lock()

    def add_meeting(self, meeting: Meeting) -> None:
        with self._lock:
            self._meetings.append(meeting)
            self._meetings.sort(key=lambda value: (value.booked_interval.start_time, value.id))

    def remove_meeting(self, meeting: Meeting) -> None:
        with self._lock:
            for idx, current in enumerate(self._meetings):
                if current.id == meeting.id:
                    del self._meetings[idx]
                    break

    def has_conflict(self, interval: Interval) -> bool:
        with self._lock:
            for meeting in self._meetings:
                if meeting.booked_interval.start_time >= interval.end_time:
                    break
                if meeting.booked_interval.is_overlap(interval):
                    return True
            return False

    def get_meetings(self) -> list[Meeting]:
        with self._lock:
            return list(self._meetings)
