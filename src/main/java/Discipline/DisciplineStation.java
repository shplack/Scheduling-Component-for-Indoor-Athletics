package Discipline;

import java.util.ArrayList;
import java.util.List;

import static Discipline.Disciplines.Discipline;
import static Discipline.Stations.Station;

public class DisciplineStation {

    public static ArrayList<Discipline> getDisciplines(Station station) {

        return switch(station) {

            case RUNNING_TRACK -> new ArrayList<>(List.of(Discipline.SPRINT200M, Discipline.MIDDLE800M,
                    Discipline.MIDDLE1500M, Discipline.LONG3000M));

            case SPRINT_TRACK -> new ArrayList<>(List.of(Discipline.HURDLE60M, Discipline.SPRINT60M));

            case LONG_TRIPLE_I, LONG_TRIPLE_II -> new ArrayList<>(List.of(Discipline.LONG_JUMP));

            case HIGH_JUMP_I, HIGH_JUMP_II -> new ArrayList<>(List.of(Discipline.HIGH_JUMP));

            case POLE_VAULT -> new ArrayList<>(List.of(Discipline.POLE_VAULT));

            case SHOT_PUT_I, SHOT_PUT_II -> new ArrayList<>(List.of(Discipline.SHOT_PUT));

            default -> null;
        };
    }
}
