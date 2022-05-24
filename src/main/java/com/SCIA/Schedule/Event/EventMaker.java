package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.Competitions.AgeGroup;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.Trial;
import com.SCIA.Discipline.Discipline;
import com.SCIA.Discipline.Station;
import com.SCIA.Schedule.TimeSlot;

import java.util.*;
import java.util.stream.Collectors;

public class EventMaker {
    // A map of stations to the last time slot that was used for that station.
    static Map<Station, TimeSlot> stationTimes = new HashMap<>(Station.values().length);

    // Creating a list of lists of events. The outer list is the size of the number of stations. The inner list is a list
    // of events that are happening at that station. // Better performance than a HashMap
    static List<List<Event>> stationEvents = new ArrayList<>(Station.values().length);

    static {
        for (int i = 0; i < Station.values().length; i++) {
            stationEvents.add(new LinkedList<>());
        }
    }

    /**
     * Assign the event to a station
     *
     * @param event The event to be added to the stationEvents list.
     */
    private static void assignEventToStation(Event event) {
        stationEvents.get(event.station().ordinal()).add(event);
    }

    /**
     * Calculate the number of time slots needed for the given event
     *
     * @param event The event
     * @return The number of time slots needed
     */
    private static int calculateEventDurationDisciplineMeasuredInDistance(Event event) {
        int numAthletes = event.athletes().size();
        int minutesPerAthlete = event.discipline().duration();
        return (int) Math.ceil((double) numAthletes * minutesPerAthlete / TimeSlot.INCREMENT);
    }

    /**
     * If the eventToCheck is a trial that must happen before the event, and the eventToCheck is after the timeSlot, then
     * there is a conflict
     *
     * @param eventToCheck The event that we're checking to see if it conflicts with the event we're trying to schedule.
     * @param event        the event that we're trying to schedule
     * @param timeSlot     the time slot we're trying to schedule the event in
     * @return True if the events conflict
     */
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

    /**
     * Checks if event1 must happen before event2 and if event1 does happen before event2, or if the event's
     * time slots conflict if they are at the same station
     *
     * @param event1 The first event to compare
     * @param event2 The event that we're checking for conflicts with.
     * @return True if the events conflict
     */
    static boolean hasConflict(Event event1, Event event2) {
        if (!canConflict(event1, event2)) return false;

        if (event1.trial().mustHappenBefore(event2.trial())) {
            if (event1.timeSlot().compareTo(event2.timeSlot()) > 0) {
                return true;
            }
        }

        return event1.timeSlot().conflictsWith(event2.timeSlot());
    }

    /**
     * If the two events are in the same age group, and they are not both running events in the same trial, then they can
     * conflict.
     *
     * @param event1 The first event to check for conflicts
     * @param event2 The event that we're checking for conflicts with
     * @return True if there is a possibility they can conflict
     */
    private static boolean canConflict(Event event1, Event event2) {
        if (event1.trial().mustHappenBefore(event2.trial()))
            return true;
        if (event1.age_group() != event2.age_group())
            return false;

        Set<Athlete> intersectingAthletes = event1.athletes().stream().distinct().filter(event2.athletes()::contains)
                .collect(Collectors.toSet());

        return !intersectingAthletes.isEmpty();
    }

    /**
     * > This function checks if the event has a conflict with any other event in the list of events
     *
     * @param event the event to check for conflicts
     * @return True if the event has a conflict
     */
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

    /**
     * > Find the next available time slot for an event, given the last time slot tried, the duration of the event, and the
     * event itself
     *
     * @param last_tried The last time slot that was tried to be booked.
     * @param duration the duration of the event
     * @param event The event we're trying to book
     * @return The next available time slot for the event.
     */
    private static TimeSlot nextAvailableTimeSlot(Integer last_tried, int duration, Event event) {

        TimeSlot timeSlot; // the time slot to assign to the event
        if (last_tried == null) // first assignment
            // get the last booked time slot at a station, or create a new time slot
            timeSlot = stationTimes.getOrDefault(event.station(), new TimeSlot(0, duration));
        else
            timeSlot = new TimeSlot(last_tried, duration);
        timeSlot.setTimeSlot(timeSlot.getLastTimeSlot() + 1, duration); // Get the next time slot

        List<Event> eventsToCheck = new LinkedList<>();
        stationEvents.forEach(events -> { // get a list of possibly conflicting events
            eventsToCheck.addAll(events.stream().filter(eventToCheck -> canConflict(event, eventToCheck)).toList());
        });

        // find conflicting events
        for (Event checkEvent : eventsToCheck) {
            if (hasConflict(checkEvent, event, timeSlot)) {
                // if the conflicting event's time slot is higher, assign the next time slot
                int newTimeSlot = Math.max(timeSlot.getLastTimeSlot(), checkEvent.timeSlot().getLastTimeSlot() + 1);
                timeSlot.setTimeSlot(newTimeSlot);
            }
        }

        if (timeSlot.getDay() == 2 && event.discipline() == Discipline.MIDDLE1500M && event.trial() == Trial.SEMI_FINAL && event.station() == Station.RUNNING_TRACK && event.age_group() == AgeGroup.EIGHT_AND_UNDER)
            System.out.print("");

        // if the time slot goes past the last available time slot for the day, start the time slot at the
        // first time slot the next day
        if (timeSlot.isInvalid())
            timeSlot.setTimeSlot(TimeSlot.getFirstTimeSlot(timeSlot.getDay() + 1));

        return timeSlot;
    }

