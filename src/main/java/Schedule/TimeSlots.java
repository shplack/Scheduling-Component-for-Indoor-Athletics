package Schedule;

public class TimeSlots {

    private static final int START_TIME = 10;
    private static final int MAX_NUM_SLOTS_ONE_DAY = 120;

    public static String getTime(int time_slot) {

        time_slot %= MAX_NUM_SLOTS_ONE_DAY;

        int time_slots_in_minutes = time_slot * 5;
        int hours = time_slots_in_minutes / 60 + START_TIME;
        int minutes = time_slots_in_minutes % 60;

        String str_minutes = String.valueOf(minutes);
        if (minutes < 10)
            str_minutes = "0" + str_minutes;

        String str_hours = String.valueOf(hours);
        if (hours < 10)
            str_hours = "0" + str_hours;

        return str_hours + ":" + str_minutes;
    }
}
