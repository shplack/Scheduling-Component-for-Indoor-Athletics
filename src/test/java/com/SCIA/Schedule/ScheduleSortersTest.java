package com.SCIA.Schedule;

import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Schedule.ScheduleSorters.SortByTimeStationDisciplineAgeGroup;
import com.SCIA.Schedule.ScheduleSorters.SortEventByTrialAgeGroupDiscipline;
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
    void sortEventsByTrialAgeGroupDiscipline() {
        schedule.eventList().sort(new SortEventByTrialAgeGroupDiscipline());
        schedule.eventList().forEach(System.out::println);
    }

    @Test
    void sortEventsByTrialStationAgeGroup() {
        schedule.eventList().sort(new SortEventsByTrialStationAgegroup());
        System.out.println(schedule);
    }

    @Test
    void sortEventsByTimeStationDisciplineAgeGroup() {
        schedule.eventList().sort(new SortByTimeStationDisciplineAgeGroup());
        System.out.println(schedule);
    }

}