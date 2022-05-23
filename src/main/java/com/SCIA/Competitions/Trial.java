package com.SCIA.Competitions;

import com.SCIA.Discipline.Station;


public enum Trial {
    QUALIFYING,
    QUARTER_FINAL,
    SEMI_FINAL,
    FINAL,
    TRIAL,
    AWARD;

    /**
     * Qualifying < Quarter < Semi < Final
     * All < Awards
     *
     * @param trial The trial that we are checking to see if it must happen before this trial.
     */
    public boolean mustHappenBefore(Trial trial) {
        if (this == trial)
            return false;

        if (this == AWARD)
            return this.ordinal() < trial.ordinal();

        if (this != FINAL && trial.ordinal() <= FINAL.ordinal())
            return this.ordinal() < trial.ordinal();

        return this.ordinal() < trial.ordinal();
    }

    /**
     * The number of groups to be planned for each trial.
     *
     * @return The number of groups in the round.
     */
    public int getNumGroups() {
        return switch (this) {
            case QUARTER_FINAL -> 4;
            case SEMI_FINAL -> 2;
            case FINAL -> 1;
            default -> 0;
        };
    }

    /**
     * Return an array of all the trials needed for the running discipline
     *
     * @return Running trials
     */
    public static Trial[] runningTrials() {
        return new Trial[]{
                Trial.QUALIFYING,
                Trial.QUARTER_FINAL,
                Trial.SEMI_FINAL,
                Trial.FINAL
        };
    }

    /**
     * The minimum number of required groups to be able to have a trial.
     *
     * @return The number of groups required to advance to the next round.
     */
    private int getMinimumRequiredNumGroups() {
        return switch (this) {
            case QUALIFYING -> 4;
            case QUARTER_FINAL -> 2;
            case SEMI_FINAL -> 1;
            default -> 0;
        };
    }

    /**
     * Check if there are enough athletes to be able to have an event
     *
     * @param station     The station that the athletes are being assigned to.
     * @param numAthletes the number of athletes that are currently in the station
     * @return A boolean value.
     */
    public boolean enoughAthletes(Station station, int numAthletes) {
        int minNumGroups = this.getMinimumRequiredNumGroups();
        if (minNumGroups == 0)
            return numAthletes > 0;
        return numAthletes > minNumGroups * station.capacity();
    }
}
