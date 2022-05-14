package Schedule;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class TimeSlotTest {

    @Test
    void testToString() {
        assertEquals(TimeSlot.toString(50), "14:10");
        assertEquals(TimeSlot.toString(51), "14:15");
        assertNotEquals(TimeSlot.toString(52), "14:15");
        assertEquals(TimeSlot.toString(170), "14:10");
        assertEquals(TimeSlot.toString(500), "11:40");
    }
}