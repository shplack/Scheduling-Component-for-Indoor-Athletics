package com.SCIA.SimulatedAnneling;

import com.SCIA.Schedule.TimeSlot.*;
import com.SCIA.Schedule.Event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutation {

    public static List<Event> MutationFunction(List<Event> eventList)    {

        Random rand = new Random();

        int rand_int1 = rand.nextInt(eventList.size());
        int rand_int2 = rand.nextInt(eventList.size());

        //deep copy of eventList needs to be done here

        Event event1 = eventList.get(rand_int1);
        Event event2 = eventList.get(rand_int2);

        if(event1.isSwappable(event2)) {

            ArrayList<Integer> timeslot1 = event1.time_slots();
            ArrayList<Integer> timeslot2 = event2.time_slots();

            for (int i = 0; i < event1.time_slots().size(); i++) {
                event1.time_slots().set(i, timeslot2.get(i));
            }
            for (int i = 0; i < eventList.size(); i++) {
                event2.time_slots().set(i, timeslot1.get(i));
            }
            //do e^ calculation and compare to random float number between 0 and 1
            //If its less than e^ calculation return new schedule
        }
        return eventList;


    }

}
