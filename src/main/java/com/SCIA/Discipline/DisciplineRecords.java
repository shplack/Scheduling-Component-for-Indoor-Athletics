package com.SCIA.Discipline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DisciplineRecords extends HashMap<Discipline, ArrayList<Float>> {

    /**
     * Create a new DisciplineRecord
     */
    public DisciplineRecords() {
        super();
    }

    /**
     * Copy constructor
     *
     * @param disciplineRecords The record to copy
     */
    public DisciplineRecords(DisciplineRecords disciplineRecords) {
        super(disciplineRecords);
    }

    /**
     * Sort the records in the list, and if lowerIsBetter is true, then sort them in ascending order, otherwise sort them
     * in descending order.
     *
     * @param records       The list of records to sort.
     * @param lowerIsBetter If true, then the lower the score, the better. If false, then the higher the score, the better.
     */
    public static void sortRecords(List<Float> records, boolean lowerIsBetter) {
        records.sort((record1, record2) -> {
            int delta = record1.compareTo(record2);
            return delta * (lowerIsBetter ? -1 : 1);
        });
    }

    /**
     * Returns the records of a given discipline
     *
     * @param discipline The discipline you want to get the records for.
     * @return The records array
     */
    public ArrayList<Float> getRecords(Discipline discipline) {
        return get(discipline);
    }

    /**
     * Get the best record for a given discipline.
     *
     * @param discipline The discipline to get the best record for.
     * @return The best record for a given discipline.
     */
    public float getBestRecord(Discipline discipline) {
        ArrayList<Float> records = new ArrayList<>(getRecords(discipline));
        sortRecords(records, discipline.isMeasuredInTime());
        return records.get(0);
    }

    /**
     * Get the worst record for a given discipline.
     *
     * @param discipline The discipline to get the records for.
     * @return The worst record for a given discipline.
     */
    public float getWorstRecord(Discipline discipline) {
        ArrayList<Float> records = new ArrayList<>(getRecords(discipline));
        sortRecords(records, discipline.isMeasuredInTime());
        return records.get(records.size() - 1);
    }

    /**
     * Returns a list of disciplines, sorted or not, in ascending or descending order.
     *
     * @param sorted    whether to sort the list
     * @param ascending If true, the list will be sorted in ascending order. If false, the list will be sorted in
     *                  descending order.
     * @return A list of disciplines.
     */
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

    /**
     * Returns a list of disciplines, sorted or not;
     *
     * @param sorted true if you want the list to be sorted by name, false if you want it in the order it was added.
     * @return A list of disciplines.
     */
    public List<Discipline> getDisciplines(boolean sorted) {
        return getDisciplines(sorted, true);
    }

    /**
     * Return the disciplines the athlete has a record for.
     *
     * @return A list of disciplines.
     */
    public List<Discipline> getDisciplines() {
        return getDisciplines(false);
    }

    /**
     * If the discipline is not in the map, add it, then add the record to the discipline's list.
     *
     * @param discipline The discipline to add the record to.
     * @param record the record to add
     */
    public void addRecord(Discipline discipline, float record) {
        if (get(discipline) == null)
            put(discipline, new ArrayList<>());

        get(discipline).add(record);
    }

    /**
     * Return a formatted string of each discipline record with a prefix
     *
     * @param prefix the prefix to be added to each line of the string
     * @return A string representation of the athlete's records.
     */
    public String toString(String prefix) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Discipline discipline : getDisciplines()) {
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

    /**
     * Returns a deep copy of the discipline record
     *
     * @return A deep copy
     */
    public DisciplineRecords deepCopy() {
        DisciplineRecords disciplineRecords = new DisciplineRecords();

        for (Discipline discipline : keySet()) {
            disciplineRecords.put(discipline, new ArrayList<>(get(discipline)));
        }

        return disciplineRecords;
    }
}
