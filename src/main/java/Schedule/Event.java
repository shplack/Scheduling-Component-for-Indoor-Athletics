package Schedule;

import Athlete.Athlete;

import java.util.ArrayList;
import java.util.Arrays;

import static Competitions.Trials.Trial;
import static Discipline.Disciplines.Discipline;
import static Discipline.Stations.Station;

public record Event(int[] time_slots, ArrayList<Athlete> athletes, Station station, Discipline discipline, Trial trial) {
    public enum Property {
        TIME_SLOTS,
        ATHLETES,
        STATION,
        DISCIPLINE,
        TRIAL
    }

    public Event deepCopy() {
        return new Event(Arrays.copyOf(time_slots, time_slots.length), new ArrayList<>(athletes), station, discipline, trial);
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

        return trial.equals(event.trial);
    }

    public String toString() {
        if (time_slots.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(
                TimeSlot.toString(time_slots[0]) + " " +
                TimeSlot.toString(time_slots[time_slots.length - 1] + 1) + " " +
                discipline.toString() + "\t\t" +
                trial.toString() + "  \t" +
                station.toString() + "\t"
        );

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
