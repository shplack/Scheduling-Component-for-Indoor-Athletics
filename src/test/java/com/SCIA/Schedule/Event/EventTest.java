package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.Gender;
import com.SCIA.Competitions.AgeGroups;
import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

import static com.SCIA.Competitions.Trials.Trial;
import static com.SCIA.Discipline.Disciplines.Discipline;
import static com.SCIA.Discipline.Stations.Station;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    Event event;
    Event event2;
    TimeSlot timeSlot = new TimeSlot(1, 3);

    @BeforeEach
    void setUp() {
        event = new Event(
                timeSlot,
                new ArrayList<>(List.of(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
                Station.HIGH_JUMP_I,
                Discipline.HIGH_JUMP,
                Trial.TRIAL,
                AgeGroups.AgeGroup.EIGHT_AND_UNDER,
                Gender.FEMALE
        );

        event2 = new Event(
                new TimeSlot(2, 5),
                new ArrayList<>(List.of(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
                Station.HIGH_JUMP_I,
                Discipline.HIGH_JUMP,
                Trial.TRIAL,
                AgeGroups.AgeGroup.EIGHT_AND_UNDER,
                Gender.FEMALE
        );

    }


    @Test
    void test() {
        //event.timeSlot().setTimeSlot(132, 2);
        System.out.println(event.timeSlot());
        System.out.println(event.timeSlot().getDay());
        System.out.println();
        System.out.println(event.timeSlot().conflictsWith(event2.timeSlot().getTimeSlot(), event2.timeSlot().getDuration()));
    }

    /*

    @Test
    void testTimeSlot() {
        assertEquals(timeSlot, event.timeSlot());
    }



    @Test
    @Disabled
    void deepCopy() {
    }

    @Test
    @Disabled
    void testEquals() {
    }

     */
}