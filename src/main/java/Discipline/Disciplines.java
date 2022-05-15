package Discipline;

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

        public boolean isMeasuredInTime() {
            return switch (this) {
                case SPRINT60M, SPRINT200M, MIDDLE800M, MIDDLE1500M, LONG3000M, HURDLE60M -> true;
                default -> false;
            };
        }
    }
}
