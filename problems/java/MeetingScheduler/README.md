# Meeting Scheduler - Low Level Design (LLD)

A Java implementation of a meeting scheduler system that supports room booking, calendar conflict checks, participant management, RSVP updates, and notifications.

---

## 1. Problem Overview

The scheduler coordinates meetings between users while respecting:

- Room capacity constraints
- Time interval overlaps
- User calendar conflicts (advisory)
- RSVP lifecycle
- Invite/cancel notifications

This design focuses on clear object modeling and separation of concerns for interview-style LLD discussions.

---

## 2. Features Implemented

- Create meetings with organizer, participants, subject, and interval
- Auto-pick the first suitable room (sorted by capacity)
- Add/remove participants after meeting creation
- Track RSVP status (`PENDING`, `ACCEPTED`, `REJECTED`)
- Cancel meetings and release booked room intervals
- Send invite/cancel notifications
- Query which users have conflicts for a candidate interval

---

## 3. Package Structure

```text
src/
├── enums/
│   ├── NotificationType.java
│   └── RSVPStatus.java
├── models/
│   ├── Calendar.java
│   ├── Interval.java
│   ├── Meeting.java
│   ├── MeetingRoom.java
│   ├── Notification.java
│   └── User.java
├── services/
│   ├── MeetingScheduler.java
│   └── NotificationService.java
└── main/
    └── Driver.java
```

---

## 4. Core Design Choices

- **Singleton scheduler**: `MeetingScheduler` uses double-checked locking for a single orchestrator instance.
- **Room booking model**: each `MeetingRoom` stores blocked intervals in a sorted set and checks overlap before booking.
- **Calendar model**: each `User` owns a `Calendar` that keeps meetings sorted by start time for efficient conflict checks.
- **Status tracking**: `Meeting` keeps participant RSVP state in a thread-safe map.
- **Notification abstraction**: `NotificationService` builds notifications and dispatches to attendees.

Patterns used: Singleton (scheduler), basic service-layer orchestration, and domain-driven entities.

---

## 5. How It Works (Flow)

1. `Driver` creates users, rooms, and intervals.
2. `MeetingScheduler.scheduleMeeting(...)` selects a room and creates a `Meeting`.
3. Organizer and participants get the meeting added to calendars.
4. Invite notifications are sent.
5. Users respond with RSVP statuses.
6. Later operations (add/remove participants, cancel) update meeting state, calendars, room bookings, and notifications.

---

## 6. How to Run

Prerequisite: Java 17+ (or compatible JDK).

```bash
cd problems/java/MeetingScheduler
mkdir -p out
javac -d out src/enums/*.java src/models/*.java src/services/*.java src/main/Driver.java
java -cp out main.Driver
```

---

## 7. Notes

- Conflict checking via `getParticipantsWithConflicts(...)` is advisory and separate from room booking.
- If a participant rejects, the meeting is removed from that user calendar in this demo model.
- Room assignment currently uses first-fit by ascending room capacity.

For a Python port with equivalent structure, see [problems/python/MeetingScheduler](../../python/MeetingScheduler).
