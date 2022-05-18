package com.SCIA.Schedule;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Competitions.Trials.Trial;
import com.SCIA.Competitions.Trials.Trial.Order;
import com.SCIA.Schedule.Event.Event;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.SCIA.Competitions.Trials.Trial.Order.EQUAL;
import static com.SCIA.Competitions.Trials.Trial.Order.LOWER;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    static Schedule schedule;
    static Logger logger = Logger.getLogger(ClassOrderer.ClassName.class.getName());

    @BeforeAll
    static void setUpAll() throws IOException {
        CSV csv = new CSV("registration-list.csv");
        ArrayList<AthleteRecord> athlete_records = csv.getRecords();
        ArrayList<CompetitionGroup> competition_groups = CompetitionGroupsMaker.makeCompetitionGroups(athlete_records);
        schedule = ScheduleMaker.makeSchedule(competition_groups);
    }

    @Test
    void nonEmptyEventList() {
        assertTrue(schedule.eventList().size() > 0);
    }

    @Test
    void outputSchedule() {
        String schedule_output = schedule.toString();
        assertTrue(schedule_output.length() > 0);
        System.out.println(schedule_output);
    }

    @Test
    void inOrder() {
        List<Event> events = schedule.eventList();
        events.sort(new Schedule.SortEventByTrialAgeGroupDiscipline());
        for (int i = 0, j = 1; j < events.size(); i++, j++) {
            Order order = events.get(i).trial().compareOrder(events.get(j).trial());
            assertTrue(order == LOWER || order == EQUAL);
        }
    }
}