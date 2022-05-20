package com.SCIA.SimulatedAnneling;

import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.TimeSlot.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Judgement {

    public static int getConflicts(List<Event> eventList)  {
        int conflicts = 0;
        //System.out.println("This is size: "+ eventList.size());
        for (int i = 0; i < eventList.size(); i++) {
            for (int j = 0; j < eventList.size(); j++) {
                for (int k = 0; k < eventList.get(i).time_slots().size(); k++)
                    for (int l = 0; l < eventList.get(j).time_slots().size(); l++)
                        if (Objects.equals(eventList.get(i).time_slots().get(k), eventList.get(j).time_slots().get(l)))
                            for (int o = 0; o < eventList.get(i).athletes().size(); o++) {
                                for (int p = 0; p < eventList.get(j).athletes().size(); p++) {
                                    if (eventList.get(i) != eventList.get(j)) {
                                        if ((eventList.get(i).athletes().get(o).id()) == eventList.get(j).athletes().get(p).id())
                                        {
                                            //System.out.println("This is i: " + i);
                                            //int first_athlete = eventList.get(i).athletes().get(o).id();
                                            //int second_athlete = eventList.get(j).athletes().get(p).id();
                                            //System.out.println("first athlete: " + first_athlete + " and second athlete: " + second_athlete);
                                            conflicts++;
                                            //System.out.println("This is conflicts: " + conflicts);
                                        }
                                    }
                                }
                            }
            }
        }
        //System.out.println("This is conflicts: " + conflicts);
        //double double_conflicts = conflicts;
        //double_conflicts = Math.sqrt(double_conflicts);
        //conflicts = (int) double_conflicts;
        return conflicts;
    }
}
