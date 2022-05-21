package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Competitions.AgeGroups.AgeGroup;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.Trials;
import com.SCIA.Competitions.Trials.Trial;
import com.SCIA.Discipline.Disciplines.Discipline;
import com.SCIA.Discipline.Stations.Station;
import com.SCIA.Schedule.TimeSlot;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

import static com.SCIA.Competitions.Trials.Trial.*;

public class EventMaker {
    static Map<Station, Integer> stationTimes = new HashMap<>(Station.values().length);
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

    private static boolean hasConflict(Event eventToCheck, Event event, int time_slot, int duration) {
        if (!canConflict(eventToCheck, event))
            return false;


        List<Integer> time_slots = eventToCheck.time_slots();
        int start1 = time_slots.get(0);
        int end1 = time_slots.get(time_slots.size()-1);
        int start2 = time_slot;
        int end2 = time_slot + duration - 1;

        return timeSlotConflict(start1, end1, start2, end2);

    }

    private static boolean timeSlotConflict(int start1, int end1, int start2, int end2) {
        if (start1 >= start2 && start1 <= end2)
            return true;
        if (end1 >= start2 && end1 <= end2)
            return true;
        if (start2 >= start1 && start2 <= end1)
            return true;
        if (end2 >= start1 && end2 <= end1)
            return true;

        return false;
    }

    static boolean hasConflict(Event event1, Event event2) {
        if (!canConflict(event1, event2)) return false;

        List<Integer> time_slots1 = event1.time_slots();
        int start1 = time_slots1.get(0);
        int end1 = time_slots1.get(time_slots1.size()-1);

        List<Integer> time_slots2 = event2.time_slots();
        int start2 = time_slots2.get(0);
        int end2 = time_slots2.get(time_slots2.size()-1);

        return timeSlotConflict(start1, end1, start2, end2);
    }

    private static boolean canConflict(Event event1, Event event2) {
        if (event1.age_group() != event2.age_group())
            return false;

        if (event1.discipline().isRunningDiscipline() && event2.discipline().isRunningDiscipline()) {
            if (event1.trial() != Trial.QUALIFYING && event2.trial() != Trial.QUALIFYING) {
                if (event1.trial() == event2.trial() && (event1.trial() != AWARD && event2.trial() != AWARD)) {
                    return false;
                }
            }
        }

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
            if (hasConflict(event, check))
                    return true;
        }

        return false;
    }

    private static boolean hasConflicts() {
        List<Event> eventList = new LinkedList<>();
        for (List<Event> events : stationEvents) {
            eventList.addAll(events);
        }

        for (int i = 0; i < eventList.size() - 1; i++) {
            for (int j = i + 1; j < eventList.size(); j++) {
                Event event1 = eventList.get(i);
                Event event2 = eventList.get(j);
                if (hasConflict(event1, event2)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static int nextAvailableTimeSlot(Integer last_tried, int duration, Event event) {

        int next_booked;
        if (last_tried == null)
            next_booked = stationTimes.getOrDefault(event.station(), 0) + 1;
        else
            next_booked = last_tried;

        List<Event> eventsToCheck = new LinkedList<>();
        stationEvents.forEach(events -> {
            eventsToCheck.addAll(events.stream().filter(eventToCheck -> canConflict(event, eventToCheck)).toList());
        });

        for (Event checkEvent : eventsToCheck) {
            if (hasConflict(checkEvent, event, next_booked, duration)) {
                int end = checkEvent.time_slots().get(checkEvent.time_slots().size() - 1);
                next_booked = Math.max(next_booked, end + 1);
            }
        }

        if (TimeSlot.pastLastTimeSlot(next_booked, duration))
            next_booked = TimeSlot.getNextDayTimeSlot(next_booked);
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

        int new_booked = nextAvailableTimeSlot(null, duration, event);

        List<Integer> time_slots = new ArrayList<>(duration);
        for (int i = new_booked; i < duration + new_booked; i++)
            time_slots.add(i);

        boolean sameDay = TimeSlot.getDay(time_slots.get(0)) == TimeSlot.getDay(time_slots.get(time_slots.size()-1));
        assert sameDay;

        event.assignTimeSlots(time_slots);
        while (hasConflict(event)) {
            new_booked = nextAvailableTimeSlot(new_booked, duration, event);

            time_slots = new ArrayList<>(duration);
            for (int i = new_booked; i < duration + new_booked; i++)
                time_slots.add(i);
            event.assignTimeSlots(time_slots);
        }
        assignEventToStation(event);

        stationTimes.put(event.station(), new_booked + duration);
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
                    new ArrayList<>(),
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
