package Discipline;

import static Discipline.Disciplines.Discipline;

public class Stations {

    public enum Station {
        RUNNING_TRACK,
        SPRINT_TRACK,
        LONG_TRIPLE_I,
        LONG_TRIPLE_II,
        HIGH_JUMP_I,
        HIGH_JUMP_II,
        POLE_VAULT,
        SHOT_PUT_I,
        SHOT_PUT_II
    }

    public static int getAthleteLimit(Station station) {
        return switch(station) {
            case RUNNING_TRACK -> 6;
            case SPRINT_TRACK -> 8;
            default -> 1;
        };
    }

    public static int getAthleteLimit(Discipline discipline) {
        return switch(discipline) {
            case SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M -> 6;
            case SPRINT60M, HURDLE60M -> 8;
            default -> 1;
        };
    }

}
