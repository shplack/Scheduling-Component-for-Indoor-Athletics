package SimulatedAnneling;

import Athlete.*;
import Discipline.Discipline;
import Discipline.Station;
import Schedule.Event;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JudgementTest {

    Event event;
    Event event_2;
    Event event_3;
    @BeforeEach
    void setUp() {
        event = new Event(
                new int[]{1, 2, 3},
                (ArrayList<Athlete>) Arrays.asList(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                )),
                Station.RUNNING_TRACK,
                Discipline.LONG3000M
        );

        event_2 = new Event(
                new int[]{4, 5, 6},
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

        event_3 = new Event(
                new int[]{2},
                (ArrayList<Athlete>) Arrays.asList(new Athlete(
                        1,
                        "ÖSK",
                        "Alexander",
                        "Hedberg",
                        Gender.MALE,
                        29
                )),
                Station.POLE_VAULT,
                Discipline.POLE_VAULT
        );

        ArrayList<Event> eventLista  = new ArrayList<Event>(List.of(event,event_2,event_3));
    }

}