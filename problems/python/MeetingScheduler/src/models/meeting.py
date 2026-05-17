from itertools import count
from threading import Lock

from enums.rsvp_status import RSVPStatus
from models.interval import Interval
from models.meeting_room import MeetingRoom


class Meeting:
    _next_id = count(1)
    _id_lock = Lock()

    def __init__(
        self,
        subject: str,
        organizer: "User",
        participants: list["User"],
        room: MeetingRoom,
        interval: Interval,
    ) -> None:
        with Meeting._id_lock:
            self.id = next(Meeting._next_id)
        self.subject = subject
        self.organizer = organizer
        self.booked_room = room
        self.booked_interval = interval
        self.active = True
        self._participant_status: dict["User", RSVPStatus] = {
            participant: RSVPStatus.PENDING for participant in participants
        }
        self._status_lock = Lock()

    def add_participant(self, participant: "User") -> None:
        with self._status_lock:
            self._participant_status.setdefault(participant, RSVPStatus.PENDING)

    def remove_participant(self, participant: "User") -> None:
        with self._status_lock:
            self._participant_status.pop(participant, None)

    def update_participant_status(self, participant: "User", status: RSVPStatus) -> None:
        with self._status_lock:
            if participant in self._participant_status:
                self._participant_status[participant] = status

    def get_all_participants(self) -> list["User"]:
        with self._status_lock:
            return list(self._participant_status.keys())
