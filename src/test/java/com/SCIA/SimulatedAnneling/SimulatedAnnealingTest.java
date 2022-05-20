package com.SCIA.SimulatedAnneling;

import com.SCIA.Athlete.*;
import com.SCIA.Discipline.*;
import com.SCIA.Schedule.Event.Event;
import com.SCIA.SimulatedAnneling.Judgement;
import com.SCIA.SimulatedAnneling.Mutation;
import org.junit.jupiter.api.BeforeEach;
import com.SCIA.Schedule.*;
import org.junit.jupiter.api.Test;

import com.SCIA.Athlete.*;
import com.SCIA.Discipline.Disciplines.Discipline;
import com.SCIA.Discipline.Stations.Station;


import com.SCIA.Schedule.Event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SimulatedAnnealingTest {
    List<Event> eventList;
    int conflicts = 1;
    Schedule schedule;

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



        eventList = new ArrayList<>(List.of(event, event_2, event_3, event_4,event_5));
    }

    @Test
    void SimulatedAnnealing() throws IOException {

        CSV csv = new CSV("src/main/resources/registration-list.csv");
        ArrayList<AthleteRecord> athlete_records = csv.getRecords();
        ArrayList<CompetitionGroup> competition_groups = CompetitionGroupsMaker.makeCompetitionGroups(athlete_records);
        schedule = ScheduleMaker.makeSchedule(competition_groups);

        float heat = InitialHeat.initialHeat(eventList);
        int value = 0;
        while (value == 0) {
            //copy of list declared here and put into mutation
            //List<Event> copyEventList = deepcopy(eventList);

            List<Event> returnedList = Mutation.MutationFunction(schedule.eventList());

            //int score = newConflicts - oldConflicts

            conflicts = Judgement.getConflicts(returnedList);
            if(conflicts == 15)
                value = 1;
        }
    }
}