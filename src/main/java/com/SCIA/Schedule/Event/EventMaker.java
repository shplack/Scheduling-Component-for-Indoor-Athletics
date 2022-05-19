package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.AgeGroups;
import com.SCIA.Competitions.Trials;
import com.SCIA.Discipline.Disciplines;
import com.SCIA.Discipline.Stations;
import com.SCIA.Discipline.Stations.Station;

import java.util.*;

public class EventMaker {

    private static final Map<Station, List<Event>> station_events;

    static {
        station_events = new HashMap<>(Station.values().length);
        Arrays.stream(Station.values()).forEach(station -> station_events.put(station, new ArrayList<>()));
    }

    /*
    private void assignEventToStation(Event event) {

    }
    */

    private static ArrayList<Event> makeXFinalsEvent(CompetitionGroup competitionGroup, Trials.Trial trial, Station station) {
        ArrayList<Athlete> athletes = new ArrayList<>(competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList());
        Disciplines.Discipline discipline = competitionGroup.discipline();
        AgeGroups.AgeGroup ageGroup = competitionGroup.age_group();

        int num_groups = trial.getNumGroups();
        if (num_groups <= 0)
            num_groups = 1;

        return new ArrayList<>(Collections.nCopies(num_groups, new Event(null, athletes, station, discipline, trial, ageGroup, competitionGroup.gender())));
    }

    private static ArrayList<Event> makeQualifyingEvent(CompetitionGroup competitionGroup, Trials.Trial trial, Station station) {
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<AthleteRecord> athleteRecords = competitionGroup.athleteRecordsList();
        Disciplines.Discipline discipline = competitionGroup.discipline();
        AgeGroups.AgeGroup ageGroup = competitionGroup.age_group();

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

    private static ArrayList<Event> makeIncrementalEvent(CompetitionGroup competitionGroup, Trials.Trial trial, Station station) {
        ArrayList<Event> events = new ArrayList<>();

        if (trial == Trials.Trial.TRIAL_I) {
            competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).forEach((athlete) ->
                    events.add(new Event(
                            null, new ArrayList<>(List.of(athlete)), station, competitionGroup.discipline(),
                            trial, competitionGroup.age_group(), competitionGroup.gender()
                    ))
            );
        }

        return events;
    }

    private static ArrayList<Event> makeNonIncrementalEvents(CompetitionGroup competitionGroup, Trials.Trial trial, Station station) {
        ArrayList<Event> events = new ArrayList<>();

        competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).forEach( (athlete) ->
                events.add(new Event(
                        null, new ArrayList<>(List.of(athlete)), station, competitionGroup.discipline(),
                        trial, competitionGroup.age_group(), competitionGroup.gender()
                ))
        );

        return events;
    }

    private static ArrayList<Event> makeTrialEvent(CompetitionGroup competitionGroup, Trials.Trial trial, Station station ) {
        Disciplines.Discipline discipline = competitionGroup.discipline();
        if (discipline.isIncremental())
            return makeIncrementalEvent(competitionGroup, trial, station);

        return makeNonIncrementalEvents(competitionGroup, trial, station);
    }

    public static ArrayList<Event> makeEvents(CompetitionGroup competitionGroup) {
        ArrayList<Event> events = new ArrayList<>();

        Trials.Trial[] trials = competitionGroup.discipline().getTrials();
        Station[] stations = competitionGroup.discipline().getStations();
        for (int i = 0; i < trials.length; i++) {
            Station station = stations[i % stations.length]; // make sure to not only use one station that is available
            Trials.Trial trial = trials[i];
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

        for (CompetitionGroup competitionGroup : competitionGroups) {
            Event event = new Event(
                    new ArrayList<>(),
                    new ArrayList<>(competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList()),
                    Station.AWARDS_STAGE,
                    competitionGroup.discipline(),
                    Trials.Trial.AWARD,
                    competitionGroup.age_group(),
                    competitionGroup.gender()
            );

            Objects.requireNonNull(station_events.putIfAbsent(event.station(), new ArrayList<>())).add(event);

            awardsEvents.add(event);
        }

        return awardsEvents;
    }
}
