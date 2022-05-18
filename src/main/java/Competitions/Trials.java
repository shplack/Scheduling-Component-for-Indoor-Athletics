package Competitions;

import static Discipline.Disciplines.Discipline;
import static Discipline.Stations.Station;

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




        public boolean canHazTrial(Station station, int numAthletes) {
            return switch (this) {
                case QUARTER_FINAL -> numAthletes > station.getAthleteLimit() * this.getNumGroups();
                case SEMI_FINAL -> numAthletes > station.getAthleteLimit();
                default -> numAthletes > 0;
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
