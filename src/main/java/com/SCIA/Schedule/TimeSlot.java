package com.SCIA.Schedule;

public class TimeSlot {

    public TimeSlot(int startTime, int duration){
        this.startTime = startTime;
        this.duration = duration;
    }

    private final int startTime;
    private final int duration;
    protected static final int START_TIME = 8;
    protected static final int END_TIME = 19;
    protected static final int HOURS_PER_DAY = END_TIME - START_TIME;
    public static final int INCREMENT = 5;
    protected static final int NUM_TIME_SLOTS_PER_DAY = HOURS_PER_DAY * 60 / INCREMENT;

    //get day by timeslot
    public static int getDay(int time_slot) {
        return (time_slot - 1) / NUM_TIME_SLOTS_PER_DAY + 1;
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
        if (getDay(time_slot) != getDay(time_slot + duration))
            return true;

        int hour = getHour(time_slot);

        if (hour < END_TIME)
            return false;
        return true;
    }

    public static int getNextDayTimeSlot(int time_slot) {

        int day = getDay(time_slot);
        int timeSlotNextDay = day * NUM_TIME_SLOTS_PER_DAY + 1;
        return timeSlotNextDay;
    }

    public static int getLastTimeSlot() {
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
        return toString(startTime);
    }


   public String getEndTime(){
        if (getRelativeTimeSlot(startTime) == NUM_TIME_SLOTS_PER_DAY){
            return toString(END_TIME,0);
        }

        int end_time = getRelativeTimeSlot(startTime+duration);
        if (end_time == NUM_TIME_SLOTS_PER_DAY){
            return toString(END_TIME,0);
        }

        return toString(end_time);
    }
}
