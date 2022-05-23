package com.SCIA.Athlete;

import com.SCIA.Discipline.Discipline;
import com.SCIA.Discipline.DisciplineRecords;
import util.Pair.Pair;

import java.util.ArrayList;
import java.util.List;


public class AthleteRecord extends Pair<Athlete, DisciplineRecords> {

    /**
     * AthleteRecord constructor
     *
     * @param key   Athlete
     * @param value DisciplineRecords
     */
    public AthleteRecord(Athlete key, DisciplineRecords value) {
        super(key, value);
    }

    /**
     * Get the athlete
     *
     * @return Athlete
     */
    public Athlete getAthlete() {
        return getKey();
    }

    /**
     * Get the athlete's discipline records.
     *
     * @return The athlete's discipline records
     */
    public DisciplineRecords getDisciplineRecords() {
        return getValue();
    }

    /**
     * Get the records for a given discipline.
     *
     * @param discipline The discipline to get the records for.
     * @return A list of records for a given discipline.
     */
    public ArrayList<Float> getRecords(Discipline discipline) {
        return getDisciplineRecords().getRecords(discipline);
    }

    /**
     * Add a record to the athlete's discipline records.
     *
     * @param discipline The discipline to add the record to.
     * @param record The record to add.
     */
    public void addRecord(Discipline discipline, float record) {
        getDisciplineRecords().addRecord(discipline, record);
    }

    /**
     * Return a list of the disciplines the athlete has a record of.
     *
     * @return A list of disciplines.
     */
    public List<Discipline> getDisciplines() {
        return getDisciplineRecords().getDisciplines();
    }
}
