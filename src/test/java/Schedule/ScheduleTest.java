package Schedule;

import Athlete.AthleteRecord;
import CSV.CSV;
import Competitions.CompetitionGroup;
import Competitions.CompetitionGroupsMaker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ScheduleTest {

    static Schedule schedule;

    @BeforeAll
    static void setUpAll() throws IOException {
        CSV csv = new CSV("src/main/resources/registration-list.csv");
        ArrayList<AthleteRecord> athlete_records = csv.getRecords();
        ArrayList<CompetitionGroup> competition_groups = CompetitionGroupsMaker.makeCompetitionGroups(athlete_records);
        schedule = ScheduleMaker.makeSchedule(competition_groups);
    }

    @Test
    @Disabled
    void nonEmptyEventList() {
        assertTrue(schedule.eventList().size() > 0);
    }

    @Test
    void outputSchedule() {
        String schedule_output = schedule.toString();
        assertTrue(schedule_output.length() > 0);
        System.out.println(schedule_output);
    }

}