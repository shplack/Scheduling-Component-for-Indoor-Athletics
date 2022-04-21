import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import Athlete.Athlete;
import CSV.CSV;
public class Main {
    public static void main(String[] args)
            throws IOException
    {
        // Enter data using BufferReader
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(System.in));

        // Reading data using readLine
//        String name = reader.readLine();

        CSV csv = new CSV("/home/shplack/registration-list.csv");
//        for (List<String> record : csv.getRecords()) {
//            for (String field : record) {
//                System.out.print(field + " ");
//            }
//            System.out.println();
//        }

        for (Athlete athlete : csv.toAthletes()) {
            System.out.println(athlete.toString());
        }
    }
}