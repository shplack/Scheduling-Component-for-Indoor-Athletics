package com.SCIA.Schedule;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Athlete.Gender;
import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.AgeGroups;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Competitions.Trials;
import com.SCIA.Competitions.Trials.Trial.Order;
import com.SCIA.Discipline.Disciplines;
import com.SCIA.Discipline.Stations;
import com.SCIA.Schedule.Event.Event;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.SCIA.Competitions.Trials.Trial.AWARD;
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
    void allEventsEndOnTheSameDayStarted() {
        int i = 0;
        List<Event> failedEvents = new LinkedList<>();

        while (i < schedule.eventList().size()) {
            Event event = schedule.eventList().get(i++);
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

    boolean hasConflict(Event event1, Event event2) {
        if (event1.age_group() != event2.age_group())
            return false;

        if (event1.discipline().isRunningDiscipline() && event2.discipline().isRunningDiscipline()) {
            if (event1.trial() != Trials.Trial.QUALIFYING && event2.trial() != Trials.Trial.QUALIFYING) {
                if (event1.trial() == event2.trial() && (event1.trial() != AWARD && event2.trial() != AWARD)) {
                    return false;
                }
            }
        }

        Set<Athlete> intersectingAthletes = event1.athletes().stream().distinct().filter(event2.athletes()::contains)
                .collect(Collectors.toSet());

        if (intersectingAthletes.isEmpty())
            return false;

        List<Integer> time_slots1 = event1.time_slots();
        int start1 = time_slots1.get(0);
        int end1 = time_slots1.get(time_slots1.size()-1);

        List<Integer> time_slots2 = event2.time_slots();
        int start2 = time_slots2.get(0);
        int end2 = time_slots2.get(time_slots2.size()-1);

        if (start1 >= start2 && start1 <= end2)
            return true;
        if (end1 >= start2 && end1 <= end2)
            return true;
        if (start2 >= start1 && start2 <= end1)
            return true;
        if (end2 >= start1 && end2 <= end1)
            return true;

        return false;
    }

    @Test
    void noConflicts() {
        List<Event> eventList = new ArrayList<>(schedule.eventList());
        Map<Event, List<Event>> conflictingEvents = new HashMap<>();

        for (int i = 0; i < eventList.size() - 1; i++) {
            for (int j = i + 1; j < eventList.size(); j++) {
                Event event1 = eventList.get(i);
                Event event2 = eventList.get(j);
                if (hasConflict(event1, event2)) {
                    if (hasConflict(event2, event1)) {
                        if (!conflictingEvents.containsKey(event1))
                            conflictingEvents.put(event1, new LinkedList<>());
                        conflictingEvents.get(event1).add(event2);
                    }
                }
            }
        }

        conflictingEvents.forEach((event, conflicts) -> {
            StringBuilder stringBuilder = new StringBuilder("Conflicting events: " + event + "\n");
            conflicts.forEach(event1 -> stringBuilder.append("\t").append(event1).append("\n"));
            System.out.println(stringBuilder);
        });

        if (conflictingEvents.isEmpty())
            System.out.println("No conflicting events found.");

        assertTrue(conflictingEvents.isEmpty());

    }
}