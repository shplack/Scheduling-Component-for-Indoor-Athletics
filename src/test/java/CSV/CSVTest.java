package CSV;

import Athlete.Athlete;
import Athlete.AthleteRecord;
import Athlete.Gender;
import Discipline.DisciplineRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static Discipline.Disciplines.Discipline.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CSVTest {

    private static float stringTimeToSeconds(String time) {
        float result ;
        if (time.contains(":")) {
            String[] parts = time.split(":");
            float minutes = Float.parseFloat(parts[0]);
            float seconds = Float.parseFloat(parts[1]);
            result = minutes * 60 + seconds;
        }
        else {
            result = Float.parseFloat(time);
        }
        return result;
    }

    AthleteRecord athleteRecord;
    DisciplineRecords disciplineRecords = new DisciplineRecords();

    @BeforeEach
    void setUp() throws IOException {
        String csv_file_path = System.getProperty("user.dir") + "/src/main/resources/registration-list.csv";
        CSV csv = new CSV(csv_file_path);

        athleteRecord = csv.getRecords().get(0);

        disciplineRecords.addRecord(SPRINT60M, 9.49666886618982f);
        disciplineRecords.addRecord(MIDDLE800M, stringTimeToSeconds("03:17.60"));
        disciplineRecords.addRecord(LONG3000M, stringTimeToSeconds("13:47.41"));
        disciplineRecords.addRecord(LONG_JUMP, 7.20284000885032f);
        disciplineRecords.addRecord(TRIPLE_JUMP, 17.0951993478996f);
        disciplineRecords.addRecord(HIGH_JUMP, 2.16875896159374f);
        disciplineRecords.addRecord(SHOT_PUT, 15.3692192104435f);
    }


    @Test
    void getRecords() {
        assertEquals(
                athleteRecord,
                new AthleteRecord(
                    new Athlete(
                            1,
                            "Madrid IF",
                            "Vincent",
                            "Hughes ",
                            Gender.MALE,
                            14
                    ),
                    disciplineRecords
                )
        );
    }
}