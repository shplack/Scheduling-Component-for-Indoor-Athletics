package com.SCIA.Schedule;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Discipline.Disciplines;
import com.SCIA.Discipline.Stations;
import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.Event.EventMaker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleMaker {
    public static Schedule makeSchedule(List<CompetitionGroup> competitionGroups) {
        List<Event> eventList = new ArrayList<>();
        List<CompetitionGroup> incrementalDisciplines = new ArrayList<>();

        for (CompetitionGroup competitionGroup : competitionGroups) {
            competitionGroup.athleteRecordsList().sort(Comparator.comparingDouble(
                    ar -> ar.getDisciplineRecords().getBestRecord(competitionGroup.discipline())
            ));

            if (!competitionGroup.discipline().isIncremental()) {
                ArrayList<Event> events = EventMaker.makeEvents(competitionGroup);
                eventList.addAll(events);
            } else {
                incrementalDisciplines.add(competitionGroup);
            }
        }

        eventList.addAll(EventMaker.makeIncrementalEvents(incrementalDisciplines));





        eventList.addAll(EventMaker.awardsCeremony(competitionGroups));

        return new Schedule(eventList);
    }
}
