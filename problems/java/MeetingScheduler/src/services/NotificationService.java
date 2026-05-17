package services;

import enums.NotificationType;
import models.Meeting;
import models.Notification;
import models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationService {
    private static final AtomicInteger notificationId = new AtomicInteger(1);

    public void sendNotifications(NotificationType type, List<User> participants, Meeting meeting) {
        Notification notification = new Notification(
                notificationId.getAndIncrement(),
                type,
                type.getSubjectPrefix() + ": " + meeting.getSubject(),
                LocalDateTime.now());
        for (User participant : participants) {
            notification.send(participant, meeting);
        }
    }
}
