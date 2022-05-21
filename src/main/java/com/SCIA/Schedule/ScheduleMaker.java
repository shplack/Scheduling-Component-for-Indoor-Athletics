package com.SCIA.Schedule;

import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.CompetitionGroupSorters;
import com.SCIA.Competitions.Trials;
import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.Event.EventMaker;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ScheduleMaker {
    public static Schedule makeSchedule(List<CompetitionGroup> competitionGroups) {
        List<Event> eventList = new LinkedList<>();

        List<CompetitionGroup> runningCompetitions = new LinkedList<>();
        List<CompetitionGroup> trialCompetitions = new LinkedList<>();


        competitionGroups.sort(new CompetitionGroupSorters.DisciplineAgeGroupGender());

        for (CompetitionGroup competitionGroup : competitionGroups) {
            competitionGroup.athleteRecordsList().sort(Comparator.comparingDouble(
                    ar -> ar.getDisciplineRecords().getBestRecord(competitionGroup.discipline())
            ));

            if (competitionGroup.discipline().isRunningDiscipline())
                runningCompetitions.add(competitionGroup);
            else if (competitionGroup.discipline().isTrialDiscipline())
                trialCompetitions.add(competitionGroup);
        }

        eventList.addAll(EventMaker.makeRunningEvents(runningCompetitions));
        eventList.addAll(EventMaker.makeTrialEvents(trialCompetitions, Trials.Trial.TRIAL));
        eventList.addAll(EventMaker.awardsCeremony(competitionGroups));

        return new Schedule(eventList);
    }
}
