package com.SCIA.Competitions;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Athlete.Gender;
import com.SCIA.Competitions.AgeGroups.AgeGroup;
import com.SCIA.Discipline.Disciplines.Discipline;

import java.util.ArrayList;

public class CompetitionGroup {

    private final Discipline discipline;
    private final AgeGroup age_group;
    private final Gender gender;
    private final ArrayList<AthleteRecord> athleteRecordsList;

    public Discipline discipline() {
        return discipline;
    }

    public AgeGroup age_group() {
        return age_group;
    }

    public Gender gender() {
        return gender;
    }

    public ArrayList<AthleteRecord> athleteRecordsList() {
        return athleteRecordsList;
    }

    public CompetitionGroup(Discipline discipline, AgeGroup age_group, Gender gender) {
        this.discipline = discipline;
        this.age_group = age_group;
        this.gender = gender;
        this.athleteRecordsList = new ArrayList<>();
    }

    public void addAthleteRecord(AthleteRecord athleteRecord) {
        this.athleteRecordsList.add(athleteRecord);
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
