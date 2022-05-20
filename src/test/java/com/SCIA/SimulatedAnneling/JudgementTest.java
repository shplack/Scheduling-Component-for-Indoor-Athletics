package com.SCIA.SimulatedAnneling;


import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.Gender;
import com.SCIA.Competitions.AgeGroups;
import com.SCIA.Competitions.Trials;
import com.SCIA.Discipline.Disciplines;
import com.SCIA.Discipline.Stations;
import com.SCIA.Schedule.Event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JudgementTest {
    ArrayList<Event> eventList;
    int conflict = 0;

    @BeforeEach
    void setUp() {
        Event event = new Event(
                new ArrayList<>(List.of(1)),
                new ArrayList<>(Arrays.asList(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
                Stations.Station.LONG_TRIPLE_I,
                Disciplines.Discipline.TRIPLE_JUMP,
                Trials.Trial.TRIAL_I,
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
                Stations.Station.LONG_TRIPLE_II,
                Disciplines.Discipline.TRIPLE_JUMP,
                Trials.Trial.TRIAL_I,
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
                Stations.Station.LONG_TRIPLE_II,
                Disciplines.Discipline.TRIPLE_JUMP,
                Trials.Trial.TRIAL_I,
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
                Trials.Trial.TRIAL_I,
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
                Trials.Trial.TRIAL_I,
                AgeGroups.AgeGroup.NINETEEN_AND_OVER,
                Gender.MALE
        );

        eventList  = new ArrayList<>(List.of(event,event_2,event_3));
    }



    @Test
    void getTimeSlot() {
        Judgement.getConflicts(eventList);




    }






}