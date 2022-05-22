package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.Gender;
import com.SCIA.Schedule.TimeSlot;

import java.util.List;
import java.util.Objects;

import static com.SCIA.Competitions.AgeGroups.AgeGroup;
import static com.SCIA.Competitions.Trials.Trial;
import static com.SCIA.Discipline.Disciplines.Discipline;
import static com.SCIA.Discipline.Stations.Station;

public class Event {

    private final TimeSlot timeSlot;
    private final List<Athlete> athletes;
    private final Station station;
    private final Discipline discipline;
    private final Trial trial;
    private final AgeGroup age_group;
    private final Gender gender;

    public TimeSlot timeSlot() {
        return this.timeSlot;
    }

    public List<Athlete> athletes() {
        return this.athletes;
    }

    public Station station() {
        return this.station;
    }

    public Discipline discipline() {
        return this.discipline;
    }

    public Trial trial() {
        return this.trial;
    }

    public AgeGroup age_group() {
        return this.age_group;
    }

    public Gender gender() {
        return this.gender;
    }



    public Event(TimeSlot timeSlot, List<Athlete> athletes, Station station, Discipline discipline, Trial trial,
                 AgeGroup age_group, Gender gender) {
        this.timeSlot = Objects.requireNonNullElseGet(timeSlot, () -> new TimeSlot(0, 0));
        Objects.requireNonNull(athletes);
        Objects.requireNonNull(station);
        Objects.requireNonNull(discipline);
        Objects.requireNonNull(trial);
        Objects.requireNonNull(age_group);
        Objects.requireNonNull(gender);

        this.athletes = athletes;
        this.station = station;
        this.discipline = discipline;
        this.trial = trial;
        this.age_group = age_group;
        this.gender = gender;
    }

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

    public void assignTimeSlot(int startTime, int duration) {
        this.timeSlot.setTimeSlot(startTime, duration);
    }

    public void assignTimeSlot(TimeSlot timeSlot) {
        this.timeSlot.setTimeSlot(timeSlot);
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
        return new Event(new TimeSlot(timeSlot), List.copyOf(athletes), station, discipline, trial, age_group, gender);
    }

    public boolean equals(Event event) {
        if (!timeSlot.equals(event.timeSlot))
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
