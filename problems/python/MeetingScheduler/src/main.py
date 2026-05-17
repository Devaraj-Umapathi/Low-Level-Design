from datetime import datetime
import random

from enums.rsvp_status import RSVPStatus
from models.interval import Interval
from models.meeting_room import MeetingRoom
from models.user import User
from services.meeting_scheduler import MeetingScheduler


def simulate_user_responses(users: list[User], meeting) -> None:
    random.seed(7)
    for user in users:
        response = random.choice([RSVPStatus.ACCEPTED, RSVPStatus.REJECTED])
        user.respond_invitation(meeting, response)


def header(title: str) -> None:
    print("\n==============================")
    print(f"▶ {title}")
    print("==============================\n")


def arrow(message: str) -> None:
    print(f"→ {message}")


def format_names(users: list[User]) -> str:
    return ", ".join(user.name for user in users)


def main() -> None:
    try:
        scheduler = MeetingScheduler.get_instance()

        room_a = MeetingRoom(1, 4)
        room_b = MeetingRoom(2, 8)
        scheduler.add_rooms([room_a, room_b])

        maya = User("Maya", "maya@example.com")
        rohan = User("Rohan", "rohan@example.com")
        elena = User("Elena", "elena@example.com")
        samir = User("Samir", "samir@example.com")

        header("Scenario 1: Schedule a Meeting")
        arrow('Scheduling "Design Review" for Rohan, Samir with Maya as organizer...')
        participants_1 = [rohan, samir]
        interval_1 = Interval(
            datetime(2026, 7, 10, 10, 0),
            datetime(2026, 7, 10, 11, 0),
        )
        meeting_1 = scheduler.schedule_meeting(
            maya, participants_1, interval_1, "Design Review"
        )
        if meeting_1 is None:
            raise RuntimeError("Failed to schedule Design Review")
        simulate_user_responses(participants_1, meeting_1)

        header("Scenario 2: Fix Participants")
        arrow("Removing Samir (added by mistake)...")
        scheduler.remove_participants(meeting_1, [samir])
        arrow("Adding Elena (correct participant)...")
        scheduler.add_participants(meeting_1, [elena])

        header("Scenario 3: Advisory Conflict Check")
        interval = Interval(
            datetime(2026, 7, 10, 10, 30),
            datetime(2026, 7, 10, 11, 30),
        )
        current_participants = [rohan, elena, samir]
        conflicted = scheduler.get_participants_with_conflicts(
            current_participants, interval
        )
        arrow(f"Users with calendar overlap for 10:30–11:30: {format_names(conflicted)}")

        header("Scenario 4: Cancel Meeting")
        arrow('Cancelling "Design Review"...')
        scheduler.cancel_meeting(meeting_1)

        header("Summary")
        print("✔ All scenarios completed")
        print(
            "✔ Demonstrated: scheduling, RSVP, participant changes, "
            "conflict check, cancellation, validation"
        )
    except Exception as error:  # noqa: BLE001
        print(f"✗ Unexpected error: {error}")
        raise


if __name__ == "__main__":
    main()
