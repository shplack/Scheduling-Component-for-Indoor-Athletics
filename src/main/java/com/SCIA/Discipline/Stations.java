package com.SCIA.Discipline;


public class Stations {

    public enum Station {
        RUNNING_TRACK,
        SPRINT_TRACK,
        LONG_TRIPLE_I,
        LONG_TRIPLE_II,
        HIGH_JUMP_I,
        HIGH_JUMP_II,
        POLE_VAULT,
        SHOT_PUT_I,
        SHOT_PUT_II,

        AWARDS_STAGE;

        public int getAthleteLimit() {
            return switch(this) {
                case RUNNING_TRACK -> 6;
                case SPRINT_TRACK -> 8;
                default -> 1;
            };
        }


    }

}
