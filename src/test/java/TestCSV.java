import Athlete.Athlete;
import Athlete.AthleteRecord;
import CSV.SCIA_CSV;
import Discipline.Discipline;

import java.io.IOException;
import java.util.Map;

public class TestCSV {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TestCSV <csv file>");
            System.exit(1);
        }

        try {
            SCIA_CSV csv = new SCIA_CSV(args[0]);
            for (AthleteRecord athleteRecord : csv.getRecords()) {
                Athlete athlete = athleteRecord.getAthlete();
                System.out.println(athlete.toString());
                for (Map.Entry<Discipline, Float> disciplineRecord : athleteRecord.getDisciplineRecords().entrySet()) {
                    System.out.println("\t" + disciplineRecord.getKey().toString() + ": " + disciplineRecord.getValue());
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }

    }
}
