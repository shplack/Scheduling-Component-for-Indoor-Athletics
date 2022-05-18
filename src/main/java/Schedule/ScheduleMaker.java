package Schedule;

import Athlete.AthleteRecord;
import Competitions.CompetitionGroup;
import Schedule.Event.Event;
import Schedule.Event.EventMaker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScheduleMaker {
    public static Schedule makeSchedule(List<CompetitionGroup> competitionGroups) {
        List<Event> eventList = new ArrayList<>();

        for (CompetitionGroup competitionGroup : competitionGroups) {
            ArrayList<AthleteRecord> sortedAthleteRecords = new ArrayList<>(competitionGroup.athleteRecordsList());

            sortedAthleteRecords.sort(Comparator.comparingDouble(
                    ar -> ar.getDisciplineRecords().getBestRecord(competitionGroup.discipline())
            ));

            ArrayList<Event> events = EventMaker.makeEvents(competitionGroup);
            eventList.addAll(events);

        }
        return new Schedule(eventList);
    }
}
