package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class MeetingRoom {

    private final int id;
    private final int capacity;
    private final TreeSet<Interval> blockedIntervals;

    public MeetingRoom(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        blockedIntervals = new TreeSet<>(
                Comparator.comparing(Interval::getStartTime).thenComparing(Interval::getEndTime));
    }

    public synchronized boolean checkAndBookInterval(int requestedCapacity, Interval interval) {
        if (isAvailable(requestedCapacity, interval)) {
            blockedIntervals.add(interval);
            return true;
        }
        return false;
    }

    private boolean isAvailable(int requestedCapacity, Interval interval) {
        if (requestedCapacity > capacity) {
            return false;
        }

        for (Interval current : blockedIntervals) {
            if (!current.getStartTime().isBefore(interval.getEndTime())) {
                break;
            }
            if (current.isOverlap(interval)) {
                return false;
            }
        }
        return true;
    }

    public synchronized void releaseInterval(Interval interval) {
        blockedIntervals.remove(interval);
    }

    public List<Interval> getBlockedIntervals() {
        return new ArrayList<>(blockedIntervals);
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }
}
