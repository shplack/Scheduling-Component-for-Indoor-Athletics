package Competitions;

import Athlete.AthleteRecord;
import Athlete.Gender;

import java.util.ArrayList;
import java.util.List;

import static Competitions.AgeGroups.AgeGroup;
import static Discipline.Disciplines.Discipline;

public record CompetitionGroup(Discipline discipline, AgeGroup ageGroup, Gender gender, List<AthleteRecord> athleteRecordsList) {

    public CompetitionGroup(Discipline discipline, AgeGroup ageGroup, Gender gender) {
        this(
                discipline,
                ageGroup,
                gender,
                new ArrayList<>()
        );
    }

    public void addAthleteRecord(AthleteRecord athleteRecord) {
        this.athleteRecordsList().add(athleteRecord);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(
                "Competition: " + discipline +
                "\n  ├─ Age group: " + ageGroup.toString() +
                "\n  ├─ Gender: " + gender.toString() +
                "\n  └─ Athletes: "
        );

        for (int i = 0; i < athleteRecordsList.size(); i++) {
            stringBuilder.append("\n      ")
                    .append(i < athleteRecordsList.size() - 1 ? "├" : "└")
                    .append("─ ")
                    .append(athleteRecordsList.get(i).toString());
        }

        return stringBuilder.toString();
    }
}