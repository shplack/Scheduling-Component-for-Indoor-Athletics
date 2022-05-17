package Schedule;

import Athlete.AthleteRecord;
import CSV.CSV;
import Competitions.CompetitionGroup;
import Competitions.CompetitionGroupsMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ScheduleTest {
    Schedule schedule;
    @BeforeEach
    void setUp() throws IOException {
        CSV csv = new CSV("src/main/resources/registration-list.csv");
        ArrayList<AthleteRecord> athlete_records = csv.getRecords();
        ArrayList<CompetitionGroup> competition_groups = CompetitionGroupsMaker.makeCompetitionGroups(athlete_records);
        schedule = ScheduleMaker.makeSchedule(competition_groups);
        System.out.println(schedule.toString());
    }

    @Test
    void scheduleExists() {
        assertTrue(schedule.eventList().size() > 0);
    }

}