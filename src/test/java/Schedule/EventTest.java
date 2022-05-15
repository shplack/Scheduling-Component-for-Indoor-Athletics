package Schedule;

import Athlete.*;
import Discipline.Discipline;
import Discipline.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    Event event;
    Event event_2;
    Event event_three;
    @BeforeEach
    void setUp() {
        event = new Event(
                new int[] {1,2,3},
                (ArrayList<Athlete>) Arrays.asList(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                )),
                Station.HIGH_JUMP_I,
                Discipline.HIGH_JUMP
        );

        event_2 = new Event(
                new int[] {4,5,6},
                (ArrayList<Athlete>) Arrays.asList(new Athlete(
                        2,
                        "ÖÖÖ",
                        "Victor",
                        "Jonsson",
                        Gender.MALE,
                        30
                )),
                Station.LONG_TRIPLE_I,
                Discipline.TRIPLE_JUMP
        );

    }

    @Test
    void deepCopy() {
    }

    @Test
    void testEquals() {
    }
}