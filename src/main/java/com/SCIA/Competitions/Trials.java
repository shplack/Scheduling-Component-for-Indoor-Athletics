package com.SCIA.Competitions;

import com.SCIA.Discipline.Stations.Station;

public class Trials {

    public enum Trial {
        QUALIFYING,
        QUARTER_FINAL,
        SEMI_FINAL,
        FINAL,
        TRIAL,
        AWARD;

        public boolean mustHappenBefore(Trial trial) {
            if (this == trial)
                return false;

            if (this == AWARD)
                return this.ordinal() < trial.ordinal();

            if (this != FINAL && trial.ordinal() <= FINAL.ordinal())
                return this.ordinal() < trial.ordinal();

            return this.ordinal() < trial.ordinal();
        }



        public int getNumGroups() {
            switch (this) {
                case QUARTER_FINAL:
                    return 4;
                case SEMI_FINAL:
                    return 2;
                case FINAL:
                    return 1;
                default:
                    return 0;
            }
        }

        private int getMinimumRequiredNumGroups() {
            switch (this) {
                case QUALIFYING:
                    return 4;
                case QUARTER_FINAL:
                    return 2;
                case SEMI_FINAL:
                    return 1;
                default:
                    return 0;
            }
        }

        public boolean canHazTrial(Station station, int numAthletes) {
            int minNumGroups = this.getMinimumRequiredNumGroups();
            if (minNumGroups == 0)
                return numAthletes > 0;
            return numAthletes > minNumGroups * station.getAthleteLimit();
        }
    }

    public static Trial[] runningTrials() {
        return new Trial[] {
                Trial.QUALIFYING,
                Trial.QUARTER_FINAL,
                Trial.SEMI_FINAL,
                Trial.FINAL
        };
    }


}
