package Discipline;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static Discipline.Disciplines.Discipline.LONG_JUMP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DisciplineRecordsTest {

    private DisciplineRecords disciplineRecords;

    @BeforeEach
    void setUp() {
        disciplineRecords = new DisciplineRecords();
        disciplineRecords.addRecord(LONG_JUMP, 9.45f);
    }

    @Test
    void getRecords() {
        assertEquals(disciplineRecords.getRecords(LONG_JUMP), List.of(9.45f));
    }

    @Test
    @Disabled
    void getBestRecord() {
    }

    @Test
    @Disabled
    void getWorstRecord() {
    }

    @Test
    @Disabled
    void getDisciplines() {
    }

    @Test
    @Disabled
    void testGetDisciplines() {
    }

    @Test
    @Disabled
    void testGetDisciplines1() {
    }

    @Test
    @Disabled
    void addRecord() {
    }

    @Test
    @Disabled
    void testToString() {
    }

    @Test
    @Disabled
    void deepCopy() {
    }
}