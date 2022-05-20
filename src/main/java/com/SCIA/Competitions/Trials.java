package com.SCIA.Competitions;

import com.SCIA.Discipline.Disciplines;
import com.SCIA.Discipline.Stations;

import static com.SCIA.Competitions.Trials.Trial.Order.*;

public class Trials {

    public enum Trial {
        QUALIFYING,
        QUARTER_FINAL,
        SEMI_FINAL,
        FINAL,
        TRIAL_I,
        TRIAL_II,
        TRIAL_III,
        TRIAL_IV,
        TRIAL,

        AWARD;

        public enum Order {
            HIGHER,
            EQUAL,
            LOWER
        }

        private boolean between(Trial lower_ordinal, Trial higher_ordinal) {
            return this.ordinal() >= lower_ordinal.ordinal() && this.ordinal() <= higher_ordinal.ordinal();
        }

        public Order compareOrder(Trial trial) {
            if ((this.between(QUALIFYING, FINAL) && trial.between(QUALIFYING, FINAL)) ||
                    (this.between(TRIAL_I, TRIAL_IV) && trial.between(TRIAL_I, TRIAL_IV)) ||
                    (this == AWARD || trial == AWARD))
                return this.ordinal() > trial.ordinal() ?  HIGHER : this.ordinal() == trial.ordinal() ? EQUAL : LOWER;
            return EQUAL;
        }

        public int getNumGroups() {
            return switch(this) {
                case QUARTER_FINAL -> 4;
                case SEMI_FINAL -> 2;
                case FINAL -> 1;
                default -> 0;
            };
        }

        public boolean canHazTrial(Stations.Station station, int numAthletes) {
            return switch (this) {
                case QUARTER_FINAL -> numAthletes > station.getAthleteLimit() * this.getNumGroups();
                case SEMI_FINAL -> numAthletes > station.getAthleteLimit();
                default -> numAthletes > 0;
            };
        }
    }

    public static int numberOfTrials(Disciplines.Discipline discipline) {
        return switch(discipline) {
            case LONG_JUMP, TRIPLE_JUMP, HIGH_JUMP, POLE_VAULT -> 2;
            default -> 4;
        };
    }


}
