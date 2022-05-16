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
        for (Event event : eventList)
            for (int k = 0; k < event.time_slots().length; k++)
                System.out.println(event.time_slots()[k]);
        //loops through list and compares each timeslot of the event
        //with the timeslot of the other events and checks if it is the same athlete
        //in two timeslots
        /*
        for (int i = 0; i < eventList.size(); i++) {
            for (int j = 0; j < eventList.size(); j++) {
                for (int k = 0; k < eventList.get(i).time_slots().length; k++)
                    for (int l = 0; l < eventList.get(j).time_slots().length; l++)
                        eventList.get(i).time_slots(k).equals(eventList.get(j).time_slots(l));
            }
        }

         */
    }






}