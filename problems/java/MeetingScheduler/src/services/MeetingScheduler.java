package services;

import enums.NotificationType;
import enums.RSVPStatus;
import models.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MeetingScheduler {

    private static volatile MeetingScheduler instance;
    private static final Object lock = new Object();

    private final NotificationService notificationService;
    private final List<MeetingRoom> meetingRooms;

    private MeetingScheduler() {
        meetingRooms = new ArrayList<>();
        notificationService = new NotificationService();
    }

    public static MeetingScheduler getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new MeetingScheduler();
                }
            }
        }
        return instance;
    }

    public void addRooms(List<MeetingRoom> rooms) {
        meetingRooms.addAll(rooms);
        meetingRooms.sort(Comparator.comparingInt(MeetingRoom::getCapacity));
    }

    public Optional<Meeting> scheduleMeeting(User organizer, List<User> participants, Interval interval, String subject) {

        int capacity = participants.size() + 1; // Participants + organizer
        MeetingRoom room = bookMeetingRoom(interval, capacity);
        if (room == null) {
            System.out.println("Meeting Room not available at the given interval to schedule the meeting");
            return Optional.empty();
        }

        Meeting meeting = new Meeting(subject, organizer, participants, room, interval);

        for (User participant : participants) {
            participant.addMeetingToCalendar(meeting);
        }

        organizer.addMeetingToCalendar(meeting);
        organizer.respondInvitation(meeting, RSVPStatus.ACCEPTED);

        notificationService.sendNotifications(NotificationType.INVITE, participants, meeting);

        System.out.println("Meeting Scheduled Successfully");

        return Optional.of(meeting);
    }

    public void cancelMeeting(Meeting meeting) {
        if (!meeting.isActive()) {
            System.out.println("Meeting already cancelled");
            return;
        }
        meeting.setActive(false);

        MeetingRoom room = meeting.getBookedRoom();
        Interval interval = meeting.getBookedInterval();
        room.releaseInterval(interval);

        List<User> participants = meeting.getAllParticipants();
        for (User attendee : participants) {
            attendee.removeMeetingFromCalendar(meeting);
        }
        meeting.getOrganizer().removeMeetingFromCalendar(meeting);

        notificationService.sendNotifications(NotificationType.CANCEL, participants, meeting);

        System.out.println("Meeting Cancelled Successfully");
    }

    public void addParticipants(Meeting meeting, List<User> participants) {
        if (!meeting.isActive()) {
            System.out.println("Cannot add participants — meeting is cancelled");
            return;
        }
        for (User participant : participants) {
            meeting.addParticipant(participant);
            participant.addMeetingToCalendar(meeting);
        }

        notificationService.sendNotifications(NotificationType.INVITE, participants, meeting);

        System.out.println("Participants Added Successfully");
    }

    public void removeParticipants(Meeting meeting, List<User> participants) {
        if (!meeting.isActive()) {
            System.out.println("Cannot remove participants — meeting is cancelled");
            return;
        }
        for (User participant : participants) {
            meeting.removeParticipant(participant);
            participant.removeMeetingFromCalendar(meeting);
        }

        notificationService.sendNotifications(NotificationType.CANCEL, participants, meeting);

        System.out.println("Participants Removed Successfully");
    }

    public List<User> getParticipantsWithConflicts(List<User> participants, Interval interval) {

        List<User> conflictedUsers = new ArrayList<>();
        for (User candidate : participants) {
            if (candidate.hasConflict(interval)) {
                conflictedUsers.add(candidate);
            }
        }
        return conflictedUsers;
    }

    private MeetingRoom bookMeetingRoom(Interval interval, int capacity) {
        for (MeetingRoom room : meetingRooms) {
            if (room.checkAndBookInterval(capacity, interval)) {
                System.out.println("Room : " + room.getId() + " booked successfully");
                return room;
            }
        }
        return null;
    }
}
