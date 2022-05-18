package Schedule.Event;

import Athlete.Athlete;
import Athlete.AthleteRecord;
import Competitions.CompetitionGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Competitions.AgeGroups.AgeGroup;
import static Competitions.Trials.Trial;
import static Discipline.Disciplines.Discipline;
import static Discipline.Stations.Station;

public class EventMaker {

    private static ArrayList<Event> makeXFinalsEvent(CompetitionGroup competitionGroup, Trial trial, Station station) {
        ArrayList<Athlete> athletes = new ArrayList<>(competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList());
        Discipline discipline = competitionGroup.discipline();
        AgeGroup ageGroup = competitionGroup.age_group();

        int num_groups = trial.getNumGroups();
        if (num_groups <= 0)
            num_groups = 1;

        return new ArrayList<>(Collections.nCopies(num_groups, new Event(null, athletes, station, discipline, trial, ageGroup, competitionGroup.gender())));
    }

    private static ArrayList<Event> makeQualifyingEvent(CompetitionGroup competitionGroup, Trial trial, Station station) {
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<AthleteRecord> athleteRecords = competitionGroup.athleteRecordsList();
        Discipline discipline = competitionGroup.discipline();
        AgeGroup ageGroup = competitionGroup.age_group();

        int num_groups = (int) Math.ceil((double) athleteRecords.size() / station.getAthleteLimit());

        ArrayList<ArrayList<Athlete>> athleteGroups = new ArrayList<>(num_groups);
        for (int i = 0; i < num_groups; i++)
            athleteGroups.add(new ArrayList<>());

        for (int i = 0, groupNum = 0; i < athleteRecords.size(); i++) {
            if (athleteGroups.get(groupNum).size() >= station.getAthleteLimit())
                groupNum++;
            athleteGroups.get(groupNum).add(athleteRecords.get(i).getAthlete());
        }

        athleteGroups.forEach(group -> events.add(new Event(null, group, station, discipline, trial, ageGroup, competitionGroup.gender())));

        return events;
    }

    private static ArrayList<Event> makeIncrementalEvent(CompetitionGroup competitionGroup, Trial trial, Station station) {
        ArrayList<Event> events = new ArrayList<>();

        if (trial == Trial.TRIAL_I) {
            competitionGroup.athleteRecordsList().stream().map((ar) -> ar.getAthlete()).forEach((athlete) ->
                    events.add(new Event(
                            null, new ArrayList<>(List.of(athlete)), station, competitionGroup.discipline(),
                            trial, competitionGroup.age_group(), competitionGroup.gender()
                    ))
            );
        }

        return events;
    }

    private static ArrayList<Event> makeNonIncrementalEvents(CompetitionGroup competitionGroup, Trial trial, Station station) {
        ArrayList<Event> events = new ArrayList<>();

        competitionGroup.athleteRecordsList().stream().map((ar) -> ar.getAthlete()).forEach( (athlete) ->
                events.add(new Event(
                        null, new ArrayList<>(List.of(athlete)), station, competitionGroup.discipline(),
                        trial, competitionGroup.age_group(), competitionGroup.gender()
                ))
        );

        return events;
    }

    private static ArrayList<Event> makeTrialEvent(CompetitionGroup competitionGroup, Trial trial, Station station ) {
        Discipline discipline = competitionGroup.discipline();
        if (discipline.isIncremental())
            return makeIncrementalEvent(competitionGroup, trial, station);

        return makeNonIncrementalEvents(competitionGroup, trial, station);
    }

    public static ArrayList<Event> makeEvents(CompetitionGroup competitionGroup) {
        ArrayList<Event> events = new ArrayList<>();

        Trial[] trials = competitionGroup.discipline().getTrials();
        Station[] stations = competitionGroup.discipline().getStations();
        for (int i = 0; i < trials.length; i++) {
            Station station = stations[i % stations.length]; // make sure to not only use one station that is available
            Trial trial = trials[i];
            if (!trial.canHazTrial(station, competitionGroup.athleteRecordsList().size()))
                continue;

            events.addAll(switch(trial){
                case QUALIFYING -> makeQualifyingEvent(competitionGroup, trial, station);
                case QUARTER_FINAL, SEMI_FINAL, FINAL -> makeXFinalsEvent(competitionGroup, trial, station);
                default -> makeTrialEvent(competitionGroup, trial, station);
            });
        }

        return events;
    }

    public static ArrayList<Event> awardsCeremony (List<CompetitionGroup> competitionGroups) {
        ArrayList<Event> awardsEvents = new ArrayList<>(competitionGroups.size());

        competitionGroups.forEach(competitionGroup ->
            awardsEvents.add(new Event(
                new ArrayList<>(),
                new ArrayList<>(competitionGroup.athleteRecordsList().stream().map(ar -> ar.getAthlete()).toList()),
                Station.AWARDS_STAGE,
                competitionGroup.discipline(),
                Trial.AWARD,
                competitionGroup.age_group(),
                competitionGroup.gender()
            ))
        );

        return awardsEvents;
    }
}
