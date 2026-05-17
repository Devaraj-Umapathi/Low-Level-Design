from enums.rsvp_status import RSVPStatus
from models.calendar import Calendar
from models.interval import Interval
from models.meeting import Meeting


class User:
    def __init__(self, name: str, email: str) -> None:
        self.name = name
        self.email = email
        self._calendar = Calendar()

    def add_meeting_to_calendar(self, meeting: Meeting) -> None:
        self._calendar.add_meeting(meeting)

    def remove_meeting_from_calendar(self, meeting: Meeting) -> None:
        self._calendar.remove_meeting(meeting)

    def respond_invitation(self, meeting: Meeting, response: RSVPStatus) -> None:
        meeting.update_participant_status(self, response)
        print(f"  · {self.name} responded: {response.value}")
        if response == RSVPStatus.REJECTED:
            self._calendar.remove_meeting(meeting)

    def has_conflict(self, interval: Interval) -> bool:
        return self._calendar.has_conflict(interval)

    def __hash__(self) -> int:
        return hash((self.name, self.email))
