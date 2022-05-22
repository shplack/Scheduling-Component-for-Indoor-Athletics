package com.SCIA.Discipline;

import java.util.ArrayList;
import java.util.List;

import static com.SCIA.Discipline.Stations.Station;

public class DisciplineStation {

    public static ArrayList<Disciplines.Discipline> getDisciplines(Station station) {

        switch (station) {
            case RUNNING_TRACK:
                return new ArrayList<>(List.of(Disciplines.Discipline.SPRINT200M, Disciplines.Discipline.MIDDLE800M,
                        Disciplines.Discipline.MIDDLE1500M, Disciplines.Discipline.LONG3000M));
            case SPRINT_TRACK:
                return new ArrayList<>(List.of(Disciplines.Discipline.HURDLE60M, Disciplines.Discipline.SPRINT60M));
            case LONG_TRIPLE_I:
            case LONG_TRIPLE_II:
                return new ArrayList<>(List.of(Disciplines.Discipline.LONG_JUMP));
            case HIGH_JUMP_I:
            case HIGH_JUMP_II:
                return new ArrayList<>(List.of(Disciplines.Discipline.HIGH_JUMP));
            case POLE_VAULT:
                return new ArrayList<>(List.of(Disciplines.Discipline.POLE_VAULT));
            case SHOT_PUT_I:
            case SHOT_PUT_II:
                return new ArrayList<>(List.of(Disciplines.Discipline.SHOT_PUT));
            default:
                return null;
        }
    }
}
