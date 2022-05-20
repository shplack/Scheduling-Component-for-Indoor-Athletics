package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.AgeGroups;
import com.SCIA.Competitions.Trials;
import com.SCIA.Discipline.Disciplines.Discipline;
import com.SCIA.Discipline.Stations.Station;
import com.SCIA.Schedule.TimeSlot;

import java.util.*;

import static com.SCIA.Competitions.Trials.Trial.TRIAL;

public class EventMaker {
    static Map<Station, Integer> stationTimes = new HashMap<>(Station.values().length);

    private static int calculateIncrementalEventDuration(Event event) {
        return event.athletes().size() * 10 / TimeSlot.INCREMENT;
    }

    private static int calculateNonIncrementalEventDuration(CompetitionGroup competitionGroup) {
        Discipline discipline = competitionGroup.discipline();

        if (!discipline.isMeasuredInTime())
            return 1;

        float max = 0;
        for (AthleteRecord athleteRecord : competitionGroup.athleteRecordsList()) {
            max = Math.max(athleteRecord.getDisciplineRecords().getWorstRecord(discipline), max);
        }

        return (int) Math.ceil(max / 60 / TimeSlot.INCREMENT);
    }

    private static List<Event> assignTimeSlot(Event event, CompetitionGroup competitionGroup) {
        List<Event> continuedEvents = new ArrayList<>();
        int last_booked;
        int new_booked;
        if (!stationTimes.containsKey(event.station())) {
            new_booked = 1;
        } else {
            last_booked = stationTimes.get(event.station());
            new_booked = last_booked + 1;
        }

        int duration = competitionGroup.discipline().isIncremental() ?
                calculateIncrementalEventDuration(event) :
                calculateNonIncrementalEventDuration(competitionGroup);


        while (TimeSlot.pastLastTimeSlot(new_booked + duration)) {
            int eventDay = TimeSlot.getDay(new_booked);
            int lastTimeSlot = TimeSlot.getLastTimeSlot(eventDay);
            int available_time = lastTimeSlot - new_booked;

            Event eventCopy = new Event(event);

            List<Integer> time_slots = new ArrayList<>(available_time);
            for (int i = new_booked; i < lastTimeSlot; i++)
                time_slots.add(i);

            eventCopy.assignTimeSlots(time_slots);
            continuedEvents.add(eventCopy);

            new_booked = TimeSlot.getNextDayTimeSlot(new_booked);
            stationTimes.put(eventCopy.station(), new_booked);
            duration -= available_time;
        }

        if (duration <= 0)
            return continuedEvents;

        List<Integer> time_slots = new ArrayList<>(duration);
        for (int i = new_booked; i <= duration + new_booked; i++)
            time_slots.add(i);
        event.assignTimeSlots(time_slots);
        stationTimes.put(event.station(), new_booked + duration);
        return continuedEvents;
    }

    private static ArrayList<Event> makeXFinalsEvent(CompetitionGroup competitionGroup, Trials.Trial trial, Station station) {
        ArrayList<Athlete> athletes = new ArrayList<>(competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList());
        Discipline discipline = competitionGroup.discipline();
        AgeGroups.AgeGroup ageGroup = competitionGroup.age_group();

        int num_groups = trial.getNumGroups();
        if (num_groups <= 0)
            num_groups = 1;

        ArrayList<Event> events = new ArrayList<>(num_groups);
        Event event = new Event(null, athletes, station, discipline, trial, ageGroup, competitionGroup.gender());

        for (int i = 0; i < num_groups; i++) {
            Event eventCopy = new Event(event);
            events.addAll(assignTimeSlot(eventCopy, competitionGroup));
            events.add(eventCopy);
        }

        return events;
    }

    private static ArrayList<Event> makeQualifyingEvent(CompetitionGroup competitionGroup, Trials.Trial trial, Station station) {
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<AthleteRecord> athleteRecords = competitionGroup.athleteRecordsList();
        Discipline discipline = competitionGroup.discipline();
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

        athleteGroups.forEach(group -> {
            Event event = new Event(null, group, station, discipline, trial, ageGroup, competitionGroup.gender());
            events.add(event);
        });

        return events;
    }

    public static List<Event> makeIncrementalEvents(List<CompetitionGroup> incrementalDisciplines) {
        List<Event> events = new ArrayList<>(incrementalDisciplines.size());
        Map<Discipline, List<CompetitionGroup>> disciplineCompetitionGroupMap = new HashMap<>();

        incrementalDisciplines.forEach(competitionGroup -> {
            if (!disciplineCompetitionGroupMap.containsKey(competitionGroup.discipline()))
                disciplineCompetitionGroupMap.put(competitionGroup.discipline(), new ArrayList<>());
            disciplineCompetitionGroupMap.get(competitionGroup.discipline()).add(competitionGroup);
        });

        disciplineCompetitionGroupMap.forEach((discipline, competitionGroups) -> {
            Station[] stations = discipline.getStations();
            for (int i = 0; i < competitionGroups.size(); i++) {
                CompetitionGroup competitionGroup = competitionGroups.get(i);
                Event event = new Event(
                       null,
                       new ArrayList<>(competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList()),
                       stations[i % stations.length],
                       competitionGroup.discipline(),
                       TRIAL,
                       competitionGroup.age_group(),
                       competitionGroup.gender()
                );
                List<Event> eventContinuations = assignTimeSlot(event, competitionGroup);
                events.addAll(eventContinuations);
                events.add(event);
            }
        });

        return events;
    }

    private static ArrayList<Event> makeNonIncrementalEvents(CompetitionGroup competitionGroup, Trials.Trial trial, Station station) {
        ArrayList<Event> events = new ArrayList<>();

        competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).forEach( (athlete) ->
                {
                    Event event = new Event(
                            null, new ArrayList<>(List.of(athlete)), station, competitionGroup.discipline(),
                            trial, competitionGroup.age_group(), competitionGroup.gender()
                    );
                    events.add(event);
                }
        );

        return events;
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

            events.addAll(switch(trial) {
                case QUALIFYING -> makeQualifyingEvent(competitionGroup, trial, station);
                case QUARTER_FINAL, SEMI_FINAL, FINAL -> makeXFinalsEvent(competitionGroup, trial, station);
                default -> makeNonIncrementalEvents(competitionGroup, trial, station);
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

            awardsEvents.add(event);
        }

        return awardsEvents;
    }
}
