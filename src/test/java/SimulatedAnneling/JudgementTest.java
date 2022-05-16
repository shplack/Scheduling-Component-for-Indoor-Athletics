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

import static org.junit.jupiter.api.Assertions.*;

class JudgementTest {
    ArrayList<Event> eventList;
    int conflict = 0;

    @BeforeEach
    void setUp() {
        Event event = new Event(
                new int[]{1, 2, 3},
                new ArrayList<>(Arrays.asList(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
                Station.RUNNING_TRACK,
                Discipline.LONG3000M
        );

        Event event_2 = new Event(
                new int[]{4, 5, 6},
                new ArrayList<>(Arrays.asList(new Athlete(
                        2,
                        "ÖÖÖ",
                        "Victor",
                        "Jonsson",
                        Gender.MALE,
                        30
                ))),
                Station.LONG_TRIPLE_I,
                Discipline.TRIPLE_JUMP
        );

        Event event_3 = new Event(
                new int[]{2},
                new ArrayList<>(Arrays.asList(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
                Station.POLE_VAULT,
                Discipline.POLE_VAULT
        );
        eventList  = new ArrayList<>(List.of(event,event_2,event_3));
    }



    @Test
    void getTimeSlot() {
        /*
        for (Event event : eventList)
            for (int k = 0; k < event.time_slots().length; k++)
                if (event.time_slots()[k] == event.time_slots()[0])
                    System.out.println(event.time_slots()[k]);

         */
        //System.out.println(eventList.get(0).athletes().get(0).id());

        //loops through list and compares each timeslot of the event
        //with the timeslot of the other events and checks if it is the same athlete
        //in two timeslots

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
                                            int first_athlete = eventList.get(i).athletes().get(o).id();
                                            int second_athlete = eventList.get(j).athletes().get(p).id();
                                            System.out.println("first athlete: " + first_athlete + " and second athlete: " + second_athlete);
                                            conflict++;
                                        }
                                    }
                                }
                            }
            }
            }


        System.out.println(conflict);




    }






}