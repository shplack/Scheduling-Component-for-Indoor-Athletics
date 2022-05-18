package com.SCIA.Schedule;

import com.SCIA.Schedule.Event.Event;

import java.util.List;

public record Schedule(List<Event> eventList) {

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Day:\t")
                .append("Start:\t")
                .append("Day:\t")
                .append("End:\t")
                .append("com.SCIA.Discipline:\t")
                .append("Trial:\t\t\t")
                .append("Station:\t\t")
                .append("Age group:\t\t")
                .append("Athletes:\n");

        for (Event event : eventList)
            stringBuilder.append(event.toString()).append("\n");

        return stringBuilder.toString();
    }
}

