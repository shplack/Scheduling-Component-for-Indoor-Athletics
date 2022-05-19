package com.SCIA.Schedule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TimeSlotTest {

    @Test
    void testToString() {
        /*assertEquals(TimeSlot.toString(1), "110:00");
        assertEquals(TimeSlot.toString(13), "11:00");
        assertEquals(TimeSlot.toString(25), "12:00");
        assertEquals(TimeSlot.toString(37), "13:00");
        assertEquals(TimeSlot.toString(49), "14:00");
        assertEquals(TimeSlot.toString(50), "14:05");
        assertEquals(TimeSlot.toString(51), "14:10");
        assertNotEquals(TimeSlot.toString(52), "14:10");
        assertEquals(TimeSlot.toString(108), "18:55");
        assertEquals(TimeSlot.toString(109), "10:00");*/
        System.out.println(TimeSlot.getStartTime(217));
    }
}