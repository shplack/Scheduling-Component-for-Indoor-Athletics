package com.ui;

import com.SCIA.CSV.CSV;
import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.Schedule;
import com.SCIA.Schedule.ScheduleMaker;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class UserInterface {
    static Console console = System.console();

    /**
     * If the path is a directory, not a file, not found, executable, or not readable, then return false. Otherwise, return
     * true
     *
     * @param path The path to the file you want to check.
     * @return A boolean value.
     */
    public static boolean checkPermissions(Path path) {
        if (Files.isDirectory(path))
            console.printf("You've entered a directory. Please try again with the file path.");
        else if (Files.notExists(path))
            console.printf("File not found. Check your file path and try again.\n");
        else if (Files.isExecutable(path))
            console.printf("The file should not be an executable. Try again.\n");
        else if (!Files.isReadable(path))
            console.printf("Unable to read file. Check permissions and try again.\n");
        else
            return true;
        return false;
    }

    /**
     * It prompts the user for a file path, and returns the file path
     *
     * @param msg The message to display to the user.
     * @return The file path.
     */
    public static String getFilePath(String msg) {
        console.printf(msg);
        String filePath = console.readLine();
        while (filePath.isBlank())
            console.printf("You did not enter anything. " + msg);

        return filePath;
    }

    /**
     * Get a file path from the user, and keep asking until the user gives a valid file path.
     *
     * @param msg The message to display to the user when asking for the file path.
     * @return A file path
     */
    public static String getFile(String msg) {
        String filePath = getFilePath(msg);
        while (!checkPermissions(Paths.get(filePath)))
            filePath = getFilePath(msg);
        return filePath;
    }

    /**
     * It asks the user for a CSV file with athlete records, asks the user for a file to write the generated schedule to,
     * generates the schedule, and writes it to the file
     */
    public static void menu() {
        // Asking the user for a CSV file with athlete records, and if something goes wrong, it will ask the user to try
        // again.
        CSV csv;
        try {
            csv = new CSV(getFilePath("Please enter the path to your CSV with athlete records: "));
        } catch (IOException e) {
            console.printf("Something went wrong. Please try again.\n");
            menu();
            return;
        }

        File output = null;
        while (output == null) {
            // Asking the user for a file path, and keeps asking until the user gives a valid file path.
            console.printf("Please enter the full path and name of the file you wish the generated schedule should be written to: ");
            String outputFilePath = console.readLine();
            while (outputFilePath.isBlank()) {
                console.printf("You did not enter anything. Please enter the path to where the generated schedule should be written to: ");
            }


            // Checking if the file already exists. If it does, it asks the user if they want to overwrite the file. If
            // they do, it breaks out of the loop. If they don't, it sets the output to null, which will cause the loop
            // to run again.
            output = new File(outputFilePath);
            try {
                if (!output.createNewFile()) {
                    console.printf("File already exists. Overwrite? (y/n): ");
                    if (console.readLine().equalsIgnoreCase("y") || console.readLine().equalsIgnoreCase("yes"))
                        break;
                    output = null;
                }
            } catch (IOException e) {
                console.printf("Something unknown went wrong.");
                System.exit(-1);
            }
        }

        // Make a schedule from the given csv file
        Schedule schedule = ScheduleMaker.makeSchedule(csv);

        // Writing the schedule to the csv file.
        try (PrintWriter writer = new PrintWriter(output)) {
            StringBuilder sb = new StringBuilder();
            for (Event event : schedule.eventList())
                sb.append(event.timeSlot().getDay())
                        .append(",")
                        .append(event.timeSlot().getStartTime())
                        .append(",")
                        .append(event.timeSlot().getEndTime())
                        .append(",")
                        .append(event.discipline())
                        .append(",")
                        .append(event.trial())
                        .append(",")
                        .append(event.station())
                        .append(",")
                        .append(event.age_group())
                        .append(",")
                        .append(event.gender())
                        .append(",")
                        .append(event.athletes().stream().map(Athlete-> Athlete.name()+" "+Athlete.surname())
                                .collect(Collectors.joining(",")))
                        .append("\n");
            writer.write(sb.toString());
        } catch (IOException e) {
            console.printf("Something unknown went wrong.");
            System.exit(-1);
        }

        console.printf("The generated schedule has been written to: '" + output.getAbsolutePath() + "'.\n");
    }
}
