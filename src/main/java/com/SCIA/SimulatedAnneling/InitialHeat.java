package com.SCIA.SimulatedAnneling;

import java.util.ArrayList;
import java.util.List;

import com.SCIA.Schedule.Event.Event;

public class InitialHeat {

    public static double initialHeat(List<Event> eventList)  {
        int conflicts = Judgement.getConflicts(eventList);
        //calculate heat so maybe the conflicts value times 100000
        return conflicts*100000;
    }


}
