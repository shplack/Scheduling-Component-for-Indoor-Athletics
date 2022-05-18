package com.SCIA.Discipline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DisciplineRecords extends HashMap<Disciplines.Discipline, ArrayList<Float>> {

    public DisciplineRecords() {
        super();
    }

    public DisciplineRecords(DisciplineRecords disciplineRecords) {
        super(disciplineRecords);
    }

    public ArrayList<Float> getRecords(Disciplines.Discipline discipline) {
        return get(discipline);
    }

    private Float getRecord(Disciplines.Discipline discipline, int index) {
        if (get(discipline) == null)
            return null;

        ArrayList<Float> records =new ArrayList<>(get(discipline));
        records.sort(Float::compareTo);

        return records.get(index);
    }

    public Float getBestRecord(Disciplines.Discipline discipline) {
        return getRecord(discipline, 0);
    }

    public Float getWorstRecord(Disciplines.Discipline discipline) {
        return getRecord(discipline, get(discipline).size() - 1);
    }

    public List<Disciplines.Discipline> getDisciplines(boolean sorted, boolean ascending) {
        ArrayList<Disciplines.Discipline> disciplines = new ArrayList<>(keySet());
        if (!sorted)
            return disciplines;

        if (ascending)
            disciplines.sort(Comparator.comparing(Disciplines.Discipline::toString));
        else
            disciplines.sort(Comparator.comparing(Disciplines.Discipline::toString).reversed());

        return disciplines;
    }

    public List<Disciplines.Discipline> getDisciplines(boolean sorted) {
        return getDisciplines(sorted, true);
    }

    public List<Disciplines.Discipline> getDisciplines() {
        return getDisciplines(false);
    }

    public void addRecord(Disciplines.Discipline discipline, Float record) {
        if (get(discipline) == null)
            put(discipline, new ArrayList<>());

        get(discipline).add(record);
    }

    public String toString(String prefix) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Disciplines.Discipline discipline : getDisciplines()) {
            stringBuilder.append(prefix).append(discipline.toString()).append(": ");
            List<Float> records = getRecords(discipline);
            for (int i = 0; i < records.size(); i++) {
                stringBuilder.append(prefix).append(prefix);
                if (i < records.size() - 1)
                    stringBuilder.append(records.get(i)).append(", ");
                else
                    stringBuilder.append(records.get(i));
            }
            stringBuilder.append(prefix).append("\n");
        }

        return stringBuilder.toString();
    }

    public DisciplineRecords deepCopy() {
        DisciplineRecords disciplineRecords = new DisciplineRecords();

        for (Disciplines.Discipline discipline : keySet()) {
            disciplineRecords.put(discipline, new ArrayList<>(get(discipline)));
        }

        return disciplineRecords;
    }
}