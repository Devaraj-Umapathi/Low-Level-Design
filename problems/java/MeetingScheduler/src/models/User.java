package models;

import enums.RSVPStatus;

public class User {

    private final String name;
    private final String email;
    private final Calendar calendar;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        calendar = new Calendar();
    }

    public void addMeetingToCalendar(Meeting meeting) {
        calendar.addMeeting(meeting);
    }

    public void removeMeetingFromCalendar(Meeting meeting) {
        calendar.removeMeeting(meeting);
    }

    public void respondInvitation(Meeting meeting, RSVPStatus response) {
        meeting.updateParticipantStatus(this, response);
        System.out.println("  · " + name + " responded: " + response);
        if (response == RSVPStatus.REJECTED) {
            calendar.removeMeeting(meeting);
        }
    }

    public boolean hasConflict(Interval interval) {
        return calendar.hasConflict(interval);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
