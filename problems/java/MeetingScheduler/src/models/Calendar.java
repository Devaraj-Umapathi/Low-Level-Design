package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Calendar {

    private final Set<Meeting> meetings;

    public Calendar() {
        meetings = Collections.synchronizedSet(new TreeSet<>(
                Comparator.comparing((Meeting m) -> m.getBookedInterval().getStartTime())
                        .thenComparingInt(Meeting::getId)));
    }

    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    public boolean hasConflict(Interval interval) {
        synchronized (meetings) {
            for (Meeting meeting : meetings) {
                if (!meeting.getBookedInterval().getStartTime().isBefore(interval.getEndTime())) {
                    break;
                }
                if (meeting.getBookedInterval().isOverlap(interval)) {
                    return true;
                }
            }
            return false;
        }
    }

    public List<Meeting> getMeetings() {
      return new ArrayList<>(meetings);
    }
}
