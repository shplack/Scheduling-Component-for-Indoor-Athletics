package CSV;

import Athlete.Athlete;
import Athlete.Athlete.Property;
import Athlete.AthleteRecord;
import Discipline.Discipline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SCIA_CSV {
    private static final String DELIMITER = ",";
    private static final int NUM_PROPERTIES = Property.values().length - 1;

    private final String csvFilePath;
    private final ArrayList<AthleteRecord> records;
    private void readRecords() throws IOException {
        List<String> rows = Files.readAllLines(Path.of(csvFilePath));
        for (int i = 0; i < rows.size(); i++) {
            List<String> columns = new ArrayList<>(List.of(rows.get(i).split(DELIMITER)));
            List<String> athlete_columns = columns.subList(0, NUM_PROPERTIES);
            List<String> discipline_records_columns = columns.subList(NUM_PROPERTIES, columns.size());
            Athlete athlete = SCIA_RecordReader.getAthlete(i, athlete_columns);
            Map<Discipline, Float> discipline_records = SCIA_RecordReader.getDisciplineRecords(discipline_records_columns);
            this.records.add(new AthleteRecord(athlete, discipline_records));
        }
    }

    public SCIA_CSV(String csvFilePath) throws IOException {
        this.csvFilePath = csvFilePath;
        this.records = new ArrayList<>();
        this.readRecords();
    }

    public ArrayList<AthleteRecord> getRecords() {
        return records;
    }
}
