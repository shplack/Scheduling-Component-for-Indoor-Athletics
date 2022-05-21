package com.SCIA.Schedule;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Competitions.Trials.Trial.Order;
import com.SCIA.Schedule.Event.Event;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static com.SCIA.Competitions.Trials.Trial.Order.EQUAL;
import static com.SCIA.Competitions.Trials.Trial.Order.LOWER;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    static Schedule schedule;

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
    void sortedSchedule() {
        System.out.println(schedule.inOrder());
    }

    @Test
    void inOrder() {
        List<Event> events = schedule.eventList();
        events.sort(new ScheduleSorters.SortEventsByTrialStationAgegroup());
        for (int i = 0, j = 1; j < events.size(); i++, j++) {
            Order order = events.get(i).trial().compareOrder(events.get(j).trial());
            assertTrue(order == LOWER || order == EQUAL);
        }
    }

    @Test
    void allEventsEndOnTheSameDayStarted() {
        int i = 0;
        List<Event> failedEvents = new LinkedList<>();

        while (i < schedule.eventList().size()) {
            Event event = null;
            event = schedule.eventList().get(i++);
            int start = event.time_slots().get(0);
            int end = event.time_slots().get(event.time_slots().size()-1);
            if (TimeSlot.getDay(start) != TimeSlot.getDay(end))
                failedEvents.add(event);
        }

        if (!failedEvents.isEmpty())
            failedEvents.forEach(event -> System.out.println("Failed event: " + event));
        else
            System.out.println("All events end the same day they start.");

        assertTrue(failedEvents.isEmpty());
    }

    @Test
    void allEventsEndOnTheSameDayStartedStringCompare() {
        List <Event> failedEvents = new LinkedList<>();

        schedule.eventList().forEach(event -> {
            List<Integer> time_slots = event.time_slots();
            int start_time = time_slots.get(0);
            int end_time = time_slots.get(time_slots.size()-1) + 1;
            if (TimeSlot.toString(start_time).compareTo(TimeSlot.toString(end_time)) > 0)
                failedEvents.add(event);
            System.out.println(event);
        });

        failedEvents.forEach(System.out::println);
        if (failedEvents.isEmpty())
            System.out.println("Test passed");

        assertTrue(failedEvents.isEmpty());
    }

    @Test
    void noEventEndsAt8() {
        List<Event> failedEvents = new LinkedList<>();

        schedule.eventList().forEach(event -> {
            List<Integer> time_slots = event.time_slots();
            int end = time_slots.get(time_slots.size()-1);
            String end_time = TimeSlot.toString(end + 1);
            boolean passed = !end_time.equals(TimeSlot.toString(1));
            if (!passed)
                failedEvents.add(event);
        });

        failedEvents.forEach(System.out::println);

        if (failedEvents.isEmpty())
            System.out.println("Test passed");
        else
            assert false;
    }
}