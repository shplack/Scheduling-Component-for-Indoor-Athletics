package Athlete;

import java.util.Map;
import java.util.Objects;

public record Athlete(int id, String club, String name, String surname, Gender gender, int age) {
    public enum Property {
        CLUB,
        NAME,
        SURNAME,
        GENDER,
        AGE,
        ID
    }

    public Athlete {
        Objects.requireNonNull(club);
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);
        Objects.requireNonNull(gender);

        club = club.trim();
        name = name.trim();
        surname = surname.trim();
    }

    public Athlete(Map<Property, String> properties) {
        this(
                Integer.parseInt(properties.get(Property.ID)),
                properties.get(Property.CLUB),
                properties.get(Property.NAME),
                properties.get(Property.SURNAME),
                properties.get(Property.GENDER).equals("M") ? Gender.MALE : Gender.FEMALE,
                Integer.parseInt(properties.get(Property.AGE))
        );
    }

    public Athlete deepCopy() {
        return new Athlete(id, club, name, surname, gender, age);
    }

    public String toString() {
        return "ID: " + id +
               ", Club: " + club +
               ", Name: " + name + " " + surname +
               ", Gender: " + gender.toString() +
               ", Age: " + age;
    }
}
