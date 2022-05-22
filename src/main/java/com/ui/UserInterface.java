package com.ui;

import com.SCIA.CSV.CSV;
import com.SCIA.Schedule.Schedule;
import com.SCIA.Schedule.ScheduleMaker;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserInterface {
    static Console console = System.console();

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

    public static String getFilePath(String msg) {
        console.printf(msg);
        String filePath = console.readLine();
        while (filePath.isBlank())
            console.printf("You did not enter anything. " + msg);

        return filePath;
    }

    public static String getFile(String msg) {
        String filePath = getFilePath(msg);
        while (!checkPermissions(Paths.get(filePath)))
            filePath = getFilePath(msg);
        return filePath;
    }
    public static void menu() {
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
            console.printf("Please enter the full path and name of the file you wish the generated schedule should be written to: ");
            String outputFilePath = console.readLine();
            while (outputFilePath.isBlank()) {
                console.printf("You did not enter anything. Please enter the path to where the generated schedule should be written to: ");
            }


            output = new File(outputFilePath);
            FileWriter fileWriter;
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

        Schedule schedule = ScheduleMaker.makeSchedule(csv);
        try(FileWriter fileWriter = new FileWriter(output, false)) {
            // TODO: schedule to CSV record
        } catch (IOException e) {
            console.printf("Something unknown went wrong.");
            System.exit(-1);
        }

        console.printf("The generated schedule has been written to: '" + output.getAbsolutePath() + "'.");
    }
}
