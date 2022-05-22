package com.SCIA.Athlete;

import java.util.Map;
import java.util.Objects;

public class Athlete {

    private final int id;
    private final String club;
    private final String name;
    private final String surname;
    private final Gender gender;
    private final int age;

    public int id() {
        return id;
    }

    public String club() {
        return club;
    }

    public String name() {
        return name;
    }

    public String surname() {
        return surname;
    }

    public Gender gender() {
        return gender;
    }

    public int age() {
        return age;
    }

    public enum Property {
        CLUB,
        NAME,
        SURNAME,
        GENDER,
        AGE,
        ID
    }

    public Athlete(int id, String club, String name, String surname, Gender gender, int age) {
        Objects.requireNonNull(club);
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);
        Objects.requireNonNull(gender);
        this.id = id;
        this.club = club.trim();
        this.name = name.trim();
        this.surname = surname.trim();
        this.gender = gender;
        this.age = age;
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
