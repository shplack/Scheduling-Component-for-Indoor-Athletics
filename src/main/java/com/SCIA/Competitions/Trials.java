package com.SCIA.Competitions;

import com.SCIA.Discipline.Disciplines;
import com.SCIA.Discipline.Stations;
import com.SCIA.Discipline.Stations.Station;

import static com.SCIA.Competitions.Trials.Trial.Order.*;

public class Trials {

    public enum Trial {
        QUALIFYING,
        QUARTER_FINAL,
        SEMI_FINAL,
        FINAL,
        TRIAL,

        AWARD;

        public enum Order {
            HIGHER,
            EQUAL,
            LOWER
        }

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

    public static Trial[] runningTrials() {
        return new Trial[] {
                Trial.QUALIFYING,
                Trial.QUARTER_FINAL,
                Trial.SEMI_FINAL,
                Trial.FINAL
        };
    }


}
