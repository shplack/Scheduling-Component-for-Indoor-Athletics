package com.SCIA.Competitions;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Athlete.GenderGroup;
import com.SCIA.Discipline.Discipline;

import java.util.ArrayList;

public record CompetitionGroup(Discipline discipline, AgeGroup age_group, GenderGroup gender,
                               ArrayList<AthleteRecord> athleteRecordsList) {

    /**
     * Competition group constructor
     *
     * @param discipline  Discipline
     * @param age_group   AgeGroup
     * @param genderGroup GenderGroup
     */
    public CompetitionGroup(Discipline discipline, AgeGroup age_group, GenderGroup genderGroup) {
        this(
                discipline,
                age_group,
                genderGroup,
                new ArrayList<>()
        );
    }

    /**
     * Add an athlete record to the competition group
     *
     * @param athleteRecord The athlete record to be added
     */
    public void addAthleteRecord(AthleteRecord athleteRecord) {
        this.athleteRecordsList().add(athleteRecord);
    }

    /**
     * Print the competition group in a nice format
     *
     * @return The formatted string
     */
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
