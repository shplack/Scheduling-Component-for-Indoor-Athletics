package com.SCIA.Discipline;

import java.util.ArrayList;
import java.util.List;

import static com.SCIA.Discipline.Stations.Station;

public class DisciplineStation {

    public static ArrayList<Disciplines.Discipline> getDisciplines(Station station) {

        return switch(station) {

            case RUNNING_TRACK -> new ArrayList<>(List.of(Disciplines.Discipline.SPRINT200M, Disciplines.Discipline.MIDDLE800M,
                    Disciplines.Discipline.MIDDLE1500M, Disciplines.Discipline.LONG3000M));

            case SPRINT_TRACK -> new ArrayList<>(List.of(Disciplines.Discipline.HURDLE60M, Disciplines.Discipline.SPRINT60M));

            case LONG_TRIPLE_I, LONG_TRIPLE_II -> new ArrayList<>(List.of(Disciplines.Discipline.LONG_JUMP));

            case HIGH_JUMP_I, HIGH_JUMP_II -> new ArrayList<>(List.of(Disciplines.Discipline.HIGH_JUMP));

            case POLE_VAULT -> new ArrayList<>(List.of(Disciplines.Discipline.POLE_VAULT));

            case SHOT_PUT_I, SHOT_PUT_II -> new ArrayList<>(List.of(Disciplines.Discipline.SHOT_PUT));

            default -> null;
        };
    }
}
