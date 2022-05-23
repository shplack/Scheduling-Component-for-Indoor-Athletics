package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.GenderGroup;
import com.SCIA.Competitions.AgeGroup;
import com.SCIA.Competitions.Trial;
import com.SCIA.Discipline.Discipline;
import com.SCIA.Discipline.Station;
import com.SCIA.Schedule.TimeSlot;

import java.util.List;
import java.util.Objects;

public record Event(TimeSlot timeSlot, List<Athlete> athletes, Station station, Discipline discipline, Trial trial,
                    AgeGroup age_group, GenderGroup gender) {

    /**
     * Event constructor. Only time slot may be null because it will be initialized with 0,0
     *
     * @param timeSlot   The time slot for the event
     * @param athletes   The participating athletes
     * @param station    The station the event will occur at
     * @param discipline The discipline the event is for
     * @param trial      The trial of the competition
     * @param age_group  The age group of the competition
     * @param gender     The gender group of the competition
     */
    public Event {
        if (timeSlot == null)
            timeSlot = new TimeSlot(0, 0);
        Objects.requireNonNull(athletes);
        Objects.requireNonNull(station);
        Objects.requireNonNull(discipline);
        Objects.requireNonNull(trial);
        Objects.requireNonNull(age_group);
        Objects.requireNonNull(gender);
    }

    /**
     * Copy an event's variables by reference except for the time slot
     *
     * @param event The event to copy
     */
    public Event(Event event) {
        this(
                new TimeSlot(0, 0),
                event.athletes,
                event.station,
                event.discipline,
                event.trial,
                event.age_group,
                event.gender
        );
    }

    /**
     * A method that assigns a time slot to an event.
     *
     * @param startTime the starting time slot
     * @param duration  the duration of the event
     */
    public void assignTimeSlot(int startTime, int duration) {
        this.timeSlot.setTimeSlot(startTime, duration);
    }

    /**
     * A method that assigns a time slot to an event.
     *
     * @param timeSlot the time slot to assign
     */
    public void assignTimeSlot(TimeSlot timeSlot) {
        this.timeSlot.setTimeSlot(timeSlot);
    }

    /**
     * Returns a deep copy of the event
     *
     * @return a deep copy of the given event
     */
    public Event deepCopy() {
        return new Event(new TimeSlot(timeSlot), List.copyOf(athletes), station, discipline, trial, age_group, gender);
    }

    /**
     * Returns if the two events' properties are equal
     *
     * @param event The event to compare
     * @return True if the properties are equal, otherwise false.
     */
    public boolean equals(Event event) {
        if (!timeSlot.equals(event.timeSlot))
            return false;

        if (athletes.size() != event.athletes.size()) {
            return false;
        } else {
            for (int i = 0; i < athletes.size(); i++) {
                if (!athletes.get(i).equals(event.athletes.get(i))) {
                    return false;
                }
            }
        }

        if (!station.equals(event.station))
            return false;

        if (!discipline.equals(event.discipline))
            return false;

        if (!age_group.equals(event.age_group))
            return false;

        if (!gender.equals(event.gender))
            return false;

        return trial.equals(event.trial);
    }

    /**
     * Return a formatted string of the event
     *
     * @return The formatted string
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Day: ").append(timeSlot.getDay()).append(", ")
                .append(timeSlot.getStartTime()).append(" - ")
                .append(timeSlot.getEndTime()).append("\t");

        stringBuilder.append(discipline).append("\t")
                .append(trial).append("  \t")
                .append(station).append("\t")
                .append(age_group).append("\t\t")
                .append(gender).append("\t");

        for (Athlete athlete : athletes) {
            stringBuilder
                    .append(athlete.name())
                    .append(" ")
                    .append(athlete.surname())
                    .append(", ");
        }

        return stringBuilder.toString();
    }


}
