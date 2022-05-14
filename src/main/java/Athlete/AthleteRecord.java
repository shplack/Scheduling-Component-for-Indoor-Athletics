package Athlete;

import Discipline.*;
import Pair.Pair;

import java.util.ArrayList;

public class AthleteRecord extends Pair<Athlete, DisciplineRecords>  {
    /**
     * Creates a new pair
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public AthleteRecord(Athlete key, DisciplineRecords value) {
        super(key, value);
    }

    public Athlete getAthlete() {
        return getKey();
    }

    public DisciplineRecords getDisciplineRecords() {
        return getValue();
    }

    public ArrayList<Float> getRecords(Discipline discipline) {
        return getDisciplineRecords().getRecords(discipline);
    }
}
