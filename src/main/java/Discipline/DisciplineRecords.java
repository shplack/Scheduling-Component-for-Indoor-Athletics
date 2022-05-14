package Discipline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DisciplineRecords extends HashMap<Discipline, ArrayList<Float>> {

    public DisciplineRecords() {
        super();
    }

    public DisciplineRecords(DisciplineRecords disciplineRecords) {
        super(disciplineRecords);
    }

    public ArrayList<Float> getRecords(Discipline discipline) {
        return get(discipline);
    }

    private Float getRecord(Discipline discipline, int index) {
        if (get(discipline) == null)
            return null;

        ArrayList<Float> records =new ArrayList<>(get(discipline));
        records.sort(Float::compareTo);

        return records.get(index);
    }

    public Float getBestRecord(Discipline discipline) {
        return getRecord(discipline, 0);
    }

    public Float getWorstRecord(Discipline discipline) {
        return getRecord(discipline, get(discipline).size() - 1);
    }

    public List<Discipline> getDisciplines(boolean sorted, boolean ascending) {
        ArrayList<Discipline> disciplines = new ArrayList<>(keySet());
        if (!sorted)
            return disciplines;

        if (ascending)
            disciplines.sort(Comparator.comparing(Discipline::toString));
        else
            disciplines.sort(Comparator.comparing(Discipline::toString).reversed());

        return disciplines;
    }

    public List<Discipline> getDisciplines(boolean sorted) {
        return getDisciplines(sorted, true);
    }

    public List<Discipline> getDisciplines() {
        return getDisciplines(false);
    }

    public void addRecord(Discipline discipline, Float record) {
        if (get(discipline) == null)
            put(discipline, new ArrayList<>());

        get(discipline).add(record);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Discipline discipline : getDisciplines()) {
            stringBuilder.append("\t")
                    .append(discipline.toString())
                    .append(": ")
                    .append(get(discipline).toString())
                    .append("\n");
        }

        return stringBuilder.toString();
    }

    public DisciplineRecords deepCopy() {
        DisciplineRecords disciplineRecords = new DisciplineRecords();

        for (Discipline discipline : keySet()) {
            disciplineRecords.put(discipline, new ArrayList<>(get(discipline)));
        }

        return disciplineRecords;
    }
}
