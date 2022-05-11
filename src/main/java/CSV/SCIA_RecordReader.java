package CSV;

import Athlete.Athlete;
import Discipline.Discipline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SCIA_RecordReader {
    static Athlete getAthlete(int id, List<String> columns) {
        Map<Athlete.Property, String> properties = new HashMap<>();

        properties.put(Athlete.Property.ID, String.valueOf(id));
        for (int i = 0; i < columns.size(); i++)
            properties.put(Athlete.Property.values()[i], columns.get(i));

        return new Athlete(properties);
    }

    static Map<Discipline, Float> getDisciplineRecords(List<String> columns) {
        Map<Discipline, Float> discipline_records = new HashMap<>();

        for (int i = 0; i < columns.size(); i++) {
            Discipline discipline = Discipline.values()[i];

            float record = -1.0f;
            if (i >= Discipline.LONG_JUMP.ordinal()) // distance in meters
                record = Float.parseFloat(columns.get(i));
            /*
            else // time in milliseconds
                record = getMilliseconds(columns.get(i)); // TODO: rezas magic
            */

            discipline_records.put(discipline, record);
        }

        return discipline_records;
    }
}
