package Schedule;

import Athlete.Athlete;
import Athlete.Gender;
import Discipline.Discipline;
import Discipline.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EventTest {

    Event event;
    Event event_two;

    @BeforeEach
    void setUp() {
        event = new Event(
                new int[] {1,2,3},
                new ArrayList<>(List.of(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                ))),
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