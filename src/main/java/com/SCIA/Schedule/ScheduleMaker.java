package com.SCIA.Schedule;

import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.CompetitionGroupSorters;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Competitions.Trial;
import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.Event.EventMaker;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ScheduleMaker {
    /**
     * > It takes a list of competition groups, sorts them by discipline, age group, and gender, then creates a schedule of
     * events for the competition
     *
     * @param competitionGroups a list of CompetitionGroup objects.
     * @return A schedule of events
     */
    public static Schedule makeSchedule(List<CompetitionGroup> competitionGroups) {
        List<Event> eventList = new LinkedList<>();

        List<CompetitionGroup> runningCompetitions = new LinkedList<>();
        List<CompetitionGroup> trialCompetitions = new LinkedList<>();

        competitionGroups.sort(new CompetitionGroupSorters.DisciplineAgeGroupGender());

        for (CompetitionGroup competitionGroup : competitionGroups) {
            // Sorting the athlete records by their best record in the discipline of the competition group.
            competitionGroup.athleteRecordsList().sort(Comparator.comparingDouble(
                    ar -> ar.getDisciplineRecords().getBestRecord(competitionGroup.discipline())
            ));

            // Sorting the competition groups into two lists, one for running competitions and one for trial competitions.
            if (competitionGroup.discipline().isRunningDiscipline())
                runningCompetitions.add(competitionGroup);
            else if (competitionGroup.discipline().isTrialDiscipline())
                trialCompetitions.add(competitionGroup);
        }

        // Adding all the events to the eventList.
        eventList.addAll(EventMaker.makeRunningEvents(runningCompetitions));
        eventList.addAll(EventMaker.makeTrialEvents(trialCompetitions, Trial.TRIAL));
        eventList.addAll(EventMaker.awardsCeremony(competitionGroups));

        return new Schedule(eventList);
    }

    /**
     * > This function takes a CSV with athlete records and returns a generated schedule
     *
     * @param csv a CSV object that contains the data from the CSV file
     * @return A Schedule object
     */
    public static Schedule makeSchedule(CSV csv) {
        return makeSchedule(CompetitionGroupsMaker.makeCompetitionGroups(csv.getRecords()));
    }
}
