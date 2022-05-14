import Athlete.Athlete;
import Athlete.AthleteRecord;
import CSV.CSV;

import java.io.IOException;

public class TestCSV {


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TestCSV <csv file>");
            System.exit(1);
        }

        try {
            CSV csv = new CSV(args[0]);
            for (AthleteRecord athleteRecord : csv.getRecords()) {
                Athlete athlete = athleteRecord.getAthlete();
                System.out.println(athlete.toString());
                System.out.println(athleteRecord.getDisciplineRecords().toString());
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }

    }
}
