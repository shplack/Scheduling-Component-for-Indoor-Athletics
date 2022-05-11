package Athlete;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public record Athlete(int id, String club, String name, String surname, Gender gender, int age, boolean participating) {
    public enum Property {
        CLUB,
        NAME,
        SURNAME,
        GENDER,
        AGE,
        PARTICIPATING,
        ID
    }

    public Athlete {
        Objects.requireNonNull(club);
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);
        Objects.requireNonNull(gender);
    }

    public Athlete(@NotNull Map<Property, String> properties) {
        this(
                Integer.parseInt(properties.get(Property.ID)),
                properties.get(Property.CLUB),
                properties.get(Property.NAME),
                properties.get(Property.SURNAME),
                Gender.values()[Integer.parseInt(properties.get(Property.GENDER))],
                Integer.parseInt(properties.get(Property.AGE)),
                Boolean.parseBoolean(properties.get(Property.PARTICIPATING))
        );
    }
}
