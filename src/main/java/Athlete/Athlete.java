package Athlete;

import java.util.Objects;

public record Athlete(String club, String name, String surname, Gender gender, int age, boolean participating) {
    public Athlete {
        Objects.requireNonNull(club);
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);
        Objects.requireNonNull(gender);
    }
}
