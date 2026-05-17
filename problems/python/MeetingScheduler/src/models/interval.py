from dataclasses import dataclass
from datetime import datetime


@dataclass(frozen=True, order=True)
class Interval:
    start_time: datetime
    end_time: datetime

    def __post_init__(self) -> None:
        if not self.start_time < self.end_time:
            raise ValueError(
                "Interval end must be after start "
                f"(got start={self.start_time}, end={self.end_time})"
            )

    def is_overlap(self, interval: "Interval") -> bool:
        return self.start_time < interval.end_time and interval.start_time < self.end_time
