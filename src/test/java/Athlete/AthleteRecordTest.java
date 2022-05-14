package Athlete;

import Discipline.Discipline;
import Discipline.DisciplineRecords;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AthleteRecordTest  {
    AthleteRecord athleteRecord;

    @BeforeEach
    void setUp() {
        athleteRecord = new AthleteRecord(
                new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ),
                new DisciplineRecords()
        );

        athleteRecord.addRecord(Discipline.LONG_JUMP, 2.34f);
    }

    @Test
    void getDisciplineRecords() {
        DisciplineRecords copy = new DisciplineRecords(athleteRecord.getDisciplineRecords());
        assertTrue(athleteRecord.getDisciplineRecords().equals(copy));
    }

    @Test
    void getRecords() {
        assertTrue(athleteRecord.getRecords(Discipline.LONG_JUMP).equals(List.of(2.34f)));
    }
}