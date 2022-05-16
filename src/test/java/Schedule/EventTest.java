package Schedule;

import Athlete.Athlete;
import Athlete.Gender;
import Competitions.AgeGroups;
import Schedule.Event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static Competitions.Trials.Trial;
import static Discipline.Disciplines.Discipline;
import static Discipline.Stations.Station;

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
                Discipline.HIGH_JUMP,
                Trial.TRIAL_I,
                AgeGroups.AgeGroup.EIGHT_AND_UNDER
        );


    }

    @Test
    void deepCopy() {
    }

    @Test
    void testEquals() {
    }
}