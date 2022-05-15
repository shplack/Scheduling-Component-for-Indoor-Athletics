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
    Event event_two;

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


    }

    @Test
    void deepCopy() {
    }

    @Test
    void testEquals() {
    }
}