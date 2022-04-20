package Athlete;

import Discipline.Discipline;

import java.util.ArrayList;
import java.util.List;

public class Athlete {
    private final String club;
    private final String name;
    private final String surname;
    private final String sex;
    private final int age;
    private final List<Discipline> disciplines = new ArrayList<>();

    public Athlete(String club, String name, String surname, String sex, int age) {
        this.club = club;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.age = age;
    }

    public String getClub() {
        return club;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public void addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }
}
