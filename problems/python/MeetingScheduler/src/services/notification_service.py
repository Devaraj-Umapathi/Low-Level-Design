from datetime import datetime
from itertools import count
from threading import Lock

from enums.notification_type import NotificationType
from models.meeting import Meeting
from models.notification import Notification
from models.user import User


class NotificationService:
    _ids = count(1)
    _id_lock = Lock()

    def send_notifications(
        self, notification_type: NotificationType, participants: list[User], meeting: Meeting
    ) -> None:
        with NotificationService._id_lock:
            notification_id = next(NotificationService._ids)

        notification = Notification(
            notification_id=notification_id,
            notification_type=notification_type,
            content=f"{notification_type.subject_prefix}: {meeting.subject}",
            creation_date=datetime.now(),
        )
        for participant in participants:
            notification.send(participant, meeting)
