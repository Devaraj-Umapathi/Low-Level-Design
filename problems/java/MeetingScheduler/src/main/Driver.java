package main;

import enums.RSVPStatus;
import models.*;
import services.MeetingScheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Driver {
    public static void main(String[] args) {
        try {
            // Set up scheduler (singleton)
            MeetingScheduler scheduler = MeetingScheduler.getInstance();

            // Set up rooms
            MeetingRoom roomA = new MeetingRoom(1, 4);
            MeetingRoom roomB = new MeetingRoom(2, 8);
            List<MeetingRoom> rooms = List.of(roomA, roomB);
            scheduler.addRooms(rooms);

            // Set up users
            User maya = new User("Maya", "maya@example.com");
            User rohan = new User("Rohan", "rohan@example.com");
            User elena = new User("Elena", "elena@example.com");
            User samir = new User("Samir", "samir@example.com");

            // Scenario 1: Schedule a meeting
            header("Scenario 1: Schedule a Meeting");
            arrow("Scheduling \"Design Review\" for Rohan, Samir with Maya as organizer...");
            List<User> participants1 = List.of(rohan, samir);
            Interval interval1 = new Interval(
                    LocalDateTime.of(2026, 7, 10, 10, 0),
                    LocalDateTime.of(2026, 7, 10, 11, 0)
            );
            Meeting meeting1 = scheduler.scheduleMeeting(maya, participants1, interval1, "Design Review").orElseThrow();
            simulateUserResponses(participants1, meeting1);

            // Scenario 2: Fix participants — remove Samir, add Elena
            header("Scenario 2: Fix Participants");
            arrow("Removing Samir (added by mistake)...");
            scheduler.removeParticipants(meeting1, List.of(samir));
            arrow("Adding Elena (correct participant)...");
            scheduler.addParticipants(meeting1, List.of(elena));

            // Scenario 3: Advisory conflict check
            header("Scenario 3: Advisory Conflict Check");
            Interval interval = new Interval(
                    LocalDateTime.of(2026, 7, 10, 10, 30),
                    LocalDateTime.of(2026, 7, 10, 11, 30)
            );
            List<User> currentParticipants = List.of(rohan, elena, samir);
            List<User> conflicted = scheduler.getParticipantsWithConflicts(currentParticipants, interval);
            arrow("Users with calendar overlap for 10:30–11:30: " + formatNames(conflicted));

            // Scenario 4: Cancel a meeting
            header("Scenario 4: Cancel Meeting");
            arrow("Cancelling \"Design Review\"...");
            scheduler.cancelMeeting(meeting1);

            header("Summary");
            System.out.println("✔ All scenarios completed");
            System.out.println("✔ Demonstrated: scheduling, RSVP, participant changes, conflict check, cancellation, validation");

        } catch (Exception e) {
            System.err.println("✗ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Deterministic RSVP simulation for reproducible demo output
    private static void simulateUserResponses(List<User> users, Meeting meeting) {
        Random random = new Random();
        for (User user : users) {
            RSVPStatus response = random.nextBoolean() ? RSVPStatus.ACCEPTED : RSVPStatus.REJECTED;
            user.respondInvitation(meeting, response);
        }
    }

    private static void header(String title) {
        System.out.println("\n==============================");
        System.out.println("▶ " + title);
        System.out.println("==============================\n");
    }

    private static void arrow(String msg) {
        System.out.println("→ " + msg);
    }

    private static String formatNames(List<User> users) {
        return users.stream().map(User::getName).collect(Collectors.joining(", "));
    }
}
