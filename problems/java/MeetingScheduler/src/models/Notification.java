package models;

import enums.NotificationType;

import java.time.LocalDateTime;

public class Notification {

    private final int id;
    private final NotificationType type;
    private final String content;
    private final LocalDateTime creationDate;

    public Notification(int id, NotificationType type, String content, LocalDateTime creationDate) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.creationDate = creationDate;
    }

    public void send(User user, Meeting meeting) {
        System.out.println("[" + id + "] " + type.getLabel() + " to " + user.getName()
                + " <" + user.getEmail() + "> — " + content + " (" + meeting.getSubject()
                + ") @ " + creationDate);
    }
}
