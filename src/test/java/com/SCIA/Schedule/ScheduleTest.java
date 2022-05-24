package com.SCIA.Schedule;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Schedule.Event.Event;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
            TimeSlot timeSlot = event.timeSlot();
            if (timeSlot.getDay() != TimeSlot.getDay(timeSlot.getLastTimeSlot()))
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
            if (event.timeSlot().getStartTime().compareTo(event.timeSlot().getEndTime()) >= 0)
                failedEvents.add(event);
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
            boolean passed = !event.timeSlot().getEndTime().equals(TimeSlot.toString(1));
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
        if (event1.trial().mustHappenBefore(event2.trial())) {
            if (event1.timeSlot().compareTo(event2.timeSlot()) > 0)
                return true;
        }

        if (event1.age_group() != event2.age_group())
            return false;

//        if (event1.discipline().isRunningDiscipline() && event2.discipline().isRunningDiscipline()) {
//            if (event1.trial() != Trials.Trial.QUALIFYING && event2.trial() != Trials.Trial.QUALIFYING) {
//                if (event1.trial() == event2.trial() && (event1.trial() != AWARD && event2.trial() != AWARD)) {
//                    return false;
//                }
//            }
//        }

        Set<Athlete> intersectingAthletes = event1.athletes().stream().distinct().filter(event2.athletes()::contains)
                .collect(Collectors.toSet());

        if (intersectingAthletes.isEmpty())
            return false;

        return event1.timeSlot().conflictsWith(event2.timeSlot());
    }

    @Test
    void noConflicts() {
        List<Event> eventList = new ArrayList<>(schedule.eventList());
        Map<Event, List<Event>> conflictingEvents = new HashMap<>();

        for (int i = 0; i < eventList.size() - 1; i++) {
            for (int j = i + 1; j < eventList.size(); j++) {
                Event event1 = eventList.get(i);
                Event event2 = eventList.get(j);
                if (hasConflict(event2, event1)) {
                    if (!conflictingEvents.containsKey(event1))
                        conflictingEvents.put(event1, new LinkedList<>());
                    conflictingEvents.get(event1).add(event2);
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

    @Test
    void noConflictingStationTimeSlots() {
        Map<Event, List<Event>> conflictingEvents = new HashMap<>();
        for (int i = 0; i < schedule.eventList().size(); i++) {
            Event event1 = schedule.eventList().get(i);
            for (int j = i + 1; j < schedule.eventList().size(); j++) {
                Event event2 = schedule.eventList().get(j);
                if (event1.station() == event2.station()) {
                    if (event1.timeSlot().compareTo(event2.timeSlot()) > 0) {
                        if (!conflictingEvents.containsKey(event1))
                            conflictingEvents.put(event1, new LinkedList<>());
                        conflictingEvents.get(event1).add(event2);
                    }
                }
            }
        }

        for (Map.Entry<Event, List<Event>> entry : conflictingEvents.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().forEach(event -> System.out.println("\t" + event));
        }

        assertTrue(conflictingEvents.isEmpty());
    }

    @Test
    void findHoles() {

    }


}