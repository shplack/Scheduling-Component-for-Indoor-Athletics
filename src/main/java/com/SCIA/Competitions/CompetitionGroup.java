package com.SCIA.Competitions;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Athlete.Gender;
import com.SCIA.Discipline.Disciplines;

import java.util.ArrayList;

public record CompetitionGroup(Disciplines.Discipline discipline, AgeGroups.AgeGroup age_group, Gender gender, ArrayList<AthleteRecord> athleteRecordsList) {

    public CompetitionGroup(Disciplines.Discipline discipline, AgeGroups.AgeGroup age_group, Gender gender) {
        this(
                discipline,
                age_group,
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
                "\n  ├─ Age group: " + age_group.toString() +
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
