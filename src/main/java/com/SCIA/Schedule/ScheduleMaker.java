package com.SCIA.Schedule;

import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.Event.EventMaker;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ScheduleMaker {
    public static Schedule makeSchedule(List<CompetitionGroup> competitionGroups) {
        List<Event> eventList = new LinkedList<>();
        List<CompetitionGroup> incrementalDisciplines = new LinkedList<>();

        for (CompetitionGroup competitionGroup : competitionGroups) {
            competitionGroup.athleteRecordsList().sort(Comparator.comparingDouble(
                    ar -> ar.getDisciplineRecords().getBestRecord(competitionGroup.discipline())
            ));

            if (!competitionGroup.discipline().isTrialDiscipline())
                eventList.addAll(EventMaker.makeEvents(competitionGroup));
            else
                incrementalDisciplines.add(competitionGroup);
        }

        eventList.addAll(EventMaker.makeIncrementalEvents(incrementalDisciplines));
        eventList.addAll(EventMaker.awardsCeremony(competitionGroups));

        return new Schedule(eventList);
    }
}
