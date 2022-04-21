package CSV;

import Athlete.Athlete;
import Discipline.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class CSV {
    private static final String COMMA_DELIMITER = ",";
    private final String filePath;
    private final List<List<String>> records;

    public CSV(String filePath) throws IOException {
        this.filePath = filePath;

        records = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                List<String> values = new ArrayList<>();

                try (Scanner rowScanner = new Scanner(scanner.nextLine())) {
                    rowScanner.useDelimiter(COMMA_DELIMITER);

                    while (rowScanner.hasNext()) {
                        String value = rowScanner.next();
                        values.add(value);
                    }
                }

                for (int i = values.size(); i <= 15; i++)
                    values.add("");

                records.add(values);
            }
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public List<List<String>> getRecords() {
        return records;
    }

    public List<Athlete> toAthletes() {
        List<Athlete> athletes = new ArrayList<>();

        for (List<String> record : records) {
            String club = record.get(0);
            String name = record.get(1);
            String surname = record.get(2);
            String sex = record.get(3);
            int age = Integer.parseInt(record.get(4));
            Athlete athlete = new Athlete(club, name, surname, sex, age);

            String sprint60mTime = record.get(5);
            String sprint200mTime = record.get(6);
            String middle800mTime = record.get(7);
            String middle1500mTime = record.get(8);
            String long3000mTime = record.get(9);
            String hurdles60mTime = record.get(10);

            String jumpingLongDistance = record.get(11);
            String jumpingTripleDistance = record.get(12);
            String jumpingHighDistance = record.get(13);
            String jumpingPoleDistance = record.get(14);

            String throwingShotDistance = record.get(15);

            if (!sprint60mTime.equals(""))
                athlete.addDiscipline(new Running("spring", 60, sprint60mTime));
            if (!sprint200mTime.equals(""))
                athlete.addDiscipline(new Running("spring", 200, sprint200mTime));
            if (!middle800mTime.equals(""))
                athlete.addDiscipline(new Running("middle", 800, middle800mTime));
            if (!middle1500mTime.equals(""))
                athlete.addDiscipline(new Running("middle", 1500, middle1500mTime));
            if (!long3000mTime.equals(""))
                athlete.addDiscipline(new Running("long", 3000, long3000mTime));
            if (!hurdles60mTime.equals(""))
                athlete.addDiscipline(new Running("hurdles", 60, hurdles60mTime));

            if (!jumpingLongDistance.equals("")) {
                float distance = Float.parseFloat(jumpingLongDistance);
                athlete.addDiscipline(new Jumping("long", distance));
            }
            if (!jumpingTripleDistance.equals("")) {
                float distance = Float.parseFloat(jumpingTripleDistance);
                athlete.addDiscipline(new Jumping("triple", distance));
            }
            if (!jumpingHighDistance.equals("")) {
                float distance = Float.parseFloat(jumpingHighDistance);
                athlete.addDiscipline(new Jumping("high", distance));
            }
            if (!jumpingPoleDistance.equals("")) {
                float distance = Float.parseFloat(jumpingPoleDistance);
                athlete.addDiscipline(new Jumping("pole", distance));
            }

            if (!throwingShotDistance.equals("")) {
                float distance = Float.parseFloat(throwingShotDistance);
                athlete.addDiscipline(new Throwing("shot", distance));
            }

            athletes.add(athlete);
        }

        return athletes;
    }
}

