package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Athlete.Gender;
import com.SCIA.Competitions.AgeGroups.AgeGroup;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.AgeGroups;
import com.SCIA.Competitions.Trials;
import com.SCIA.Competitions.Trials.Trial;
import com.SCIA.Discipline.Disciplines.Discipline;
import com.SCIA.Discipline.Stations.Station;
import com.SCIA.Schedule.TimeSlot;

import java.util.*;

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
        boolean conflict = false;
        for (Athlete athlete : eventToCheck.athletes()) {
            if (event.athletes().contains(athlete)) {
                conflict = true;
                break;
            }
        }

        if (!conflict)
            return false;


        List<Integer> time_slots = eventToCheck.time_slots();
        int start1 = time_slots.get(0);
        int end1 = time_slots.get(time_slots.size()-1);
        int start2 = time_slot;
        int end2 = time_slot + duration - 1;

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

    private static int nextAvailableTimeSlot(int duration, Event event) {
        AgeGroup ageGroup = event.age_group();
        int next_booked = stationTimes.getOrDefault(event.station(), 0) + 1;

        List<Event> eventsToCheck = new LinkedList<>();
        stationEvents.forEach(events -> {
            eventsToCheck.addAll(events.stream().filter(eventToCheck -> eventToCheck.age_group() == ageGroup).toList());
        });

        for (Event checkEvent : eventsToCheck) {
            if (hasConflict(checkEvent, event, next_booked, duration)) {
                int end = checkEvent.time_slots().get(checkEvent.time_slots().size() - 1);
                next_booked = end + 1;
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

        assert duration < TimeSlot.getLastTimeSlot();

        int new_booked = nextAvailableTimeSlot(duration, event);

        List<Integer> time_slots = new ArrayList<>(duration);
        for (int i = new_booked; i < duration + new_booked; i++)
            time_slots.add(i);

        boolean sameDay = TimeSlot.getDay(time_slots.get(0)) == TimeSlot.getDay(time_slots.get(time_slots.size()-1));
        assert sameDay;

        event.assignTimeSlots(time_slots);
        stationTimes.put(event.station(), new_booked + duration);
        assignEventToStation(event);
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

    public static List<Event> makeTrialEvents(List<CompetitionGroup> competitionGroups) {
        List<Event> events = new ArrayList<>(competitionGroups.size());

        Station[] stations;
        for (int i = 0; i < competitionGroups.size(); i++) {
            CompetitionGroup group = competitionGroups.get(i);
            List<Athlete> athletes = group.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList();
            Discipline discipline = group.discipline();
            stations = discipline.getStations();
            Station station = whichStation(stations);
            Event event = new Event(null, athletes, station, discipline, TRIAL, group.age_group(), group.gender());
            assignTimeSlot(event, group);
            events.add(event);
        }

        return events;
    }

    public static List<Event> makeRunningEvents(List<CompetitionGroup> competitionGroups) {
        Trial[] trials = Trials.runningTrials();
        List<Event> events = new ArrayList<>(trials.length * competitionGroups.size());

        for (Trial trial : trials) {
            competitionGroups.forEach(competitionGroup -> {
                Station station = whichStation(competitionGroup.discipline().getStations());
                if (trial == QUALIFYING)
                    events.addAll(makeQualifyingEvent(competitionGroup, station));
                else
                    events.addAll(makeXFinalsEvent(competitionGroup, trial, station));
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
