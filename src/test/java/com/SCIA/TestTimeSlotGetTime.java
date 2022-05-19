package com.SCIA;

import com.SCIA.Schedule.TimeSlot;
import org.junit.jupiter.api.Test;

import static com.SCIA.Schedule.TimeSlot.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTimeSlotGetTime {

    public static void testTimeSlotToString(int time_slot, String expected){

        System.out.print("Time Slot: "+ time_slot + "\t");
        System.out.print("Expected: " + expected + "  ");
        System.out.print("Actual: " + TimeSlot.toString(time_slot) + " | ");
        boolean passed = TimeSlot.toString(time_slot).equals(expected);
        System.out.println(passed ? "passed" : "failed");
    }
    @Test
    public void testTimeSlotToString(){
        assertEquals(TimeSlot.toString(50), "day: 1\t14:05");

        testTimeSlotToString(50, "14:10");
        testTimeSlotToString(51, "14:15");
        testTimeSlotToString(500, "11:40");
        System.out.println(getStartHour(500));
        System.out.println(getStartMinute(500));
        System.out.println(getStartMinute(109));
        System.out.println(getEndTime(119));
    }


}
