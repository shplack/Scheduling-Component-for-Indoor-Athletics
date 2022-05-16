package Discipline;

import static Competitions.Trials.Trial;
import static Discipline.Stations.Station;

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
            return switch(this) {
                case SPRINT60M, SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M, HURDLE60M ->
                    new Trial[] { Trial.QUALIFYING, Trial.QUARTER_FINAL, Trial.SEMI_FINAL, Trial.FINAL };
                case LONG_JUMP, TRIPLE_JUMP, HIGH_JUMP, POLE_VAULT ->
                    new Trial[] { Trial.TRIAL_I, Trial.TRIAL_II };
                case SHOT_PUT -> new Trial[] { Trial.TRIAL_I, Trial.TRIAL_II, Trial.TRIAL_III, Trial.TRIAL_IV };
            };
        }

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

        public boolean isMeasuredInTime() {
            return switch (this) {
                case SPRINT60M, SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M, HURDLE60M -> true;
                default -> false;
            };
        }
    }
}
