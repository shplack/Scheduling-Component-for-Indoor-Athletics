package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.GenderGroup;
import com.SCIA.Competitions.AgeGroup;
import com.SCIA.Competitions.Trial;
import com.SCIA.Discipline.Discipline;
import com.SCIA.Discipline.Station;
import com.SCIA.Schedule.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    Event event;
    Event event_two;
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
                        GenderGroup.MALE,
                        29
                ))),
                Station.HIGH_JUMP_I,
                Discipline.HIGH_JUMP,
                Trial.TRIAL,
                AgeGroup.EIGHT_AND_UNDER,
                GenderGroup.FEMALE
        );


    }

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
}