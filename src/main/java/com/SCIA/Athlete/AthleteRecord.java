package com.SCIA.Athlete;

import com.SCIA.Discipline.DisciplineRecords;
import util.Pair.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.SCIA.Discipline.Disciplines.Discipline;

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

    public List<Discipline> getDisciplines() {
        return getDisciplineRecords().getDisciplines();
    }
}
