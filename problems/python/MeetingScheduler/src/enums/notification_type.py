from enum import Enum


class NotificationType(Enum):
    INVITE = ("Invite", "Meeting Invitation")
    CANCEL = ("Cancel", "Meeting Cancelled")

    def __init__(self, label: str, subject_prefix: str) -> None:
        self.label = label
        self.subject_prefix = subject_prefix
