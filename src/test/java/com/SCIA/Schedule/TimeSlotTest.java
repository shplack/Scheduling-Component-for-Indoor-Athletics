package com.SCIA.Schedule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeSlotTest extends TimeSlot {

    int firstTimeSlotFirstDay = 1;
    int lastTimeSlotFirstDay = NUM_TIME_SLOTS_PER_DAY;
    int firstTimeSlotSecondDay = NUM_TIME_SLOTS_PER_DAY + 1;
    int lastTimeSlotSecondDay = NUM_TIME_SLOTS_PER_DAY * 2;
    int lastTimeSlotSecondDayNext = lastTimeSlotSecondDay + 1;

    void timeSlotToStringTest(String expected_time, int time_slot) {
        String actual_time = toString(time_slot);
        boolean passed = expected_time.equals(actual_time);
        System.out.println("Expected: " + expected_time + "\tGiven: " + time_slot + "\t" + "Actual:" + actual_time +
                " - " + (passed ? "passed" : "failed"));
        assertEquals(expected_time, actual_time);
    }


    @Test
    @DisplayName("Test getStartTime(time_slot)")
    void testGetStartTime() {
        System.out.println("Test getStartTime(time_slot, soft)");
        timeSlotToStringTest("12:10", 51);
        timeSlotToStringTest("08:00", firstTimeSlotFirstDay);
        timeSlotToStringTest("08:00", firstTimeSlotSecondDay);
        timeSlotToStringTest("18:55", lastTimeSlotSecondDay);
        timeSlotToStringTest("18:55", lastTimeSlotSecondDay);
        timeSlotToStringTest("18:55", lastTimeSlotFirstDay);
        timeSlotToStringTest("08:00", lastTimeSlotSecondDayNext);
        System.out.println();
    }
}