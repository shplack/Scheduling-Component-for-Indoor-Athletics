package SimulatedAnneling;

import Athlete.*;
import Discipline.Discipline;
import Discipline.Station;
import Schedule.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                new int[]{1},
                new ArrayList<>(Arrays.asList(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
                Station.LONG_TRIPLE_I,
                Discipline.LONG_JUMP
        );

        Event event_2 = new Event(
                new int[]{4},
                new ArrayList<>(Arrays.asList(new Athlete(
                        2,
                        "ÖÖÖ",
                        "Victor",
                        "Jonsson",
                        Gender.MALE,
                        30
                ))),
                Station.LONG_TRIPLE_II,
                Discipline.TRIPLE_JUMP
        );

        Event event_3 = new Event(
                new int[]{1},
                new ArrayList<>(Arrays.asList(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
                Station.LONG_TRIPLE_II,
                Discipline.TRIPLE_JUMP
        );


        Event event_4 = new Event(
                new int[]{4},
                new ArrayList<>(Arrays.asList(new Athlete(
                        2,
                        "ÖÖÖ",
                        "Victor",
                        "Jonsson",
                        Gender.MALE,
                        30
                ))),
                Station.POLE_VAULT,
                Discipline.POLE_VAULT
        );

        Event event_5 = new Event(
                new int[]{3},
                new ArrayList<>(Arrays.asList(new Athlete(
                        2,
                        "ÖÖÖ",
                        "Victoria",
                        "Jonsdottir",
                        Gender.MALE,
                        30
                ))),
                Station.POLE_VAULT,
                Discipline.POLE_VAULT
        );

        eventList = new ArrayList<>(List.of(event, event_2, event_3, event_4, event_5));

    }

    @Test
    void mutationFunction() {
        Random rand = new Random();

        int rand_int1 = rand.nextInt(eventList.size());
        int rand_int2 = rand.nextInt(eventList.size());




        for (int i = 0; i < eventList.size(); i++) {
            System.out.println(eventList.get(i).time_slots()[0]);
        }

        //System.out.println(eventList.get(0).time_slots()[0]);

        int timeslot1 = eventList.get(rand_int1).time_slots()[0];
        int timeslot2 = eventList.get(rand_int2).time_slots()[0];

        if (eventList.get(rand_int1).discipline() == eventList.get(rand_int2).discipline()) {
            eventList.get(rand_int1).time_slots()[0] = timeslot2;
            eventList.get(rand_int2).time_slots()[0] = timeslot1;
        }

        System.out.println("-----------------------");

        for (int i = 0; i < eventList.size(); i++) {
            System.out.println(eventList.get(i).time_slots()[0]);
        }
        /*
        Collections.shuffle(eventList);
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println(eventList.get(i).athletes().toString());
        }

         */
    }

    @Test
    void evaluate() {
        for (int i = 0; i < eventList.size(); i++) {
            for (int j = 0; j < eventList.size(); j++) {
                for (int k = 0; k < eventList.get(i).time_slots().length; k++)
                    for (int l = 0; l < eventList.get(j).time_slots().length; l++)
                        if (eventList.get(i).time_slots()[k] == (eventList.get(j).time_slots()[l]))
                            for (int o = 0; o < eventList.get(i).athletes().size(); o++) {
                                for (int p = 0; p < eventList.get(i).athletes().size(); p++) {
                                    if (eventList.get(i) != eventList.get(j)) {
                                        if ((eventList.get(i).athletes().get(o).id()) == eventList.get(j).athletes().get(p).id())
                                        {
                                            //int first_athlete = eventList.get(i).athletes().get(o).id();
                                            //int second_athlete = eventList.get(j).athletes().get(p).id();
                                            //System.out.println("first athlete: " + first_athlete + " and second athlete: " + second_athlete);
                                            conflict++;
                                        }
                                    }
                                }
                            }
            }
        }
        System.out.println("This is conflict amount: " + conflict);
    }
}
