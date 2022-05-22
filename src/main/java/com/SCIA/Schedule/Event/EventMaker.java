package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Athlete.Gender;
import com.SCIA.Competitions.AgeGroups.AgeGroup;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.Trials;
import com.SCIA.Competitions.Trials.Trial;
import com.SCIA.Discipline.Disciplines.Discipline;
import com.SCIA.Discipline.Stations.Station;
import com.SCIA.Schedule.TimeSlot;

import java.util.*;
import java.util.stream.Collectors;

import static com.SCIA.Competitions.Trials.Trial.*;

public class EventMaker {
    static Map<Station, TimeSlot> stationTimes = new HashMap<>(Station.values().length);
    static List<List<Event>> stationEvents = new ArrayList<>(Station.values().length);
    static {
        for (int i = 0; i < Station.values().length; i++) {
            stationEvents.add(new LinkedList<>());
        }
    }

    private static void assignEventToStation(Event event) {
        stationEvents.get(event.station().ordinal()).add(event);
    }

    private static int calculateIncrementalEventDuration(Event event) {
        int numAthletes = event.athletes().size();
        int minutesPerAthlete = event.discipline().duration();
        int duration = (int) Math.ceil((double) numAthletes * minutesPerAthlete / TimeSlot.INCREMENT);
        return duration;
    }

    private static boolean hasConflict(Event eventToCheck, Event event, TimeSlot timeSlot) {
        if (!canConflict(eventToCheck, event))
            return false;

        if (eventToCheck.trial().mustHappenBefore(event.trial())) {
            if (eventToCheck.timeSlot().compareTo(timeSlot) > 0) {
                return true;
            }
        }

        return eventToCheck.timeSlot().conflictsWith(timeSlot);
    }

    static boolean hasConflict(Event event1, Event event2) {
        if (!canConflict(event1, event2)) return false;

        if (event1.trial().mustHappenBefore(event2.trial())) {
            if (event1.timeSlot().compareTo(event2.timeSlot()) > 0) {
                return true;
            }
        }

        return event1.timeSlot().conflictsWith(event2.timeSlot());
    }

    private static boolean canConflict(Event event1, Event event2) {
        if (event1.trial().mustHappenBefore(event2.trial()))
            return true;
        if (event1.age_group() != event2.age_group())
            return false;

//        if (event1.discipline().isRunningDiscipline() && event2.discipline().isRunningDiscipline()) {
//            if (event1.trial() != Trial.QUALIFYING && event2.trial() != Trial.QUALIFYING) {
//                if (event1.trial() == event2.trial() && (event1.trial() != AWARD && event2.trial() != AWARD)) {
//                    return false;
//                }
//            }
//        }

        Set<Athlete> intersectingAthletes = event1.athletes().stream().distinct().filter(event2.athletes()::contains)
                .collect(Collectors.toSet());

        return !intersectingAthletes.isEmpty();
    }

    private static boolean hasConflict(Event event) {
        List<Event> eventList = new LinkedList<>();
        for (List<Event> events : stationEvents) {
            eventList.addAll(events);
        }

        for (Event check : eventList) {
            if (hasConflict(check, event))
                    return true;
        }

        return false;
    }

