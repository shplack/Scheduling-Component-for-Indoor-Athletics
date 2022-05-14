import Schedule.TimeSlots;

public class TestTimeSlotGetTime {

    public static void testTimeSlotGetTime(int time_slot, String expected){

        System.out.print("Time Slot: "+ time_slot + "\t");
        System.out.print("Expected: " + expected + "  ");
        System.out.print("Actual: " + TimeSlots.getTime(time_slot) + " | ");
        boolean passed = TimeSlots.getTime(time_slot).equals(expected);
        System.out.println(passed ? "passed" : "failed");
    }

    public static void main(String[] args) {
        testTimeSlotGetTime(50, "14:10");
        testTimeSlotGetTime(51, "14:15");
        testTimeSlotGetTime(500, "11:40");
    }

}
