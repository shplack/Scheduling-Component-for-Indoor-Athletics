package com.SCIA.Schedule;

public class TimeSlot {

    // A constant that is used to calculate the start time of a timeslot.
    protected static final int START_TIME = 8;
    // A constant that is used to calculate the start time of a timeslot.
    protected static final int END_TIME = 19;
    // A constant that is used to calculate the start time of a timeslot.
    protected static final int HOURS_PER_DAY = END_TIME - START_TIME;
    // A constant that is used to calculate the start time of a timeslot.
    public static final int INCREMENT = 5;
    // Calculating the number of time slots per day.
    protected static final int NUM_TIME_SLOTS_PER_DAY = HOURS_PER_DAY * 60 / INCREMENT;

    // The event's starting time slot
    private int timeSlot;
    // The event's duration
    private int duration;

    /**
     * TimeSlot constructor
     *
     * @param timeSlot The starting time slot
     * @param duration The duration of the event
     */
    public TimeSlot(int timeSlot, int duration) {
        this.timeSlot = timeSlot;
        this.duration = duration;
    }

    /**
     * Copy constructor for time slot
     *
     * @param timeSlot The time slot to copy
     */
    public TimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot.timeSlot;
        this.duration = timeSlot.duration;
    }

    /////////////////////////////////////////////////
    // getters and setters
    ////////////////////////////////////////////////

    /**
     * Given a time slot, return the day relative to the start of the competitions
     *
     * @param time_slot the time slot number
     * @return The day of the week.
     */
    public static int getDay(int time_slot) {
        return (time_slot - 1) / NUM_TIME_SLOTS_PER_DAY + 1;
    }

    /**
     * > Given a time slot, return the time slot relative to the first day
     *
     * @param time_slot the time slot number
     * @return The time slot relative to the day.
     */
    private static int getRelativeTimeSlot(int time_slot) {
        return time_slot - (getDay(time_slot) - 1) * NUM_TIME_SLOTS_PER_DAY;
    }

    /**
     * It takes a time slot and returns the number of hours relative to a day
     *
     * @param time_slot the time slot you want to get the hour for
     * @return The hour of the day that the time slot is in.
     */
    private static int getHour(int time_slot) {
        return (getRelativeTimeSlot(time_slot) * INCREMENT - INCREMENT) / 60 + START_TIME;
    }

    /**
     * It takes a time slot and returns number of minutes relative to a day
     *
     * @param time_slot The time slot you want to get the minutes for.
     * @return The minutes of the time slot.
     */
    static int getMinutes(int time_slot) {
        return (getRelativeTimeSlot(time_slot) * INCREMENT - INCREMENT) % 60;
    }

    /**
     * If the given timeslot and duration is past the number of time slots per day, it is invalid.
     *
     * @param time_slot A time slot
     * @param duration  The duration
     * @return True if invalid, false if not
     */
    public static boolean isInvalid(int time_slot, int duration) {
        if (getDay(time_slot) != getDay(time_slot + duration - 1))
            return true;

        int hour = getHour(time_slot);

        return hour >= END_TIME;
    }

    /**
     * Given a day, return the first time slot of that day.
     *
     * @param day The day relative to the start of the competition
     * @return The first time slot of the day.
     */
    public static int getFirstTimeSlot(int day) {
        return (day - 1) * NUM_TIME_SLOTS_PER_DAY + 1;
    }

    /**
     * > This function returns the number of time slots per day
     *
     * @return The number of time slots per day.
     */
    public static int getNumTimeSlotsPerDay() {
        return NUM_TIME_SLOTS_PER_DAY;
    }

    /**
     * Given a time slot, return the first time slot of the next day
     *
     * @param time_slot the time slot of the day
     * @return The time slot of the next day.
     */
    public static int getNextDayTimeSlot(int time_slot) {
        return getDay(time_slot) * NUM_TIME_SLOTS_PER_DAY + 1;
    }

    /**
     * Given a day, return the last time slot of that day.
     *
     * @param day The day of the week.
     * @return The last time slot of the day.
     */
    public static int getLastTimeSlot(int day) {
        return day * NUM_TIME_SLOTS_PER_DAY;
    }

    /**
     * Returns true if the given time slot conflicts with this time slot.
     *
     * @param timeSlot The time slot to check for conflicts with.
     * @return A boolean value.
     */
    public boolean conflictsWith(TimeSlot timeSlot) {
        return this.conflictsWith(timeSlot.timeSlot, timeSlot.duration);
    }

    /**
     * Get the time slot's start
     *
     * @return The event's starting time slot
     */
    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot.timeSlot;
        this.duration = timeSlot.duration;
    }

    /**
     * Set the time slot's start
     *
     * @param timeSlot The starting time slot
     */
    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Get the event's duration
     *
     * @return The event's duration
     */
    public int getDuration() {
        return duration;
    }

    public String toString() {
        return getStartTime() + " - " + getEndTime();
    }

    /**
     * It converts a time slot into a string.
     *
     * @param time_slot The time slot to convert to a string.
     * @return A string representation of the time slot.
     */
    public static String toString(int time_slot) {
        int hours = getHour(time_slot);
        int minutes = getMinutes(time_slot);
        return toString(hours, minutes);
    }

    /**
     * If the number is less than 10, add a 0 to the front of it
     *
     * @param hours The number of hours to display.
     * @param minutes The number of minutes to add to the time.
     * @return The time in hours and minutes.
     */
    private static String toString(int hours, int minutes) {
        String str_minutes = String.valueOf(minutes);
        if (minutes < 10)
            str_minutes = "0" + str_minutes;

        String str_hours = String.valueOf(hours);
        if (hours < 10)
            str_hours = "0" + str_hours;

        return str_hours + ":" + str_minutes;
    }

    /**
     * Set the time slot's duration
     *
     * @param duration The event's duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Set the time slot's start and duration
     *
     * @param timeSlot The starting time slot
     * @param duration The event's duration
     */
    public void setTimeSlot(int timeSlot, int duration) {
        setTimeSlot(timeSlot);
        setDuration(duration);
    }

    /**
     * Return the last time slot that this event occupies.
     *
     * @return The last time slot the event occupies.
     */
    public int getLastTimeSlot() {
        return timeSlot + duration - 1;
    }

    /**
     * Returns true if the given time slot conflicts with this time slot.
     *
     * @param timeSlot the time slot to check for conflicts with
     * @param duration The duration of the event in minutes.
     */
    public boolean conflictsWith(int timeSlot, int duration) {
        int end = timeSlot + duration - 1;
        if (this.timeSlot >= timeSlot && this.timeSlot <= end)
            return true;
        if (getLastTimeSlot() >= timeSlot && getLastTimeSlot() <= end)
            return true;
        if (timeSlot >= this.timeSlot && timeSlot <= getLastTimeSlot())
            return true;
        return end >= this.timeSlot && end <= getLastTimeSlot();

    }

    /**
     * This function returns the day of the time slot relative to the start of the competitions
     *
     * @return The day relative to the start of the competitions
     */
    public int getDay() {
        return getDay(this.timeSlot);
    }

    /**
     * If the current time slot's end time is the next day, then the timeslot is invalid.
     *
     * @return The last time slot of the day.
     */
    public boolean isInvalid() {
        int day = getDay(this.timeSlot);
        int lastTimeSlot = getLastTimeSlot(day);
        return getLastTimeSlot() >= lastTimeSlot;
    }

    /**
     * Given a start time slot and a duration, return the end time slot
     *
     * @param startTid the time slot id of the start time
     * @param dur duration of the class in time slots
     * @return The end time of a given time slot.
     */
    public static String getEndTime(int startTid, int dur){
        if (getRelativeTimeSlot(startTid) == NUM_TIME_SLOTS_PER_DAY){
            return toString(END_TIME,0);
        }

        int end_time = getRelativeTimeSlot(startTid + dur);
        if (end_time == NUM_TIME_SLOTS_PER_DAY){
            return toString(END_TIME,0);
        }

        return toString(end_time);

    }

    /**
     * Returns a string of the timeslot's start time.
     *
     * @return The start time of the appointment.
     */
    public String getStartTime() {
        return toString(timeSlot);
    }


    /**
     * Returns a string of the timeslot's end time.
     *
     * @return The end time of the event.
     */
    public String getEndTime() {
        if (getRelativeTimeSlot(timeSlot) == NUM_TIME_SLOTS_PER_DAY) {
            return toString(END_TIME, 0);
        }

        int end_time = getRelativeTimeSlot(timeSlot + duration);
        if (end_time == NUM_TIME_SLOTS_PER_DAY) {
            return toString(END_TIME, 0);
        }

        return toString(end_time);
    }

    /**
     * If the timeSlot and duration of the two TimeSlots are the same, then they are equal.
     *
     * @param timeSlot The time slot to compare to.
     * @return The boolean value of the comparison of the two time slots.
     */
    public boolean equals(TimeSlot timeSlot) {
        return this.timeSlot == timeSlot.timeSlot && this.duration == timeSlot.duration;
    }

    /**
     * Compares a timeslot's end time to the given timeslot's start time
     *
     * @param timeSlot The time slot that you want to compare to.
     * @return The difference between the two time slots.
     */
    public int compareTo(TimeSlot timeSlot) {
        return this.timeSlot + duration - timeSlot.timeSlot;
    }

    /**
     * Compares two time slot's starting time slot
     *
     * @param ts1 TimeSlot
     * @param ts2 TimeSlot
     * @return The difference between the two time slot's starting time slot
     */
    public static int compareTo(TimeSlot ts1, TimeSlot ts2) {
        return ts1.timeSlot - ts2.timeSlot;
    }

    /**
     * Get the later TimeSlot
     *
     * @param ts1 TimeSlot
     * @param ts2 TimeSlot
     * @return TimeSlot
     */
    public static TimeSlot max(TimeSlot ts1, TimeSlot ts2) {
        return compareTo(ts1, ts2) >= 0 ? ts1 : ts2;
    }
}
