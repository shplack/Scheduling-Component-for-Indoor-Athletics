package com.SCIA.SimulatedAnneling;

import java.util.ArrayList;
import com.SCIA.Schedule.Event.Event;

public class InitialHeat {

    public static float initialHeat(ArrayList<Event> eventList)  {
        int conflicts = Judgement.getConflicts(eventList);

        //calculate heat so maybe the conflicts value times 100000
        float return_value = conflicts*100000;

        return return_value;
    }


}
