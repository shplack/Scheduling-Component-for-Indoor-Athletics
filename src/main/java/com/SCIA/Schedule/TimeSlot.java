package com.SCIA.Schedule;

import java.util.List;

public class TimeSlot {

    private static final int START_TIME = 10;
    private static final int MAX_NUM_SLOTS_ONE_DAY = 108;
    private static final int MAX_NUM_SLOTS_ONE_DAY_Duration = 120;
    public static final int INCREMENT = 5;
    public static final int  NUM_SLOTS_UPP_TO_NIGHT = 167;

    public static List<Integer> test(List<Integer> testers) {
        return testers;
    }

    //get day by timeslot
    public static int getDay(int time_slot) {
        return (time_slot / MAX_NUM_SLOTS_ONE_DAY)+1;
    }
    public static int getDayDuration(int time_slot) {
        return (time_slot / MAX_NUM_SLOTS_ONE_DAY_Duration)+1;
    }
    public static int getStartHour(int time_slot){
        time_slot--;
        time_slot %= MAX_NUM_SLOTS_ONE_DAY;

        int time_slots_in_minutes = time_slot * INCREMENT;
        int hours = time_slots_in_minutes / 60 + START_TIME;
        return hours;
    }
    public static int getMinute(int time_slot){
        time_slot--;
        time_slot %= MAX_NUM_SLOTS_ONE_DAY;

        int time_slots_in_minutes = time_slot * INCREMENT;
        int minutes = time_slots_in_minutes % 60;
        return minutes;
    }
    public static String getStartTime(int time_slot) {

        int day = getDay(time_slot);
        int hours = getStartHour(time_slot);
        int minutes = getMinute(time_slot);

        return getString(day, hours, minutes);
    }
    public static int getEndHour(int time_slot) {
        time_slot--;
        time_slot %= MAX_NUM_SLOTS_ONE_DAY_Duration;

        int time_slots_in_minutes = time_slot * INCREMENT;
        int hours = time_slots_in_minutes / 60 + START_TIME;
        return hours;
    }
    public static int getHour(int time_slot){
        time_slot--;
        time_slot %= NUM_SLOTS_UPP_TO_NIGHT;

        int time_slots_in_minutes = time_slot * INCREMENT;
        int hours = time_slots_in_minutes / 60 + START_TIME;
        return hours;
    }
    public static String getEndTime(int time_slot) {
        int day = getDayDuration(time_slot);
        int hours = getEndHour(time_slot);
        int minutes = getMinute(time_slot);


        return getString(day, hours, minutes);
    }

    private static String getString(int day, int hours  , int minutes) {
        String str_minutes = String.valueOf(minutes);
        if (minutes < 10)
            str_minutes = "0" + str_minutes;

        String str_hours = String.valueOf(hours);
        if (hours < 10)
            str_hours = "0" + str_hours;

        return "day: "+ day + "\t"+ str_hours + ":" + str_minutes;
    }

    public static boolean pastLastTimeSlot(int time_slot) {
        int hours = getHour(time_slot);

        if (hours < 20){
            return false;
        }
        return true;
    }

    public static int getNextDayTimeSlot(int time_slot) {

        int getDay = getDay(time_slot);
        int nextDayTimeSlot = getDay * MAX_NUM_SLOTS_ONE_DAY;

        return nextDayTimeSlot;
    }

    public static int getLastTimeSlot(int day) {

        int last_time_slot = (day - 1) * MAX_NUM_SLOTS_ONE_DAY  + MAX_NUM_SLOTS_ONE_DAY_Duration - 1;

        return last_time_slot;

    }

    public static int getTimeSlot(int day) {
        int time_slot = 1 + (MAX_NUM_SLOTS_ONE_DAY) * (day - 1);
        return time_slot;
    }

    public static int getNumSlots(int day) {
        int numSlot = (MAX_NUM_SLOTS_ONE_DAY - 1) * day;
        return numSlot;
        
    }

}
