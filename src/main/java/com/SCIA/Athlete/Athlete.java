package com.SCIA.Athlete;

import java.util.Map;
import java.util.Objects;

public record Athlete(int id, String club, String name, String surname, GenderGroup gender, int age) {
    // An enum that is used to map the properties of the athlete to the values in the map.
    public enum Property {
        CLUB,
        NAME,
        SURNAME,
        GENDER,
        AGE,
        ID
    }

    /**
     * Athlete constructor
     *
     * @param id      ID
     * @param club    The club the athlete belongs to
     * @param name    The athlete's first name
     * @param surname The athlete's last name
     * @param gender  The gender of the athlete
     * @param age     The age of the athlete
     */
    public Athlete {
        Objects.requireNonNull(club);
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);
        Objects.requireNonNull(gender);

        club = club.trim();
        name = name.trim();
        surname = surname.trim();
    }

    /**
     * Create an athlete with a properties map
     *
     * @param properties The properties map
     */
    public Athlete(Map<Property, String> properties) {
        this(
                Integer.parseInt(properties.get(Property.ID)),
                properties.get(Property.CLUB),
                properties.get(Property.NAME),
                properties.get(Property.SURNAME),
                properties.get(Property.GENDER).equals("M") ? GenderGroup.MALE : GenderGroup.FEMALE,
                Integer.parseInt(properties.get(Property.AGE))
        );
    }

    /**
     * Get a deep copy of an athlete
     *
     * @return The deep copy
     */
    public Athlete deepCopy() {
        return new Athlete(id, club, name, surname, gender, age);
    }

    /**
     * Returns a nicely formatted string with the athlete's information
     *
     * @return The formatted String
     */
    public String toString() {
        return "ID: " + id +
                ", Club: " + club +
                ", Name: " + name + " " + surname +
                ", Gender: " + gender.toString() +
                ", Age: " + age;
    }
}
