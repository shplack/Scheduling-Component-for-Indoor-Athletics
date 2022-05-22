package com.SCIA.SimulatedAnneling;

import com.SCIA.Athlete.*;
import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.AgeGroups;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Competitions.Trials;
import com.SCIA.Discipline.Disciplines;
import com.SCIA.Discipline.Disciplines.Discipline;
import com.SCIA.Discipline.Stations;
import com.SCIA.Discipline.Stations.Station;


import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.Schedule;
import com.SCIA.Schedule.ScheduleMaker;
import com.SCIA.Schedule.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MutationTest {

    ArrayList<Event> eventList;
    int conflict;

    @BeforeEach
    void setUp() throws IOException {
        Event event = new Event(
                new TimeSlot(1, 1),
                new ArrayList<>(List.of(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
                Stations.Station.LONG_TRIPLE_I,
                Disciplines.Discipline.TRIPLE_JUMP,
                Trials.Trial.TRIAL,
                AgeGroups.AgeGroup.NINETEEN_AND_OVER,
                Gender.MALE
        );

        Event event_2 = new Event(
                new TimeSlot(3, 3),
                new ArrayList<>(List.of(new Athlete(
                        2,
                        "ÖÖÖ",
                        "Victor",
                        "Jonsson",
                        Gender.MALE,
                        30
                ))),
                Stations.Station.LONG_TRIPLE_II,
                Disciplines.Discipline.TRIPLE_JUMP,
                Trials.Trial.TRIAL,
                AgeGroups.AgeGroup.NINETEEN_AND_OVER,
                Gender.MALE
        );

        Event event_3 = new Event(
                new TimeSlot(1, 3),
                new ArrayList<>(List.of(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
                Stations.Station.LONG_TRIPLE_II,
                Disciplines.Discipline.TRIPLE_JUMP,
                Trials.Trial.TRIAL,
                AgeGroups.AgeGroup.NINETEEN_AND_OVER,
                Gender.MALE
        );


        Event event_4 = new Event(
                new TimeSlot(1, 3),
                new ArrayList<>(List.of(new Athlete(
                        2,
                        "ÖÖÖ",
                        "Victor",
                        "Jonsson",
                        Gender.MALE,
                        30
                ))),
                Stations.Station.POLE_VAULT,
                Disciplines.Discipline.POLE_VAULT,
                Trials.Trial.TRIAL,
                AgeGroups.AgeGroup.NINETEEN_AND_OVER,
                Gender.MALE
        );

        Event event_5 = new Event(
                new TimeSlot(1, 3),
                new ArrayList<>(List.of(new Athlete(
                        2,
                        "ÖÖÖ",
                        "Victoria",
                        "Jonsdottir",
                        Gender.MALE,
                        30
                ))),
                Stations.Station.POLE_VAULT,
                Disciplines.Discipline.POLE_VAULT,
                Trials.Trial.TRIAL,
                AgeGroups.AgeGroup.NINETEEN_AND_OVER,
                Gender.MALE
        );

//        eventList = new ArrayList<>(List.of(event, event_2, event_3, event_4, event_5));
        eventList = new ArrayList<>(ScheduleMaker.makeSchedule(new CSV("registration-list.csv")).eventList());
    }

    private static class StationEvents extends ArrayList<List<Event>> {
        static int numStations = Station.values().length;

        int numEvents = 0;
        public StationEvents() {
            super(numStations);
            for (int i = 0; i < numStations; i++)
                add(new LinkedList<>());
        }

        public StationEvents(List<Event> eventList) {
            this();
            ArrayList<Event> eventListCopy = new ArrayList<>(eventList);
            eventListCopy.sort((event1, event2) -> TimeSlot.compareTo(event1.timeSlot(), event2.timeSlot()));
            eventListCopy.forEach(this::addEvent);
        }

        public List<Event> getEvents(Station station) {
            return get(station.ordinal());
        }

        public ArrayList<Event> getAllEvents() {
            ArrayList<Event> events = new ArrayList<>(numEvents);
            for (int i = 0; i < numStations; i++)
                events.addAll(get(i));

            return events;
        }

        public void addEvent(Event event) {
            numEvents++;
            get(event.station().ordinal()).add(event);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (Station station : Station.values()) {
                stringBuilder.append(station).append(":\n");
                for (Event event : get(station.ordinal()))
                    stringBuilder.append("\t").append(event).append("\n");
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();
        }
    }

    StationEvents stationEvents() {
        eventList.sort((event1, event2) -> TimeSlot.compareTo(event1.timeSlot(), event2.timeSlot()));

        StationEvents stationEvents = new StationEvents();
        eventList.forEach(stationEvents::addEvent);

        return stationEvents;
    }

    @Test
    void printStationEvents() {
        System.out.println(stationEvents());
    }

    @Test
    void printAllEvents() {
        eventList.sort((event1, event2) -> TimeSlot.compareTo(event1.timeSlot(), event2.timeSlot()));
        eventList.forEach(System.out::println);
    }

    @Test
    void findHoles() {
        StationEvents stationEvents = new StationEvents(eventList);
        List<List<TimeSlot>> holes = new ArrayList<>(Station.values().length);
        for (int i = 0; i < Station.values().length; i++)
            holes.add(new LinkedList<>());

        for (Station station : Station.values()) {
            List<Event> events = stationEvents.getEvents(station);
            for (int i = 0; i < events.size()-1; i++) {
                TimeSlot before = events.get(i).timeSlot();
                TimeSlot after = events.get(i+1).timeSlot();
                int duration = before.compareTo(after) * -1;
                if (duration > 0)
                    holes.get(station.ordinal()).add(new TimeSlot(before.getLastTimeSlot()+1, duration));
            }
        }

        int counter = 0;
        for (List<TimeSlot> timeSlots : holes)
            counter += timeSlots.size();
        System.out.println(counter);
    }

    @Test
    void mutate() {
        StationEvents stationEvents = stationEvents();

    }


/*
    @Test
    void mutate() throws IOException {
        CSV csv = new CSV("registration-list.csv");
        ArrayList<AthleteRecord> athlete_records = csv.getRecords();
        ArrayList<CompetitionGroup> competition_groups = CompetitionGroupsMaker.makeCompetitionGroups(athlete_records);
        Schedule schedule = ScheduleMaker.makeSchedule(competition_groups);

        Mutation.MutationFunction(schedule.eventList());
    }


 */

    /*
    void mutationFunction() {
        Random rand = new Random();



        int rand_int1 = rand.nextInt(eventList.size());
        int rand_int2 = rand.nextInt(eventList.size());

        Event event1 = eventList.get(rand_int1);
        Event event2 = eventList.get(rand_int2);

        if(event1.isSwappable(event2)) {

            ArrayList<Integer> timeslot1 = event1.time_slots();
            ArrayList<Integer> timeslot2 = event2.time_slots();


            for (int i = 0; i < event1.time_slots().size(); i++) {
                event1.time_slots().set(i, timeslot2.get(i));
            }

            //System.out.println(eventList.get(0).time_slots()[0]);


            System.out.println("-----------------------");

            for (int i = 0; i < eventList.size(); i++) {
                event2.time_slots().set(i, timeslot1.get(i));
            }
        }

        /*
        Collections.shuffle(eventList);
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println(eventList.get(i).athletes().toString());
        }


    }
    */


}
