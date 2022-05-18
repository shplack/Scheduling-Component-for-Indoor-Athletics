package com.SCIA.Schedule;

import com.SCIA.Competitions.Trials.Trial.Order;
import com.SCIA.Schedule.Event.Event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Schedule(List<Event> eventList) {

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Day:\t")
                .append("Start:\t")
                .append("Day:\t")
                .append("End:\t")
                .append("Discipline:\t")
                .append("Trial:\t\t\t")
                .append("Station:\t\t")
                .append("Age group:\t\t")
                .append("Athletes:\n");

        for (Event event : eventList)
            stringBuilder.append(event.toString()).append("\n");

        return stringBuilder.toString();
    }

    static class SortEventByTrialAgeGroupDiscipline implements Comparator<Event> {

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

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    public String inOrder() {
        ArrayList<Event> events = new ArrayList<>(List.copyOf(eventList));
        events.sort(new SortEventByTrialAgeGroupDiscipline());
        return new Schedule(events).toString();
    }
}

