package Discipline;

import java.util.ArrayList;
import java.util.List;

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
        };
    }
    public static ArrayList<Station> getStation(Discipline discipline) {
        return switch(discipline) {

            case SPRINT60M,HURDLE60M -> new ArrayList<>(List.of(Station.SPRINT_TRACK));

            case SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M -> new ArrayList<>(List.of(Station.RUNNING_TRACK));

            case LONG_JUMP, TRIPLE_JUMP -> new ArrayList<>(List.of(Station.LONG_TRIPLE_I, Station.LONG_TRIPLE_II));

            case HIGH_JUMP -> new ArrayList<>(List.of(Station.HIGH_JUMP_I,Station.HIGH_JUMP_II));

            case POLE_VAULT -> new ArrayList<>(List.of(Station.POLE_VAULT));

            case SHOT_PUT -> new ArrayList<>(List.of(Station.SHOT_PUT_I, Station.SHOT_PUT_II));
        };

    }
}
