package Schedule.Event;

import Athlete.Athlete;
import Athlete.AthleteRecord;
import Schedule.TimeSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static Competitions.AgeGroups.AgeGroup;
import static Competitions.Trials.Trial;
import static Discipline.Disciplines.Discipline;
import static Discipline.Stations.Station;

public class EventMaker {
    private static Map<Station, Integer> station_time_slot = new HashMap<>();

    public static ArrayList<Event> makeQualifyingEvents(Discipline discipline, ArrayList<AthleteRecord> athleteRecords, AgeGroup age_group) {
        final Trial trial = Trial.QUALIFYING;
        ArrayList<Event> eventList = new ArrayList<>();
        Station[] stations = discipline.getStations();
        Station station = stations[0];
        
        int athlete_limit = station.getAthleteLimit();
        int num_groups = (int) Math.ceil((double) athleteRecords.size() / athlete_limit);
        int remainder = athlete_limit * num_groups - athleteRecords.size();
        int group_num = 0;

        if (trial.getNumGroups() != 0 && trial.getNumGroups() * athlete_limit < athleteRecords.size())
            return null;

        outer_loop:
        for (int i = 0, j = 0, group_size; j < num_groups; i++, j += group_size, group_num++) {
            int[] time_slots;
            double max_time = 0;
            ArrayList<Athlete> athletes = new ArrayList<>();
            group_size = athlete_limit;
            if (remainder > 0 && group_num < remainder)
                group_size--;

            for (int k = j; k < j + group_size; k++) {
                if (k >= athleteRecords.size())
                    break outer_loop;

                AthleteRecord athleteRecord = athleteRecords.get(k);

                if (discipline.isMeasuredInTime()) {
                    float worst_time = athleteRecord.getDisciplineRecords().getWorstRecord(discipline);
                    max_time = Math.max(max_time, worst_time);
                }

                athletes.add(athleteRecord.getAthlete());
            }

            int num_time_slots = (int) Math.ceil(max_time / 60 / TimeSlot.INCREMENT);
            if (num_time_slots == 0)
                num_time_slots = 1;

            time_slots = new int[num_time_slots];
            int time_slot = 1;
            if (station_time_slot.containsKey(station))
                time_slot = station_time_slot.get(station) + 1;

            for (int k = 0; k < num_time_slots; k++)
                time_slots[k] = time_slot + k;

            station_time_slot.put(station, time_slots[time_slots.length - 1]);

            Event event = new Event(time_slots, athletes, stations[i % stations.length], discipline, trial, age_group);
            eventList.add(event);
        }
        
        return eventList;
    }

    private static ArrayList<Event> makeQuarterFinalEvents(Discipline discipline, ArrayList<AthleteRecord> athleteRecords, AgeGroup age_group) {
        final Trial trial = Trial.QUARTER_FINAL;
        Station[] stations = discipline.getStations();
        int min_num_athlete = stations[0].getAthleteLimit();

        if (athleteRecords.size() < min_num_athlete)
            return null;

        ArrayList<Event> eventList = new ArrayList<>();

        AtomicReference<Float> max_time = new AtomicReference<>((float) 0);
        athleteRecords.stream().map(athleteRecord -> athleteRecord.getDisciplineRecords()
                        .getWorstRecord(discipline)).collect(Collectors.toCollection(ArrayList::new))
                .stream().max(Float::compare).ifPresent(max_time::set); // don't even ask

        int num_time_slots = (int) Math.ceil(max_time.get() / 60 / TimeSlot.INCREMENT);

        for (int i = 0; i < trial.getNumGroups(); i++) {
            Station station = stations[i % stations.length];

            int time_slot = 1;
            if (station_time_slot.containsKey(station))
                time_slot = station_time_slot.get(station) + 1;

            int[] time_slots = new int[num_time_slots];
            for (int j = 0; j < time_slots.length; j++)
                time_slots[j] = time_slot + j;

            station_time_slot.put(station, time_slots[time_slots.length - 1]);

            eventList.add(new Event(time_slots, athleteRecords.stream().map(athleteRecord -> athleteRecord.getAthlete())
                            .collect(Collectors.toCollection(ArrayList::new)), station, discipline, trial, age_group));
        }

        return eventList;
    }

    private static ArrayList<Event> makeSemiFinalEvents(Discipline discipline, ArrayList<AthleteRecord> athleteRecords, AgeGroup age_group) {
        ArrayList<Event> eventList = new ArrayList<>();
        return eventList;
    }

    private static ArrayList<Event> makeFinalsEvents(Discipline discipline, ArrayList<AthleteRecord> athleteRecords, AgeGroup age_group) {
        ArrayList<Event> eventList = new ArrayList<>();
        return eventList;
    }

    public static ArrayList<Event> makeEvents(Discipline discipline, ArrayList<AthleteRecord> athleteRecords, AgeGroup age_group) {
        ArrayList<Event> eventList = new ArrayList<>();

        ArrayList<Athlete> athletes = athleteRecords.stream()
                .map(AthleteRecord::getAthlete)
                .collect(Collectors.toCollection(ArrayList::new));

        for (AthleteRecord athleteRecord : athleteRecords)
            athletes.add(athleteRecord.getAthlete());

        for (Trial trial : discipline.getTrials()) {
            ArrayList<Event> events = switch(trial) {
                    case QUALIFYING -> makeQualifyingEvents(discipline, athleteRecords, age_group);
                    case QUARTER_FINAL -> makeQuarterFinalEvents(discipline, athleteRecords, age_group);
                    case SEMI_FINAL -> makeSemiFinalEvents(discipline, athleteRecords, age_group);
                    case FINAL -> makeFinalsEvents(discipline, athleteRecords, age_group);
                    default -> null;
                };
            if (events != null)
                eventList.addAll(events);
        }

        return eventList;
    }
}
