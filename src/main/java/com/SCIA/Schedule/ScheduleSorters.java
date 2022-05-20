package com.SCIA.Schedule;

import com.SCIA.Schedule.Event.Event;

import java.util.Comparator;

public class ScheduleSorters {
    public static class SortEventByTrialAgeGroupDiscipline implements Comparator<Event> {

        @Override
        public int compare(Event event1, Event event2) {
            if (event1.trial() == event2.trial()) {
                if (event1.age_group() == event2.age_group()) {
                    if (event1.discipline() == event2.discipline()) {
                        return 0;
                    }
                    return event1.discipline().ordinal() - event2.discipline().ordinal();
                }
                return event1.age_group().ordinal() - event2.age_group().ordinal();
            }
            return event1.trial().ordinal() - event2.trial().ordinal();
        }
    }

    public static class SortEventsByTrialStationAgegroup implements Comparator<Event> {

        @Override
        public int compare(Event event1, Event event2) {
            if (event1.trial() == event2.trial()) {
                if (event1.station() == event2.station()) {
                    if (event1.age_group() == event2.age_group()) {
                        return 0;
                    }
                    return event1.age_group().ordinal() - event2.age_group().ordinal();
                }
                return event1.station().ordinal() - event2.station().ordinal();
            }
            return event1.trial().ordinal() - event2.trial().ordinal();
        }
    }

}
