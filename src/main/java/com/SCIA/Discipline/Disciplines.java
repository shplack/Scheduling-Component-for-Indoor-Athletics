package com.SCIA.Discipline;

import static com.SCIA.Competitions.Trials.Trial;
import static com.SCIA.Discipline.Stations.Station;

public class Disciplines {
    public enum Discipline {
        SPRINT60M,
        SPRINT200M,
        MIDDLE800M,
        MIDDLE1500M,
        LONG3000M,
        HURDLE60M,
        LONG_JUMP,
        TRIPLE_JUMP,
        HIGH_JUMP,
        POLE_VAULT,
        SHOT_PUT;

        public Trial[] getTrials() {
            switch (this) {
                case SPRINT60M:
                case SPRINT200M:
                case MIDDLE800M:
                case MIDDLE1500M:
                case LONG3000M:
                case HURDLE60M:
                    return new Trial[]{Trial.QUALIFYING, Trial.QUARTER_FINAL, Trial.SEMI_FINAL, Trial.FINAL};
                default:
                    return new Trial[]{Trial.TRIAL};
            }
        }

        public Station[] getStations() {
            switch (this) {
                case SPRINT60M:
                case HURDLE60M:
                    return new Station[]{Station.SPRINT_TRACK};
                case SPRINT200M:
                case MIDDLE800M:
                case MIDDLE1500M:
                case LONG3000M:
                    return new Station[]{Station.RUNNING_TRACK};
                case LONG_JUMP:
                case TRIPLE_JUMP:
                    return new Station[]{Station.LONG_TRIPLE_I, Station.LONG_TRIPLE_II};
                case HIGH_JUMP:
                    return new Station[]{Station.HIGH_JUMP_I, Station.HIGH_JUMP_II};
                case POLE_VAULT:
                    return new Station[]{Station.POLE_VAULT};
                case SHOT_PUT:
                    return new Station[]{Station.SHOT_PUT_I, Station.SHOT_PUT_II};
                default:
                    throw new IllegalArgumentException();
            }

        }

        public boolean isMeasuredInTime() {
            switch (this) {
                case SPRINT60M:
                case SPRINT200M:
                case MIDDLE800M:
                case MIDDLE1500M:
                case LONG3000M:
                case HURDLE60M:
                    return true;
                default:
                    return false;
            }
        }

        public boolean isRunningDiscipline() {
            switch (this) {
                case SPRINT60M:
                case SPRINT200M:
                case MIDDLE800M:
                case MIDDLE1500M:
                case LONG3000M:
                case HURDLE60M:
                    return true;
                default:
                    return false;
            }
        }

        public boolean isTrialDiscipline() {
            switch (this) {
                case LONG_JUMP:
                case TRIPLE_JUMP:
                case HIGH_JUMP:
                case POLE_VAULT:
                case SHOT_PUT:
                    return true;
                default:
                    return false;
            }
        }

        public int duration() { // in minutes
            switch (this) {
                case LONG_JUMP:
                case TRIPLE_JUMP:
                    return 2;
                case HIGH_JUMP:
                case POLE_VAULT:
                    return 3;
                default:
                    return 1;
            }
        }

    }
}
