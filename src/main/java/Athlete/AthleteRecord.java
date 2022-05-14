package Athlete;

import Discipline.*;
import Pair.Pair;

import java.util.ArrayList;

public class AthleteRecord extends Pair<Athlete, DisciplineRecords>  {
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

    public void addRecord(Discipline discipline, float record) {
        getDisciplineRecords().addRecord(discipline, record);
    }
}
