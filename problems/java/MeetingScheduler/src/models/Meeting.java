package models;

import enums.RSVPStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Meeting {

    private final int id;
    private final String subject;
    private final User organizer;
    private final Map<User, RSVPStatus> participantStatus;
    private final MeetingRoom bookedRoom;
    private final Interval bookedInterval;
    private boolean active;
    private static final AtomicInteger nextId = new AtomicInteger(1);

    public Meeting(String subject, User organizer, List<User> participants, MeetingRoom room, Interval interval) {
        this.id = nextId.getAndIncrement();
        this.subject = subject;
        this.organizer = organizer;
        bookedRoom = room;
        bookedInterval = interval;
        active = true;
        participantStatus = new ConcurrentHashMap<>();

        for (User participant : participants) {
            participantStatus.put(participant, RSVPStatus.PENDING);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addParticipant(User participant) {
      participantStatus.putIfAbsent(participant, RSVPStatus.PENDING);
    }

    public void removeParticipant(User participant) {
        participantStatus.remove(participant);
    }

    public void updateParticipantStatus(User participant, RSVPStatus status) {
        participantStatus.computeIfPresent(participant, (k, v) -> status);
    }

    public List<User> getAllParticipants() {
        return new ArrayList<>(participantStatus.keySet());
    }

    public int getId() {
        return id;
    }

    public User getOrganizer() {
        return organizer;
    }

    public String getSubject() {
        return subject;
    }

    public MeetingRoom getBookedRoom() {
        return bookedRoom;
    }

    public Interval getBookedInterval() {
        return bookedInterval;
    }

}
