package com.SCIA.Discipline;

import com.SCIA.Competitions.Trial;

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

    /**
     * Returns an array of trials for running disciplines or an array with a single trial for trial disciplines.
     *
     * @return An array of Trial objects.
     */
    public Trial[] getTrials() {
        return switch(this) {
            case SPRINT60M, SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M, HURDLE60M ->
                new Trial[] { Trial.QUALIFYING, Trial.QUARTER_FINAL, Trial.SEMI_FINAL, Trial.FINAL };
            default -> new Trial[] { Trial.TRIAL };
        };
    }

    /**
     * Get the stations that the given discipline can be booked at
     *
     * @return An array of stations
     */
    public Station[] getStations() {
        return switch(this) {
            case SPRINT60M,HURDLE60M -> new Station[]{Station.SPRINT_TRACK};
            case SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M -> new Station[]{Station.RUNNING_TRACK};
            case LONG_JUMP, TRIPLE_JUMP -> new Station[]{Station.LONG_TRIPLE_I, Station.LONG_TRIPLE_II};
            case HIGH_JUMP -> new Station[]{Station.HIGH_JUMP_I, Station.HIGH_JUMP_II};
            case POLE_VAULT -> new Station[]{Station.POLE_VAULT};
            case SHOT_PUT -> new Station[]{Station.SHOT_PUT_I, Station.SHOT_PUT_II};
        };

    }

    /**
     * Check if the discipline is measured in time
     *
     * @return True if the discipline is measured in time
     */
    public boolean isMeasuredInTime() {
        return switch (this) {
            case SPRINT60M, SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M, HURDLE60M -> true;
            default -> false;
        };
    }

    /**
     * Check if the discipline is measured in distance
     *
     * @return True if the discipline is measured in distance.
     */
    public boolean isMeasuredInDistance() {
        return switch (this) {
            case LONG_JUMP, TRIPLE_JUMP, HIGH_JUMP, POLE_VAULT, SHOT_PUT -> true;
            default -> false;
        };
    }

    /**
     * Check if the discipline is of a running type
     *
     * @return True if the discipline is of a running type
     */
    public boolean isRunningDiscipline() {
        return switch (this) {
            case SPRINT60M, SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M, HURDLE60M -> true;
            default -> false;
        };
    }

    /**
     * Check if the discipline is of a trial type
     *
     * @return True if the discipline is of a trial type
     */
    public boolean isTrialDiscipline() {
        return switch (this) {
            case LONG_JUMP, TRIPLE_JUMP, HIGH_JUMP, POLE_VAULT, SHOT_PUT -> true;
            default -> false;
        };
    }

    /**
     * Get the time needed for each discipline in minutes
     *
     * @return The time needed in minutes
     */
    public int duration() { // in minutes
        return switch(this) {
            case LONG_JUMP, TRIPLE_JUMP -> 2;
            case HIGH_JUMP, POLE_VAULT -> 3;
            default -> 1;
        };
    }

}
