package Competitions;

import static Discipline.Disciplines.Discipline;

public class Trials {

    public enum Trial {
        QUALIFYING,
        QUARTER_FINAL,
        SEMI_FINAL,
        FINAL,
        TRIAL_I,
        TRIAL_II,
        TRIAL_III,
        TRIAL_IV
    }

    public static int numberOfTrials(Discipline discipline) {
        return switch(discipline) {
            case LONG_JUMP, TRIPLE_JUMP, HIGH_JUMP, POLE_VAULT -> 2;
            default -> 4;
        };
    }

    public static Trial[] getTrials(Discipline discipline) {
        return switch(discipline) {
            case SPRINT60M, SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M, HURDLE60M ->
                new Trial[] { Trial.QUALIFYING, Trial.QUARTER_FINAL, Trial.SEMI_FINAL, Trial.FINAL };
            case LONG_JUMP, TRIPLE_JUMP, HIGH_JUMP, POLE_VAULT ->
                new Trial[] { Trial.TRIAL_I, Trial.TRIAL_II };
            case SHOT_PUT -> new Trial[] { Trial.TRIAL_I, Trial.TRIAL_II, Trial.TRIAL_III, Trial.TRIAL_IV };
        };
    }
}
