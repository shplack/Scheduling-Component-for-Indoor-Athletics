package Competitions;

import Athlete.Athlete;
import Athlete.Gender;
import Discipline.Discipline;

import java.util.ArrayList;
import java.util.List;

import static Competitions.AgeGroups.AgeGroup;

public record CompetitionGroup(Discipline discipline, AgeGroup ageGroup, Gender gender, List<Athlete> athleteList) {

    public CompetitionGroup(Discipline discipline, AgeGroup ageGroup, Gender gender) {
        this(
                discipline,
                ageGroup,
                gender,
                new ArrayList<>()
        );
    }

    public void addAthlete(Athlete athlete) {
        this.athleteList().add(athlete);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(
                "Competition: " + discipline +
                "\n  ├─ Age group: " + ageGroup.toString() +
                "\n  ├─ Gender: " + gender.toString() +
                "\n  └─ Athletes: "
        );

        for (int i = 0; i < athleteList.size(); i++) {
            stringBuilder.append("\n      ")
                    .append(i < athleteList.size() - 1 ? "├" : "└")
                    .append("─ ")
                    .append(athleteList.get(i).toString());
        }

        return stringBuilder.toString();
    }
}
