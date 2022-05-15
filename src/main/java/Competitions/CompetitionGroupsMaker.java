package Competitions;

import Athlete.AthleteRecord;
import Athlete.Gender;
import Discipline.Discipline;

import java.util.ArrayList;
import java.util.List;

import static Competitions.AgeGroups.AgeGroup;

public class CompetitionGroupsMaker {

    public static List<CompetitionGroup> makeCompetitionGroups(List<AthleteRecord> athleteRecords) {
        List<CompetitionGroup> competitionGroups = new ArrayList<>();

        for (Discipline discipline : Discipline.values()) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                if (AgeGroups.unisexGroup(ageGroup)) {
                    competitionGroups.add(new CompetitionGroup(discipline, ageGroup, Gender.UNISEX));
                } else {
                    competitionGroups.add(new CompetitionGroup(discipline, ageGroup, Gender.MALE));
                    competitionGroups.add(new CompetitionGroup(discipline, ageGroup, Gender.FEMALE));
                }
            }
        }

        for (AthleteRecord athleteRecord : athleteRecords) {
            for (Discipline discipline : athleteRecord.getDisciplines()) {
                for (CompetitionGroup competitionGroup : competitionGroups) {
                    if (competitionGroup.discipline() != discipline)
                        continue;

                    if (competitionGroup.ageGroup() != AgeGroups.getAgeGroup(athleteRecord.getAthlete().age()))
                        continue;

                    if (competitionGroup.gender() == Gender.UNISEX || competitionGroup.gender() == athleteRecord.getAthlete().gender())
                        competitionGroup.addAthlete(athleteRecord.getAthlete());
                }
            }
        }

        List<CompetitionGroup> emptyGroups = new ArrayList<>();

        for (CompetitionGroup competitionGroup : competitionGroups) {
            if (competitionGroup.athleteList().isEmpty())
                emptyGroups.add(competitionGroup);
        }

        for (CompetitionGroup competitionGroup : emptyGroups) {
            competitionGroups.remove(competitionGroup);
        }

        return competitionGroups;
    }
}
