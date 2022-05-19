package com.SCIA.Schedule;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.Event.EventMaker;

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

        eventList.addAll(EventMaker.awardsCeremony(competitionGroups));

        return new Schedule(eventList);
    }
}
