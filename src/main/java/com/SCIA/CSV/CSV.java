package com.SCIA.CSV;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Discipline.DisciplineRecords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    private static final String DELIMITER = ",";
    private static final int NUM_PROPERTIES = Athlete.Property.values().length - 1;

    private final String csvFilePath;
    private final ArrayList<AthleteRecord> records;
    private void readRecords() throws IOException {
        List<String> rows = Files.readAllLines(Path.of(csvFilePath));
        for (int i = 0; i < rows.size(); i++) {
            List<String> columns = new ArrayList<>(List.of(rows.get(i).split(DELIMITER)));
            List<String> athlete_columns = columns.subList(0, NUM_PROPERTIES);
            List<String> discipline_records_columns = columns.subList(NUM_PROPERTIES, columns.size());
            Athlete athlete = RecordReader.getAthlete(i+1, athlete_columns);
            DisciplineRecords discipline_records = RecordReader.getDisciplineRecords(discipline_records_columns);
            this.records.add(new AthleteRecord(athlete, discipline_records));
        }
    }

    public CSV(String csvFilePath) throws IOException {
        this.csvFilePath = csvFilePath;
        this.records = new ArrayList<>();
        this.readRecords();
    }

    public ArrayList<AthleteRecord> getRecords() {
        return records;
    }
}
