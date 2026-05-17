from threading import Lock
from typing import Optional

from enums.notification_type import NotificationType
from enums.rsvp_status import RSVPStatus
from models.interval import Interval
from models.meeting import Meeting
from models.meeting_room import MeetingRoom
from models.user import User
from services.notification_service import NotificationService


class MeetingScheduler:
    _instance: Optional["MeetingScheduler"] = None
    _instance_lock = Lock()

    def __init__(self) -> None:
        self._meeting_rooms: list[MeetingRoom] = []
        self._rooms_lock = Lock()
        self._notification_service = NotificationService()

    @classmethod
    def get_instance(cls) -> "MeetingScheduler":
        if cls._instance is None:
            with cls._instance_lock:
                if cls._instance is None:
                    cls._instance = cls()
        return cls._instance

    def add_rooms(self, rooms: list[MeetingRoom]) -> None:
        with self._rooms_lock:
            self._meeting_rooms.extend(rooms)
            self._meeting_rooms.sort(key=lambda room: room.capacity)

    def schedule_meeting(
        self, organizer: User, participants: list[User], interval: Interval, subject: str
    ) -> Optional[Meeting]:
        capacity = len(participants) + 1
        room = self._book_meeting_room(interval, capacity)
        if room is None:
            print("Meeting Room not available at the given interval to schedule the meeting")
            return None

        meeting = Meeting(subject, organizer, participants, room, interval)
        for participant in participants:
            participant.add_meeting_to_calendar(meeting)

        organizer.add_meeting_to_calendar(meeting)
        organizer.respond_invitation(meeting, RSVPStatus.ACCEPTED)

        self._notification_service.send_notifications(
            NotificationType.INVITE, participants, meeting
        )
        print("Meeting Scheduled Successfully")
        return meeting

    def cancel_meeting(self, meeting: Meeting) -> None:
        if not meeting.active:
            print("Meeting already cancelled")
            return

        meeting.active = False
        meeting.booked_room.release_interval(meeting.booked_interval)
        participants = meeting.get_all_participants()
        for attendee in participants:
            attendee.remove_meeting_from_calendar(meeting)
        meeting.organizer.remove_meeting_from_calendar(meeting)

        self._notification_service.send_notifications(
            NotificationType.CANCEL, participants, meeting
        )
        print("Meeting Cancelled Successfully")

    def add_participants(self, meeting: Meeting, participants: list[User]) -> None:
        if not meeting.active:
            print("Cannot add participants — meeting is cancelled")
            return

        for participant in participants:
            meeting.add_participant(participant)
            participant.add_meeting_to_calendar(meeting)

        self._notification_service.send_notifications(
            NotificationType.INVITE, participants, meeting
        )
        print("Participants Added Successfully")

    def remove_participants(self, meeting: Meeting, participants: list[User]) -> None:
        if not meeting.active:
            print("Cannot remove participants — meeting is cancelled")
            return

        for participant in participants:
            meeting.remove_participant(participant)
            participant.remove_meeting_from_calendar(meeting)

        self._notification_service.send_notifications(
            NotificationType.CANCEL, participants, meeting
        )
        print("Participants Removed Successfully")

    @staticmethod
    def get_participants_with_conflicts(
        participants: list[User], interval: Interval
    ) -> list[User]:
        conflicted_users: list[User] = []
        for candidate in participants:
            if candidate.has_conflict(interval):
                conflicted_users.append(candidate)
        return conflicted_users

    def _book_meeting_room(self, interval: Interval, capacity: int) -> Optional[MeetingRoom]:
        with self._rooms_lock:
            rooms = list(self._meeting_rooms)
        for room in rooms:
            if room.check_and_book_interval(capacity, interval):
                print(f"Room : {room.id} booked successfully")
                return room
        return None
