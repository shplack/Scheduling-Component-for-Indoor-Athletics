package com.SCIA;

import com.SCIA.Schedule.TimeSlot;
import org.junit.jupiter.api.Test;

import static com.SCIA.Schedule.TimeSlot.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTimeSlotGetTime {

    public static void testTimeSlotToString(int time_slot, String expected){

        System.out.print("Time Slot: "+ time_slot + "\t");
        System.out.print("Expected: " + expected + "  ");
        System.out.print("Actual: " + getStartTime(time_slot) + " | ");
        boolean passed = getStartTime(time_slot).equals(expected);
        System.out.println(passed ? "passed" : "failed");
    }
    @Test
    public void testTimeSlotToString(){
        System.out.println(getHour(56));
        System.out.println(getHour(87));
        System.out.println(getHour(67));
        System.out.println(getHour(11));
        System.out.println(getHour(38));
        System.out.println(pastLastTimeSlot(87));
    }


}
