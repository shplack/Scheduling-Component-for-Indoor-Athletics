package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.Gender;
import com.SCIA.Schedule.TimeSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.SCIA.Competitions.AgeGroups.AgeGroup;
import static com.SCIA.Competitions.Trials.Trial;
import static com.SCIA.Discipline.Disciplines.Discipline;
import static com.SCIA.Discipline.Stations.Station;

public record Event(ArrayList<Integer> time_slots, ArrayList<Athlete> athletes, Station station, Discipline discipline, Trial trial,
                    AgeGroup age_group, Gender gender) {

    public Event {
        if (time_slots == null)
            time_slots = new ArrayList<>();
        Objects.requireNonNull(athletes);
        Objects.requireNonNull(station);
        Objects.requireNonNull(discipline);
        Objects.requireNonNull(trial);
        Objects.requireNonNull(age_group);
        Objects.requireNonNull(gender);
    }

    public Event(Event event) {
        this(
                new ArrayList<>(),
                event.athletes,
                event.station,
                event.discipline,
                event.trial,
                event.age_group,
                event.gender
        );
    }

    public void assignTimeSlots(List<Integer> time_slots) {
        this.time_slots.clear();
        this.time_slots.addAll(time_slots);
    }

    public boolean isSwappable(Event event) {
        // same age group, station, gender, not same event
        if (this.equals(event))
            return false;
        if (this.age_group != event.age_group)
            return false;
        if (this.station != event.station)
            return false;
        if (this.gender != event.gender)
            return false;

        return true;
    }

    public Event deepCopy() {
        return new Event(new ArrayList<>(time_slots), new ArrayList<>(athletes), station, discipline, trial, age_group, gender);
    }

    public boolean equals(Event event) {
        if (!time_slots.equals(event.time_slots))
            return false;

        if (athletes.size() != event.athletes.size()) {
            return false;
        }
        else {
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

    public String toString() {
                StringBuilder stringBuilder = new StringBuilder(
                discipline + "\t" +
                trial + "  \t" +
                station + "\t" +
                age_group + "\t\t" +
                gender + "\t"
        );

        for (Athlete athlete : athletes) {
            stringBuilder
                    .append(athlete.name())
                    .append(" ")
                    .append(athlete.surname())
                    .append(", ");
        }

        if (time_slots.size() > 0) {
            return
                TimeSlot.getStartTime(time_slots.get(0)) + "\t" +
                TimeSlot.getEndTime(time_slots.get(time_slots.size() - 1) + 1) + "\t" +
                stringBuilder;
        }

        return stringBuilder.toString();
    }


}
