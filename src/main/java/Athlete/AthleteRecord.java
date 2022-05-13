package Athlete;

import Discipline.Discipline;
import javafx.util.Pair;

import java.util.Map;

public class AthleteRecord extends Pair<Athlete, Map<Discipline, Float>>  {
    /**
     * Creates a new pair
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public AthleteRecord(Athlete key, Map<Discipline, Float> value) {
        super(key, value);
    }

    public Athlete getAthlete() {
        return getKey();
    }

    public Map<Discipline, Float> getDisciplineRecords() {
        return getValue();
    }

    public float getRecord(Discipline discipline) {
        return getValue().get(discipline);
    }
}
