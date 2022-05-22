package com.SCIA.SimulatedAnneling;

import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.TimeSlot.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UpdatedJudgement {

    public static int getConflicts(List<Event> eventList)  {

        int conflicts = 0;

        //System.out.println("This is size: "+ eventList.size());
        for (int First_Value_To_Iterate_List = 0; First_Value_To_Iterate_List < eventList.size(); First_Value_To_Iterate_List++) {
            for (int Second_value_To_Iterate_List = 0; Second_value_To_Iterate_List < eventList.size(); Second_value_To_Iterate_List++) {
                conflicts = getConflicts(eventList, conflicts, First_Value_To_Iterate_List, Second_value_To_Iterate_List);
            }
        }


        //System.out.println("This is conflicts: " + conflicts);
        //double double_conflicts = conflicts;
        //double_conflicts = Math.sqrt(double_conflicts);
        //conflicts = (int) double_conflicts;


        return conflicts;
    }

    static int getConflicts(List<Event> eventList, int conflicts, int first_Value_To_Iterate_List, int second_value_To_Iterate_List) {
        if (eventList.get(first_Value_To_Iterate_List).timeSlot().conflictsWith(eventList.get(second_value_To_Iterate_List).timeSlot().getTimeSlot(), eventList.get(second_value_To_Iterate_List).timeSlot().getDuration()))
            for (int First_Value_To_Iterate_Athletes = 0; First_Value_To_Iterate_Athletes < eventList.get(first_Value_To_Iterate_List).athletes().size(); First_Value_To_Iterate_Athletes++) {
                for (int Second_Value_To_Iterate_Athletes = 0; Second_Value_To_Iterate_Athletes < eventList.get(second_value_To_Iterate_List).athletes().size(); Second_Value_To_Iterate_Athletes++) {
                    if (eventList.get(first_Value_To_Iterate_List) != eventList.get(second_value_To_Iterate_List)) {
                        if ((eventList.get(first_Value_To_Iterate_List).athletes().get(First_Value_To_Iterate_Athletes).id()) == eventList.get(second_value_To_Iterate_List).athletes().get(Second_Value_To_Iterate_Athletes).id())
                        {
                            //System.out.println("This is First_Value_To_Iterate_List: " + First_Value_To_Iterate_List);
                            //int first_athlete = eventList.get(First_Value_To_Iterate_List).athletes().get(First_Value_To_Iterate_Athletes).id();
                            //int second_athlete = eventList.get(Second_value_To_Iterate_List).athletes().get(Second_Value_To_Iterate_Athletes).id();
                            //System.out.println("first athlete: " + first_athlete + " and second athlete: " + second_athlete);
                            conflicts++;
                            //System.out.println("This is conflicts: " + conflicts);
                        }
                    }
                }
            }
        return conflicts;
    }
}
