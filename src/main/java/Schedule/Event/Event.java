package Schedule.Event;

import Athlete.Athlete;
import Athlete.Gender;
import Schedule.TimeSlot;

import java.util.ArrayList;
import java.util.Arrays;

import static Competitions.AgeGroups.AgeGroup;
import static Competitions.Trials.Trial;
import static Discipline.Disciplines.Discipline;
import static Discipline.Stations.Station;

public record Event(int[] time_slots, ArrayList<Athlete> athletes, Station station, Discipline discipline, Trial trial, AgeGroup age_group,
                    Gender gender) {
    public enum Property {
        TIME_SLOTS,
        ATHLETES,
        STATION,
        DISCIPLINE,
        TRIAL,
        AGE_GROUP
    }

    public Event deepCopy() {
        return new Event(Arrays.copyOf(time_slots, time_slots.length), new ArrayList<>(athletes), station, discipline, trial, age_group, gender);
    }

    public boolean equals(Event event) {
        for (int i = 0; i < time_slots.length; i++) {
            if (time_slots[i] != event.time_slots[i]) {
                return false;
            }
        }

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
        if (time_slots != null && time_slots.length == 0) {
            return "";
        }

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

        if (time_slots != null) {
            return
                TimeSlot.toString(time_slots[0]) + "\t" +
                TimeSlot.toString(time_slots[time_slots.length - 1] + 1) + "\t" +
                stringBuilder;
        }

        return stringBuilder.toString();
    }


}
