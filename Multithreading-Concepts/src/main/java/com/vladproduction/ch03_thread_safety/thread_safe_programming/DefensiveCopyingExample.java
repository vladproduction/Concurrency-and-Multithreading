package com.vladproduction.ch03_thread_safety.thread_safe_programming;

import java.util.Date;

public class DefensiveCopyingExample {

    public class DateRange {
        private final Date start;
        private final Date end;

        public DateRange(Date start, Date end) {
            // Defensive copying in constructor
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());

            // Validation after copying
            if (this.start.after(this.end)) {
                throw new IllegalArgumentException("Start date must be before end date");
            }
        }

        public Date getStart() {
            // Defensive copying in getters
            return new Date(start.getTime());
        }

        public Date getEnd() {
            return new Date(end.getTime());
        }

        // No setters to maintain immutability
    }

    // Usage
    public void useDefensiveCopy() {
        Date start = new Date();
        Date end = new Date(start.getTime() + 86400000); // +1 day

        DateRange range = new DateRange(start, end);

        // Original date can be modified, but doesn't affect range
        start.setTime(0);  // January 1, 1970

        // Getting date doesn't expose internal state
        Date rangeStart = range.getStart();
        rangeStart.setTime(0);  // This doesn't affect the range's start date
    }

}
