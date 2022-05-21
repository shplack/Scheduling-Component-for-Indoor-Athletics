package com.SCIA.Schedule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeSlotTest{
    static TimeSlot timeSlottest = new TimeSlot(0,0);
    int firstTimeSlotFirstDay = 1;
    int lastTimeSlotFirstDay = TimeSlot.NUM_TIME_SLOTS_PER_DAY;
    int firstTimeSlotSecondDay = TimeSlot.NUM_TIME_SLOTS_PER_DAY + 1;
    int lastTimeSlotSecondDay = TimeSlot.NUM_TIME_SLOTS_PER_DAY * 2;
    int lastTimeSlotSecondDayNext = lastTimeSlotSecondDay + 1;


    void timeSlotToStringTest(String expected_time, int time_slot) {
        String actual_time = TimeSlot.toString(time_slot);
        boolean passed = expected_time.equals(actual_time);
        System.out.println("Expected: " + expected_time + "\tGiven: " + time_slot + "\t" + "Actual:" + actual_time +
                " - " + (passed ? "passed" : "failed"));
        assertEquals(expected_time, actual_time);
    }

    void getEndTimeTest(String expected_time, int start_time, int duration){
        String actual_time = TimeSlot.getEndTime(start_time, duration);
        boolean passed = expected_time.equals(actual_time);
        System.out.println("Expected: " + expected_time + "\tgiven start Time Slot " + start_time +" With duration "+
                duration + "\t" + "Actual:" + actual_time +
                " - " + (passed ? "passed" : "failed"));
        assertEquals(expected_time, actual_time);
    }

    @Test
    @DisplayName("Test getEndTime(int start_time, int duration) ")
    void testGetEndTime(){
        getEndTimeTest("19:00", 128,4);
        getEndTimeTest("19:00", 132,1);
        getEndTimeTest("19:00", 264,2);
        getEndTimeTest("19:00", 130+ 396,2);
        getEndTimeTest("19:00", firstTimeSlotFirstDay,131);
        getEndTimeTest("19:00", firstTimeSlotSecondDay,131);
        getEndTimeTest("19:00", lastTimeSlotSecondDay,2);
        getEndTimeTest("19:00", lastTimeSlotSecondDay,2);
        getEndTimeTest("19:00", lastTimeSlotFirstDay,2);
        getEndTimeTest("18:50", lastTimeSlotSecondDayNext,130);
    }
    @Test
    @DisplayName("Test getStartTime(time_slot)")
    void testGetStartTime() {
        System.out.println("Test getStartTime(time_slot, soft)");
        timeSlotToStringTest("18:55", 132);
        timeSlotToStringTest("18:55", 132+132);
        timeSlotToStringTest("18:55", 396);
        timeSlotToStringTest("18:55", 132+ 3*132);
        timeSlotToStringTest("08:00", firstTimeSlotFirstDay);
        timeSlotToStringTest("08:00", firstTimeSlotSecondDay);
        timeSlotToStringTest("18:55", lastTimeSlotSecondDay);
        timeSlotToStringTest("18:55", lastTimeSlotSecondDay);
        timeSlotToStringTest("18:55", lastTimeSlotFirstDay);
        timeSlotToStringTest("08:00", lastTimeSlotSecondDayNext);
        System.out.println();
    }
}