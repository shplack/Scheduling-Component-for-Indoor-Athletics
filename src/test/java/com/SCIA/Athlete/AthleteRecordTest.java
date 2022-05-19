package com.SCIA.Athlete;

import com.SCIA.Discipline.DisciplineRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.SCIA.Discipline.Disciplines.Discipline;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(athleteRecord.getDisciplineRecords(), copy);
    }

    @Test
    void getRecords() {
        assertEquals(athleteRecord.getRecords(Discipline.LONG_JUMP), List.of(2.34f));
    }
}