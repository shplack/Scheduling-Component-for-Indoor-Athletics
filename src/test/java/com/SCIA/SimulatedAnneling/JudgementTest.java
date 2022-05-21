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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class JudgementTest {
    ArrayList<Event> eventList;
    int conflict = 0;

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
                Stations.Station.LONG_TRIPLE_II,
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
                Stations.Station.LONG_TRIPLE_II,
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

        eventList  = new ArrayList<>(List.of(event,event_2,event_3));
    }



    @Test
    void getTimeSlot() {
        Judgement.getConflicts(eventList);

        /*
                int conflicts = 0;
                //System.out.println("This is size: "+ eventList.size());
                for (int First_Value_To_Iterate_List = 0; First_Value_To_Iterate_List < eventList.size(); First_Value_To_Iterate_List++) {
                    for (int Second_value_To_Iterate_List = 0; Second_value_To_Iterate_List < eventList.size(); Second_value_To_Iterate_List++) {
                        for (int k = 0; k < eventList.get(First_Value_To_Iterate_List).time_slots().size(); k++)
                            for (int l = 0; l < eventList.get(Second_value_To_Iterate_List).time_slots().size(); l++)
                                if (Objects.equals(eventList.get(First_Value_To_Iterate_List).time_slots().get(k), eventList.get(Second_value_To_Iterate_List).time_slots().get(l)))
                                    for (int o = 0; o < eventList.get(First_Value_To_Iterate_List).athletes().size(); o++) {
                                        for (int p = 0; p < eventList.get(Second_value_To_Iterate_List).athletes().size(); p++) {
                                            if (eventList.get(First_Value_To_Iterate_List) != eventList.get(Second_value_To_Iterate_List)) {
                                                if ((eventList.get(First_Value_To_Iterate_List).athletes().get(o).id()) == eventList.get(Second_value_To_Iterate_List).athletes().get(p).id())
                                                {
                                                    //System.out.println("This is First_Value_To_Iterate_List: " + First_Value_To_Iterate_List);
                                                    //int first_athlete = eventList.get(First_Value_To_Iterate_List).athletes().get(o).id();
                                                    //int second_athlete = eventList.get(Second_value_To_Iterate_List).athletes().get(p).id();
                                                    //System.out.println("first athlete: " + first_athlete + " and second athlete: " + second_athlete);
                                                    conflicts++;
                                                    //System.out.println("This is conflicts: " + conflicts);
                                                }
                                            }
                                        }
                                    }
                    }
                }
                //System.out.println("This is conflicts: " + conflicts);
                //double double_conflicts = conflicts;
                //double_conflicts = Math.sqrt(double_conflicts);
                //conflicts = (int) double_conflicts;
                return conflicts;
            }
        }

         */



    }
}