    /**
     * Calculate the number of time slots required to accommodate the longest time taken by any athlete in the competition
     * group.
     *
     * @param competitionGroup The competition group that the event is for.
     * @return The number of time slots required to run the competition group.
     */
    private static int calculateEventDurationDisciplineMeasuredInTime(CompetitionGroup competitionGroup) {
        Discipline discipline = competitionGroup.discipline();


        if (!discipline.isMeasuredInTime())
            return 1;

        float max = 0;
        for (AthleteRecord athleteRecord : competitionGroup.athleteRecordsList()) {
            max = Math.max(athleteRecord.getDisciplineRecords().getWorstRecord(discipline), max);
        }

        return (int) Math.ceil(max / 60 / TimeSlot.INCREMENT);
    }

    /**
     * > Assign a time slot to an event, and if it conflicts with another event, assign a new time slot to the event
     *
     * @param event the event to be scheduled
     * @param competitionGroup the group of competitors that are competing in the event
     */
    private static void assignTimeSlot(Event event, CompetitionGroup competitionGroup) {
        /*
         * Calculate the duration for the event based on if the event is an awards ceremony or based on if the event's
         * discipline is measured in time or distance.
         * Awards ceremonies don't take long.
         */
        int duration = competitionGroup.discipline().isTrialDiscipline() ? calculateEventDurationDisciplineMeasuredInDistance(event) :
                calculateEventDurationDisciplineMeasuredInTime(competitionGroup);

        // no support for multi-day events
        try {
            assert duration < TimeSlot.getNumTimeSlotsPerDay();
        } catch (AssertionError e) {
            System.err.println("The duration calculated for the event is longer than one full day.\n" +
                    "Support for multi-day events is absent.");
            System.err.println("Event: " + event);
            System.err.println("The calculated duration for the event: " + duration);
            e.printStackTrace();
            System.exit(-1);
        }

        // get the next available time slot
        TimeSlot newTimeSlot = nextAvailableTimeSlot(null, duration, event);

        // assign the time slot and analyze conflicts, assigning new ones until a non-conflicting time slot is found
        event.assignTimeSlot(newTimeSlot);
        long counter = 0;
        long maxIterations = 10000;
        while (counter++ < maxIterations && hasConflict(event)) {
            newTimeSlot = nextAvailableTimeSlot(newTimeSlot.getTimeSlot(), duration, event);
            event.assignTimeSlot(newTimeSlot);
        }

        // if we pass 10 000 iterations, give up
        try {
            assert counter < maxIterations;
        } catch (AssertionError e) {
            System.err.println("Could not find a time slot for the event after " + counter + " tries.");
            e.printStackTrace();
            System.exit(-1);
        }

        assignEventToStation(event);
        stationTimes.put(event.station(), newTimeSlot);
    }

    /**
     * This function takes a competition group, trial, and station and returns a list of events that are created from the
     * competition group
     *
     * @param competitionGroup The group of athletes that are competing in this event.
     * @param trial The trial that the event is being created for.
     * @param station The station where the event will be held
     * @return An ArrayList of Events
     */
    private static ArrayList<Event> makeXFinalsEvent(CompetitionGroup competitionGroup, Trial trial, Station station) {
        ArrayList<Athlete> athletes = new ArrayList<>(competitionGroup.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList());
        Discipline discipline = competitionGroup.discipline();
        AgeGroup ageGroup = competitionGroup.age_group();

        // the number of groups needed for a finals event
        int num_groups = trial.getNumGroups();
        if (num_groups <= 0)
            num_groups = 1;

        ArrayList<Event> events = new ArrayList<>(num_groups);

        for (int i = 0; i < num_groups; i++) {
            Event event = new Event(null, athletes, station, discipline, trial, ageGroup, competitionGroup.gender());
            assignTimeSlot(event, competitionGroup);
            events.add(event);
        }

        return events;
    }

