import Schedule.TimeSlots;

public class TimeSlotScheduleTest {

    public static boolean testGetTimeSlot(int time_slot, String expected){

        System.out.print("Time Slot: "+ time_slot + ", ");
        System.out.print("Expected: " + expected + " | ");
        boolean passed = TimeSlots.getTime(time_slot).equals(expected);
        System.out.println(passed ? "passed" : "failed");
        return passed;

    }

    public static void main(String[] args) {
         testGetTimeSlot(50, "14:10");

    }

}
