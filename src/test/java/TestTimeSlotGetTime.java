import Schedule.TimeSlot;

public class TestTimeSlotGetTime {

    public static void testTimeSlotToString(int time_slot, String expected){

        System.out.print("Time Slot: "+ time_slot + "\t");
        System.out.print("Expected: " + expected + "  ");
        System.out.print("Actual: " + TimeSlot.toString(time_slot) + " | ");
        boolean passed = TimeSlot.toString(time_slot).equals(expected);
        System.out.println(passed ? "passed" : "failed");
    }

    public static void main(String[] args) {
        testTimeSlotToString(50, "14:10");
        testTimeSlotToString(51, "14:15");
        testTimeSlotToString(500, "11:40");
    }

}
