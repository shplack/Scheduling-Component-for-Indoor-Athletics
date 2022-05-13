package CSV;

import Athlete.Athlete;
import Athlete.Athlete.Property;
import Discipline.Discipline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SCIA_RecordReader {

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
        Map<Property, String> properties = new HashMap<>();

        properties.put(Property.ID, String.valueOf(id));
        for (int i = 0; i < columns.size(); i++)
                properties.put(Property.values()[i], columns.get(i));

        return new Athlete(properties);
    }

    static Map<Discipline, Float> getDisciplineRecords(List<String> columns) {
        Map<Discipline, Float> discipline_records = new HashMap<>();

        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).equals(""))
                continue;

            Discipline discipline = Discipline.values()[i];

            float record;
            if (i >= Discipline.LONG_JUMP.ordinal()) // distance in meters
                record = Float.parseFloat(columns.get(i));

            else {
                record = stringTimeToSeconds(columns.get(i));

            }

            discipline_records.put(discipline, record);
        }

        return discipline_records;
    }

}
