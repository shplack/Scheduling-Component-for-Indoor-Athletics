package com.SCIA.Schedule;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.Gender;
import com.SCIA.Competitions.AgeGroups;
import com.SCIA.Schedule.Event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.SCIA.Competitions.Trials.Trial;
import static com.SCIA.Discipline.Disciplines.Discipline;
import static com.SCIA.Discipline.Stations.Station;

class EventTest {

    Event event;
    Event event_two;

    @BeforeEach
    void setUp() {
        event = new Event(
                new ArrayList<>(List.of(1, 2, 3)),
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
                Trial.TRIAL_I,
                AgeGroups.AgeGroup.EIGHT_AND_UNDER,
                Gender.FEMALE
        );


    }

    @Test
    void deepCopy() {
    }

    @Test
    void testEquals() {
    }
}