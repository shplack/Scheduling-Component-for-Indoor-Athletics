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
        TRIAL_IV;

        public int getNumGroups() {
            return switch(this) {
                case QUARTER_FINAL -> 4;
                case SEMI_FINAL -> 2;
                case FINAL -> 1;
                default -> 0;
            };
        }
    }

    public static int numberOfTrials(Discipline discipline) {
        return switch(discipline) {
            case LONG_JUMP, TRIPLE_JUMP, HIGH_JUMP, POLE_VAULT -> 2;
            default -> 4;
        };
    }


}
