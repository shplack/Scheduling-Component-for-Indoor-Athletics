package com.SCIA.Schedule;

public class TimeSlot {

    protected static final int START_TIME = 8;
    protected static final int END_TIME = 19;
    protected static final int HOURS_PER_DAY = END_TIME - START_TIME;
    public static final int INCREMENT = 5;
    protected static final int NUM_TIME_SLOTS_PER_DAY = HOURS_PER_DAY * 60 / INCREMENT;

    public TimeSlot(int timeSlot, int duration){
        this.timeSlot = timeSlot;
        this.duration = duration;
    }

    public TimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot.timeSlot;
        this.duration = timeSlot.duration;
    }

    private int timeSlot;
    private int duration;

    public int getTimeSlot() {
        return timeSlot;
    }

    public int getDuration() {
        return duration;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTimeSlot(int timeSlot, int duration) {
        setTimeSlot(timeSlot);
        setDuration(duration);
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot.timeSlot;
        this.duration = timeSlot.duration;
    }

    public int getLastTimeSlot() {
        return  timeSlot + duration - 1;
    }

    public boolean conflictsWith(int timeSlot, int duration) {
        int end = timeSlot + duration - 1;
        if (this.timeSlot >= timeSlot && this.timeSlot <= end)
            return true;
        if (getLastTimeSlot() >= timeSlot && getLastTimeSlot() <= end)
            return true;
        if (timeSlot >= this.timeSlot && timeSlot <= getLastTimeSlot())
            return true;
        if (end >= this.timeSlot && end <= getLastTimeSlot())
            return true;
        return false;

    }

    public boolean conflictsWith(TimeSlot timeSlot) {
        return this.conflictsWith(timeSlot.timeSlot, timeSlot.duration);
    }

    //get day by timeslot
    public static int getDay(int time_slot) {
        return (time_slot - 1) / NUM_TIME_SLOTS_PER_DAY + 1;
    }

    public int getDay() {
        return getDay(this.timeSlot);
    }

    private static int getRelativeTimeSlot(int time_slot) {
        return time_slot - (getDay(time_slot) - 1) * NUM_TIME_SLOTS_PER_DAY;
    }

    private static int getHour(int time_slot) {
        return (getRelativeTimeSlot(time_slot) * INCREMENT - INCREMENT) / 60 + START_TIME;
    }

    static int getMinutes(int time_slot) {
        return (getRelativeTimeSlot(time_slot) * INCREMENT - INCREMENT) % 60;
    }

    public String toString() {
        return getStartTime() + " - " + getEndTime();
    }

    public static String toString(int time_slot) {
        int hours = getHour(time_slot);
        int minutes = getMinutes(time_slot);
        return toString(hours, minutes);
    }

    private static String toString(int hours, int minutes) {
        String str_minutes = String.valueOf(minutes);
        if (minutes < 10)
            str_minutes = "0" + str_minutes;

        String str_hours = String.valueOf(hours);
        if (hours < 10)
            str_hours = "0" + str_hours;

        return str_hours + ":" + str_minutes;
    }

    public static boolean pastLastTimeSlot(int time_slot, int duration) {
        if (getDay(time_slot) != getDay(time_slot + duration - 1))
            return true;

        int hour = getHour(time_slot);

        if (hour < END_TIME)
            return false;
        return true;
    }

    public boolean pastLastTimeSlot() {
        int day = getDay(this.timeSlot);
        int lastTimeSlot = getLastTimeSlot(day);
        return getLastTimeSlot() > lastTimeSlot;
    }

    public static int getNextDayTimeSlot(int time_slot) {

        int day = getDay(time_slot);
        int timeSlotNextDay = day * NUM_TIME_SLOTS_PER_DAY + 1;
        return timeSlotNextDay;
    }

    public static int getFirstTimeSlot(int day) {
        return day * NUM_TIME_SLOTS_PER_DAY + 1;
    }

    public static int getNumTimeSlotsPerDay() {
        return NUM_TIME_SLOTS_PER_DAY;
    }

    public static int getLastTimeSlot(int day) {
        int last_time_slot = day * NUM_TIME_SLOTS_PER_DAY;
        return last_time_slot;
    }
    //------------------------------------------------------------------------------------------------------------
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
    public String getStartTime(){
        return toString(timeSlot);
    }


   public String getEndTime(){
        if (getRelativeTimeSlot(timeSlot) == NUM_TIME_SLOTS_PER_DAY){
            return toString(END_TIME,0);
        }

        int end_time = getRelativeTimeSlot(timeSlot +duration);
        if (end_time == NUM_TIME_SLOTS_PER_DAY){
            return toString(END_TIME,0);
        }

        return toString(end_time);
    }

    public boolean equals(TimeSlot timeSlot) {
        return this.timeSlot == timeSlot.timeSlot && this.duration == timeSlot.duration;
    }

    public int compareTo(TimeSlot timeSlot) {
        return this.timeSlot + duration - timeSlot.timeSlot;
    }

    public static int compareTo(TimeSlot ts1, TimeSlot ts2) { return ts1.timeSlot - ts2.timeSlot; }
}
