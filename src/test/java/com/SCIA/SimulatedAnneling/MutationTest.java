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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MutationTest {

    ArrayList<Event> eventList;
    int conflict;

    @BeforeEach
    void setUp() {
        Event event = new Event(
                new ArrayList<>(List.of(1)),
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
                new ArrayList<>(List.of(4)),
                new ArrayList<>(List.of(new Athlete(
                        2,
                        "ÖÖÖ",
                        "Victor",
                        "Jonsson",
                        Gender.MALE,
                        30
                ))),
                Stations.Station.LONG_TRIPLE_I,
                Disciplines.Discipline.TRIPLE_JUMP,
                Trials.Trial.TRIAL,
                AgeGroups.AgeGroup.NINETEEN_AND_OVER,
                Gender.MALE
        );

        Event event_3 = new Event(
                new ArrayList<>(List.of(1)),
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


        Event event_4 = new Event(
                new ArrayList<>(List.of(3)),
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
                new ArrayList<>(List.of(3)),
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

        eventList = new ArrayList<>(List.of(event, event_2, event_3, event_4, event_5));

    }

    @Test
    void mutate() throws IOException {
        CSV csv = new CSV("registration-list.csv");
        ArrayList<AthleteRecord> athlete_records = csv.getRecords();
        ArrayList<CompetitionGroup> competition_groups = CompetitionGroupsMaker.makeCompetitionGroups(athlete_records);
        Schedule schedule = ScheduleMaker.makeSchedule(competition_groups);

        Mutation.MutationFunction(schedule.eventList());
    }


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
