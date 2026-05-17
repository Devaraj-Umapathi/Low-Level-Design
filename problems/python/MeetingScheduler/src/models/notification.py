from dataclasses import dataclass
from datetime import datetime

from enums.notification_type import NotificationType


@dataclass(frozen=True)
class Notification:
    notification_id: int
    notification_type: NotificationType
    content: str
    creation_date: datetime

    def send(self, user: "User", meeting: "Meeting") -> None:
        print(
            f"[{self.notification_id}] {self.notification_type.label} to {user.name}"
            f" <{user.email}> — {self.content} ({meeting.subject}) @ {self.creation_date}"
        )
