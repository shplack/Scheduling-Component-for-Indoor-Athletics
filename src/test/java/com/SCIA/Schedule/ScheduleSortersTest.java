package com.SCIA.Schedule;

import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Schedule.ScheduleSorters.SortEventsByTrialStationAgegroup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ScheduleSortersTest {

    static Schedule schedule;

    @BeforeAll
    static void setUp() throws IOException {
        schedule = ScheduleMaker.makeSchedule(CompetitionGroupsMaker.makeCompetitionGroups(new CSV("registration-list.csv").getRecords()));
    }

    @Test
    void sortEventsByStationAgeGroupTrial() {
        schedule.eventList().sort(new SortEventsByTrialStationAgegroup());
        schedule.eventList().forEach(System.out::println);
    }

}