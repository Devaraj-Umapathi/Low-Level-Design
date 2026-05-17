# Meeting Scheduler - Python LLD

Python version of the Java Meeting Scheduler under [problems/java/MeetingScheduler](../../java/MeetingScheduler), with matching entities and service flow.

For full design discussion and Java details, see the [Java README](../../java/MeetingScheduler/README.md).

---

## Layout

```text
src/
├── enums/
│   ├── notification_type.py
│   └── rsvp_status.py
├── models/
│   ├── calendar.py
│   ├── interval.py
│   ├── meeting.py
│   ├── meeting_room.py
│   ├── notification.py
│   └── user.py
├── services/
│   ├── meeting_scheduler.py
│   └── notification_service.py
└── main.py
```

---

## How to run

From this directory:

```bash
cd problems/python/MeetingScheduler
PYTHONPATH=src python3 src/main.py
```

---

## Notes vs Java

- Uses snake_case naming, but keeps Java-equivalent behavior and flow.
- Scheduler remains a singleton (`MeetingScheduler.get_instance()`).
- Room and calendar data structures are protected with locks for thread-safe updates.
- Demo scenarios mirror Java driver: schedule, RSVP, participant updates, conflict check, cancel.
