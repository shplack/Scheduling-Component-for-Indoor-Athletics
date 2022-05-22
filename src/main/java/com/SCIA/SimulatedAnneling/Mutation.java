package com.SCIA.SimulatedAnneling;

import com.SCIA.Schedule.TimeSlot.*;
import com.SCIA.Schedule.Event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutation {
    /*

    public static List<Event> MutationFunction(List<Event> eventList)    {

        Random rand = new Random();

        int rand_int1 = rand.nextInt(eventList.size());
        int rand_int2 = rand.nextInt(eventList.size());

        Event event1 = eventList.get(rand_int1);
        //System.out.println("before mutate Event 1: " + event1.toString());
        Event event2 = eventList.get(rand_int2);
        //System.out.println("before mutate Event 2: " + event2.toString());

        if(event1.isSwappable(event2)) {
            System.out.println();
            ArrayList<Integer> timeslot1 = new ArrayList<>(event1.time_slots());
            ArrayList<Integer> timeslot2 = new ArrayList<>(event2.time_slots());
            event1.assignTimeSlots(timeslot2);
            event2.assignTimeSlots(timeslot1);
        }

        //System.out.println("after mutate Event 1: " + event1.toString());
        //System.out.println("after mutate Event 2: " + event2.toString());

        return eventList;


    }

     */

}