    /**
     * Get competition group and make a number of events needed for each athlete to compete in a
     * qualifying event
     *
     * @param competitionGroup The competition group that the event is being made for.
     * @param station          The station that the event will be held at
     * @return An ArrayList of Events
     */
    private static List<Event> makeQualifyingEvent(CompetitionGroup competitionGroup, Station station) {
        List<Event> events = new ArrayList<>();
        List<AthleteRecord> athleteRecords = competitionGroup.athleteRecordsList();
        Discipline discipline = competitionGroup.discipline();
        AgeGroup ageGroup = competitionGroup.age_group();

        // the number of groups needed to accommodate the station's capacity
        int num_groups = (int) Math.ceil((double) athleteRecords.size() / station.capacity());

        List<List<Athlete>> athleteGroups = new ArrayList<>(num_groups);
        for (int i = 0; i < num_groups; i++)
            athleteGroups.add(new LinkedList<>());

        // assign each athlete to a group
        for (int i = 0, groupNum = 0; i < athleteRecords.size(); i++) {
            if (athleteGroups.get(groupNum).size() >= station.capacity())
                groupNum++;
            athleteGroups.get(groupNum).add(athleteRecords.get(i).getAthlete());
        }

        // make events for each group
        athleteGroups.forEach(group -> {
            Event event = new Event(null, group, station, discipline, Trial.QUALIFYING, ageGroup, competitionGroup.gender());
            assignTimeSlot(event, competitionGroup);
            events.add(event);
        });

        return events;
    }

    /**
     * For each competition group, if there are enough participating athletes,
     * create an event for the group and assign it a time slot
     *
     * @param competitionGroups a list of CompetitionGroup objects
     * @param trial The trial that the events are being made for
     * @return A list of events.
     */
    public static List<Event> makeTrialEvents(List<CompetitionGroup> competitionGroups, Trial trial) {
        List<Event> events = new ArrayList<>(competitionGroups.size());

        for (CompetitionGroup group : competitionGroups) {
            Discipline discipline = group.discipline();
            Station station = whichStation(discipline.getStations());
            if (trial.enoughAthletes(station, group.athleteRecordsList().size())) {
                List<Athlete> athletes = group.athleteRecordsList().stream().map(AthleteRecord::getAthlete).toList();
                Event event = new Event(null, athletes, station, discipline, trial, group.age_group(), group.gender());
                assignTimeSlot(event, group);
                events.add(event);
            }
        }

        return events;
    }

    /**
     * Make events for each running discipline
     *
     * @param competitionGroups a list of competition groups
     * @return A list of events.
     */
    public static List<Event> makeRunningEvents(List<CompetitionGroup> competitionGroups) {
        Trial[] trials = Trial.runningTrials();
        List<Event> events = new ArrayList<>(trials.length * competitionGroups.size());

        for (Trial trial : trials) {
            competitionGroups.forEach(competitionGroup -> {
                Station station = whichStation(competitionGroup.discipline().getStations());
                if (trial.enoughAthletes(station, competitionGroup.athleteRecordsList().size())) {
                    if (trial == Trial.QUALIFYING)
                        events.addAll(makeQualifyingEvent(competitionGroup, station));
                    else
                        events.addAll(makeXFinalsEvent(competitionGroup, trial, station));
                }
            });
        }

        return events;
    }

    /**
     * It returns the station with the least number of events to evenly divide events among stations
     *
     * @param stations an array of all the stations
     * @return The station with the least number of events.
     */
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

    private static int getLatestDay() {
        TimeSlot latest = new TimeSlot(0, 0);

        for (List<Event> events : stationEvents) {
            if (events.size() != 0) {
                Event event = events.get(events.size() - 1);
                latest = TimeSlot.max(latest, event.timeSlot());
            }
        }

        return latest.getDay();
    }

    /**
     * For each competition group, create an event for the awards ceremony, assign it a time slot,
     * and add it to the list of events
     *
     * @param competitionGroups The competition groups to have awards for
     * @return A list of events.
     */
    public static List<Event> awardsCeremony(List<CompetitionGroup> competitionGroups) {
        ArrayList<Event> awardsEvents = new ArrayList<>(competitionGroups.size());

        TimeSlot start = new TimeSlot(TimeSlot.getFirstTimeSlot(getLatestDay() + 1), 1);

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

            event.assignTimeSlot(start);
            start.setTimeSlot(start.getTimeSlot() + 1);
            awardsEvents.add(event);
        }

        return awardsEvents;
    }
}
