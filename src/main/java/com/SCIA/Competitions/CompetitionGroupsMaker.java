package com.SCIA.Competitions;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Athlete.GenderGroup;
import com.SCIA.Discipline.Discipline;

import java.util.ArrayList;
import java.util.List;


public class CompetitionGroupsMaker {

    /**
     * It takes a list of athlete records and returns a list of competition groups
     *
     * @param athleteRecords A list of AthleteRecords.
     * @return A list of competition groups.
     */
    public static ArrayList<CompetitionGroup> makeCompetitionGroups(List<AthleteRecord> athleteRecords) {
        ArrayList<CompetitionGroup> competitionGroups = new ArrayList<>();

        // Creating competition groups with respective age group
        for (Discipline discipline : Discipline.values()) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                if (ageGroup.isUnisex()) {
                    competitionGroups.add(new CompetitionGroup(discipline, ageGroup, GenderGroup.UNISEX));
                } else {
                    competitionGroups.add(new CompetitionGroup(discipline, ageGroup, GenderGroup.MALE));
                    competitionGroups.add(new CompetitionGroup(discipline, ageGroup, GenderGroup.FEMALE));
                }
            }
        }

        // Adding the athlete records to the competition groups.
        for (AthleteRecord athleteRecord : athleteRecords) {
            for (Discipline discipline : athleteRecord.getDisciplines()) {
                for (CompetitionGroup competitionGroup : competitionGroups) {
                    if (competitionGroup.discipline() != discipline)
                        continue;

                    if (competitionGroup.age_group() != AgeGroup.getAgeGroup(athleteRecord.getAthlete().age()))
                        continue;

                    if (competitionGroup.gender() == GenderGroup.UNISEX || competitionGroup.gender() == athleteRecord.getAthlete().gender())
                        competitionGroup.addAthleteRecord(athleteRecord);
                }
            }
        }

        // Removing the empty groups from the list of competition groups.
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
