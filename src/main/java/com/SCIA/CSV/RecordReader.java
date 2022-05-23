package com.SCIA.CSV;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Discipline.Discipline;
import com.SCIA.Discipline.DisciplineRecords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RecordReader {

    /**
     * Parse a string and return the number of seconds
     *
     * @param time The time in a string
     * @return The time in seconds.
     */
    private static float stringTimeToSeconds(String time) {
        float result;
        if (time.contains(":")) {
            String[] parts = time.split(":");
            float minutes = Float.parseFloat(parts[0]);
            float seconds = Float.parseFloat(parts[1]);
            result = minutes * 60 + seconds;
        }
        else {
            result = Float.parseFloat(time);
        }
        return result;
    }

    /**
     * Parse an athlete from a list of strings
     *
     * @param id      The id of the athlete.
     * @param columns a list of the column names in the table
     * @return A map of the athlete's properties and their values.
     */
    static Athlete getAthlete(int id, List<String> columns) {
        Map<Athlete.Property, String> properties = new HashMap<>();

        // Adding the athlete's properties to the map.
        properties.put(Athlete.Property.ID, String.valueOf(id));
        for (int i = 0; i < columns.size(); i++)
                properties.put(Athlete.Property.values()[i], columns.get(i));

        return new Athlete(properties);
    }

    /**
     * It takes a list of strings, each string representing a record in a discipline, and generates a
     * DisciplineRecord
     *
     * @param columns a list of strings, each string is a record for a discipline
     * @return A DisciplineRecords object.
     */
    static DisciplineRecords getDisciplineRecords(List<String> columns) {
        DisciplineRecords discipline_records = new DisciplineRecords();

        // Iterating through the list of columns and adding the records to the discipline_records object.
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).equals(""))
                continue;

            Discipline discipline = Discipline.values()[i];

            float record = 0;
            // Checking if the discipline is measured in distance or time and then converting the record to a float.
            if (discipline.isMeasuredInDistance())
                record = Float.parseFloat(columns.get(i));
            else if (discipline.isMeasuredInTime())
                record = stringTimeToSeconds(columns.get(i));

            discipline_records.addRecord(discipline, record);
        }

        return discipline_records;
    }

}
