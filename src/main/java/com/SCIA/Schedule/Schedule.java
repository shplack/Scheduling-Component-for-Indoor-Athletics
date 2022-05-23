package com.SCIA.Schedule;

import com.SCIA.Schedule.Event.Event;

import java.util.ArrayList;
import java.util.List;

public record Schedule(List<Event> eventList) {

    /**
     * Deep copies a schedule
     *
     * @param schedule The schedule to copy
     */
    public Schedule(Schedule schedule) {
        this(schedule.eventList.stream().map(Event::deepCopy).toList());
    }

    /**
     * Returns a formatted string of the schedule and its events
     *
     * @return The formatted string
     */
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

    /**
     * Sorting the events by trial, age group and discipline and outputs the schedule as a string
     *
     * @return The formatted string of the sorted schedule
     */
    public String inOrder() {
        ArrayList<Event> events = new ArrayList<>(List.copyOf(eventList));
        events.sort(new ScheduleSorters.SortEventByTrialAgeGroupDiscipline());
        return new Schedule(events).toString();
    }
}

