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
    // The delimiter to be used to split the columns of the CSV file.
    private static final String DELIMITER = ",";
    // Getting the number of properties of the Athlete class.
    private static final int NUM_PROPERTIES = Athlete.Property.values().length - 1;

    private final String csvFilePath;
    private final ArrayList<AthleteRecord> records;

    /**
     * Read the contents of a given CSV
     *
     * @param csvFilePath The path to the CSV file
     * @throws IOException If something goes wrong
     */
    public CSV(String csvFilePath) throws IOException {
        this.csvFilePath = csvFilePath;
        this.records = new ArrayList<>();
        this.readRecords();
    }

    /**
     * Reads the records from the csv file and stores them in the records list
     */
    private void readRecords() throws IOException {
        List<String> rows = Files.readAllLines(Path.of(csvFilePath));
        // Reading the records from the csv file and storing them in the records list.
        for (int i = 0; i < rows.size(); i++) {
            // Splitting the row into columns.
            List<String> columns = new ArrayList<>(List.of(rows.get(i).split(DELIMITER)));
            // Creating a sublist of the columns list for athletes.
            List<String> athlete_columns = columns.subList(0, NUM_PROPERTIES);
            // Creating a sublist of the columns list for discipline records.
            List<String> discipline_records_columns = columns.subList(NUM_PROPERTIES, columns.size());
            Athlete athlete = RecordReader.getAthlete(i+1, athlete_columns);
            DisciplineRecords discipline_records = RecordReader.getDisciplineRecords(discipline_records_columns);
            this.records.add(new AthleteRecord(athlete, discipline_records));
        }
    }

    /**
     * This function returns the records of the athlete
     *
     * @return An ArrayList of AthleteRecord objects.
     */
    public ArrayList<AthleteRecord> getRecords() {
        return records;
    }
}
