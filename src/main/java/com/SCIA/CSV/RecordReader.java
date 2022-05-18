package com.SCIA.CSV;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Discipline.DisciplineRecords;
import com.SCIA.Discipline.Disciplines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RecordReader {

    private static float stringTimeToSeconds(String time) {
        float result ;
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

    static Athlete getAthlete(int id, List<String> columns) {
        Map<Athlete.Property, String> properties = new HashMap<>();

        properties.put(Athlete.Property.ID, String.valueOf(id));
        for (int i = 0; i < columns.size(); i++)
                properties.put(Athlete.Property.values()[i], columns.get(i));

        return new Athlete(properties);
    }

    static DisciplineRecords getDisciplineRecords(List<String> columns) {
        DisciplineRecords discipline_records = new DisciplineRecords();

        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).equals(""))
                continue;

            Disciplines.Discipline discipline = Disciplines.Discipline.values()[i];

            float record;
            if (i >= Disciplines.Discipline.LONG_JUMP.ordinal()) // distance in meters
                record = Float.parseFloat(columns.get(i));

            else {
                record = stringTimeToSeconds(columns.get(i));

            }

            discipline_records.addRecord(discipline, record);
        }

        return discipline_records;
    }

}
