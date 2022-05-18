package com.SCIA.Competitions;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Athlete.Gender;
import com.SCIA.Discipline.Disciplines;

import java.util.ArrayList;
import java.util.List;

import static com.SCIA.Competitions.AgeGroups.AgeGroup;

public class CompetitionGroupsMaker {

    public static ArrayList<CompetitionGroup> makeCompetitionGroups(List<AthleteRecord> athleteRecords) {
        ArrayList<CompetitionGroup> competitionGroups = new ArrayList<>();

        for (Disciplines.Discipline discipline : Disciplines.Discipline.values()) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                if (ageGroup.isUnisex()) {
                    competitionGroups.add(new CompetitionGroup(discipline, ageGroup, Gender.UNISEX));
                } else {
                    competitionGroups.add(new CompetitionGroup(discipline, ageGroup, Gender.MALE));
                    competitionGroups.add(new CompetitionGroup(discipline, ageGroup, Gender.FEMALE));
                }
            }
        }

        for (AthleteRecord athleteRecord : athleteRecords) {
            for (Disciplines.Discipline discipline : athleteRecord.getDisciplines()) {
                for (CompetitionGroup competitionGroup : competitionGroups) {
                    if (competitionGroup.discipline() != discipline)
                        continue;

                    if (competitionGroup.age_group() != AgeGroups.getAgeGroup(athleteRecord.getAthlete().age()))
                        continue;

                    if (competitionGroup.gender() == Gender.UNISEX || competitionGroup.gender() == athleteRecord.getAthlete().gender())
                        competitionGroup.addAthleteRecord(athleteRecord);
                }
            }
        }

        List<CompetitionGroup> emptyGroups = new ArrayList<>();

        for (CompetitionGroup competitionGroup : competitionGroups) {
            if (competitionGroup.athleteRecordsList().isEmpty())
                emptyGroups.add(competitionGroup);
        }

        for (CompetitionGroup competitionGroup : emptyGroups) {
            competitionGroups.remove(competitionGroup);
        }

        return competitionGroups;
    }
}
