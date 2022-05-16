package Schedule;

import Athlete.AthleteRecord;
import Competitions.CompetitionGroup;
import Schedule.Event.Event;
import Schedule.Event.EventMaker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Competitions.AgeGroups.AgeGroup;
import static Discipline.Disciplines.Discipline;

public class ScheduleMaker {
    public static Schedule makeSchedule(List<CompetitionGroup> competitionGroups) {
        List<Event> eventList = new ArrayList<>();

        for (CompetitionGroup competitionGroup : competitionGroups) {
            Discipline discipline = competitionGroup.discipline();
            AgeGroup age_group = competitionGroup.age_group();

            ArrayList<AthleteRecord> sortedAthleteRecords = new ArrayList<>(competitionGroup.athleteRecordsList());
            sortedAthleteRecords.sort(Comparator.comparing(ar -> ar.getDisciplineRecords().getBestRecord(discipline)));

            eventList.addAll(EventMaker.makeEvents(discipline, sortedAthleteRecords, age_group));

        }
        return new Schedule(eventList);
    }
}