    private static TimeSlot nextAvailableTimeSlot(Integer last_tried, int duration, Event event) {

        TimeSlot next_booked;
        if (last_tried == null)
            next_booked = stationTimes.getOrDefault(event.station(), new TimeSlot(1, duration));
        else
            next_booked = new TimeSlot(last_tried, duration);
        next_booked.setTimeSlot(next_booked.getTimeSlot() + 1);

        List<Event> eventsToCheck = new LinkedList<>();
        stationEvents.forEach(events -> {
            eventsToCheck.addAll(events.stream().filter(eventToCheck -> canConflict(event, eventToCheck)).toList());
        });

        for (Event checkEvent : eventsToCheck) {
            if (hasConflict(checkEvent, event, next_booked)) {
                int newTimeSlot = Math.max(next_booked.getLastTimeSlot(), checkEvent.timeSlot().getLastTimeSlot() + 1);
                next_booked.setTimeSlot(newTimeSlot);
            }
        }

        if (next_booked.pastLastTimeSlot())
            next_booked.setTimeSlot(TimeSlot.getFirstTimeSlot(next_booked.getDay() + 1));

        return next_booked;
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

    private static void assignTimeSlot(Event event, CompetitionGroup competitionGroup) {
        int duration =  event.trial() == AWARD ? 1 :
                competitionGroup.discipline().isTrialDiscipline() ? calculateIncrementalEventDuration(event) :
                calculateNonIncrementalEventDuration(competitionGroup);

        assert duration < TimeSlot.getNumTimeSlotsPerDay();

        TimeSlot newTimeSlot = nextAvailableTimeSlot(null, duration, event);

        event.assignTimeSlot(newTimeSlot);
        while (hasConflict(event)) {
            newTimeSlot = nextAvailableTimeSlot(newTimeSlot.getTimeSlot(), duration, event);
            event.assignTimeSlot(newTimeSlot);
        }
        assignEventToStation(event);
        stationTimes.put(event.station(), newTimeSlot);
    }

    private static ArrayList<Event> makeXFinalsEvent(CompetitionGroup competitionGroup, Trial trial, Station station) {
        ArrayList<Athlete> athletes = new ArrayList<>(competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList());
        Discipline discipline = competitionGroup.discipline();
        AgeGroup ageGroup = competitionGroup.age_group();

        int num_groups = trial.getNumGroups();
        if (num_groups <= 0)
            num_groups = 1;

        ArrayList<Event> events = new ArrayList<>(num_groups);
        Event event = new Event(null, athletes, station, discipline, trial, ageGroup, competitionGroup.gender());

        for (int i = 0; i < num_groups; i++) {
            Event eventCopy = new Event(event);
            assignTimeSlot(eventCopy, competitionGroup);
            events.add(eventCopy);
        }

        return events;
    }

    private static ArrayList<Event> makeQualifyingEvent(CompetitionGroup competitionGroup, Station station) {
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

        athleteGroups.forEach(group -> {
            Event event = new Event(null, group, station, discipline, QUALIFYING, ageGroup, competitionGroup.gender());
            assignTimeSlot(event, competitionGroup);
            events.add(event);
        });

        return events;
    }

    public static List<Event> makeTrialEvents(List<CompetitionGroup> competitionGroups, Trial trial) {
        List<Event> events = new ArrayList<>(competitionGroups.size());

        Station[] stations;
        for (CompetitionGroup group : competitionGroups) {
            Discipline discipline = group.discipline();
            stations = discipline.getStations();
            Station station = whichStation(stations);
            if (trial.canHazTrial(station, group.athleteRecordsList().size())) {
                List<Athlete> athletes = group.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList();
                Event event = new Event(null, athletes, station, discipline, trial, group.age_group(), group.gender());
                assignTimeSlot(event, group);
                events.add(event);
            }
        }

        return events;
    }

    public static List<Event> makeRunningEvents(List<CompetitionGroup> competitionGroups) {
        Trial[] trials = Trials.runningTrials();
        List<Event> events = new ArrayList<>(trials.length * competitionGroups.size());

        for (Trial trial : trials) {
            competitionGroups.forEach(competitionGroup -> {
                Station station = whichStation(competitionGroup.discipline().getStations());
                if (trial.canHazTrial(station, competitionGroup.athleteRecordsList().size())) {
                    if (trial == QUALIFYING)
                        events.addAll(makeQualifyingEvent(competitionGroup, station));
                    else
                        events.addAll(makeXFinalsEvent(competitionGroup, trial, station));
                }
            });
        }

        return events;
    }

    private static Station whichStation(Station[] stations) {
        Station whichStation = stations[0]; // make sure to not only use one station that is available
        int min = Integer.MAX_VALUE;
        for (Station station : stations) {
            int numEvents = stationEvents.get(station.ordinal()).size();
            if (numEvents == 0) {
                whichStation = station;
                break;
            }

            if (Math.min(min, numEvents) == numEvents) {
                min = numEvents;
                whichStation = station;
            }
        }

        return whichStation;
    }

    public static List<Event> awardsCeremony (List<CompetitionGroup> competitionGroups) {
        ArrayList<Event> awardsEvents = new ArrayList<>(competitionGroups.size());

        for (CompetitionGroup competitionGroup : competitionGroups) {
            Event event = new Event(
                    null,
                    new ArrayList<>(competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList()),
                    Station.AWARDS_STAGE,
                    competitionGroup.discipline(),
                    Trial.AWARD,
                    competitionGroup.age_group(),
                    competitionGroup.gender()
            );
            assignTimeSlot(event, competitionGroup);
            awardsEvents.add(event);
        }

        return awardsEvents;
    }
}
