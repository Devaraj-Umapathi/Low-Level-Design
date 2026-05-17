package models;

import java.time.LocalDateTime;

public class Interval {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Interval(LocalDateTime startTime, LocalDateTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Interval end must be after start (got start=" + startTime + ", end=" + endTime + ")");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Checks whether this interval overlaps with the given interval.
     * Overlap rule: this.start &lt; other.end  AND  other.start &lt; this.end
     *
     * Examples (this = A, other = B):
     *
     *   A: 10:00 ─────── 11:00
     *   B:        10:30 ─────── 11:30      → overlap   (10:00 &lt; 11:30 && 10:30 &lt; 11:00)
     *
     *   A: 10:00 ─── 11:00
     *   B:                11:00 ─── 12:00  → no overlap (touching boundary, 11:00 ≮ 11:00)
     *
     *   A: 10:00 ─── 11:00
     *   B:                     12:00 ─── 13:00  → no overlap (disjoint, 12:00 ≮ 11:00)
     *
     *   A: 10:00 ──────────────── 12:00
     *   B:        10:30 ─── 11:00          → overlap   (B fully inside A)
     */
    public boolean isOverlap(Interval interval) {
        return startTime.isBefore(interval.endTime) && interval.startTime.isBefore(endTime);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
