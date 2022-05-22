package com.SCIA.Schedule;

import com.SCIA.Schedule.Event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Schedule {

    private final List<Event> eventList;

    public Schedule(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> eventList() {
        return eventList;
    }


    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Day:\t")
                .append("Start:\t")
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

    public Schedule deepCopy() {
        return new Schedule(eventList.stream().map(Event::deepCopy).collect(Collectors.toCollection(ArrayList::new)));
    }

    public String inOrder() {
        ArrayList<Event> events = new ArrayList<>(List.copyOf(eventList));
        events.sort(new ScheduleSorters.SortEventByTrialAgeGroupDiscipline());
        return new Schedule(events).toString();
    }
}

