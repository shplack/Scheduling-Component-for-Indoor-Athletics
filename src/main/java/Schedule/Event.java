package Schedule;

import Athlete.Athlete;
import Discipline.Discipline;
import Discipline.Station;

import java.util.ArrayList;
import java.util.Arrays;

public record Event(int[] time_slots, ArrayList<Athlete> athletes, Station station, Discipline discipline) {
    public enum Property {
        TIME_SLOTS,
        ATHLETES,
        STATION,
        DISCIPLINE
    }

    public Event deepCopy() {
        return new Event(Arrays.copyOf(time_slots, time_slots.length), new ArrayList<>(athletes), station, discipline);
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

        if (!station.equals(event.station)) {
            return false;
        }

        return discipline.equals(event.discipline);
    }
}
