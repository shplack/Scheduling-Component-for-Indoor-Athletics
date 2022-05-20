package com.SCIA.Schedule.Event;

import com.SCIA.Athlete.Athlete;
import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Discipline.Stations.Station;
import com.SCIA.Schedule.Schedule;
import com.SCIA.Schedule.ScheduleMaker;
import com.SCIA.Schedule.ScheduleSorters;
import com.SCIA.Schedule.TimeSlot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EventMakerTest {

    static List<List<Event>> stationEvents;
    static Schedule schedule;
    static ArrayList<AthleteRecord> records;

    @BeforeAll
    static void setUp() throws IOException {
        records = new CSV("registration-list.csv").getRecords();
        schedule = ScheduleMaker.makeSchedule(CompetitionGroupsMaker.makeCompetitionGroups(records));
    }

    @Test
    void printStationEvents() {
        stationEvents.forEach(eventList -> eventList.forEach(System.out::println));
    }

//    @Test
//    void eventEndsOnSameDayAsItStarts() {
//        schedule.eventList().forEach(event -> {
//            ArrayList<Integer> time_slots = event.time_slots();
//            assertEquals(TimeSlot.getDay(time_slots.get(0)), TimeSlot.getDay(time_slots.get(time_slots.size()-1)+1));
//        });
//    }


    int calculateEventDuration(Event event) {
        if (!event.discipline().isMeasuredInTime())
            return 1;
        ArrayList<Athlete> athletes = event.athletes();
        List<AthleteRecord> athleteRecords = new ArrayList<>(athletes.size());
        float max = 0;
        for (AthleteRecord athleteRecord : records) {
            for (Athlete athlete : athletes) {
                if (athlete.equals(athleteRecord.getAthlete()))
                    max = Math.max(athleteRecord.getDisciplineRecords().getWorstRecord(event.discipline()), max);
            }
        }

        return (int) Math.ceil(max / 60 / TimeSlot.INCREMENT);
    }

    @Test
    void assignEventTimes() {
        Map<Station, Integer> stationTimes = new HashMap<>(Station.values().length);
        schedule.eventList().sort(new ScheduleSorters.SortEventsByTrialStationAgegroup());
        schedule.eventList().forEach(event -> {
            int last_booked;
            int new_booked;
            if (!stationTimes.containsKey(event.station())) {
                new_booked = 1;
            } else {
                last_booked = stationTimes.get(event.station());
                new_booked = last_booked + 2;
            }
            stationTimes.put(event.station(), new_booked);
            int duration = calculateEventDuration(event);
            List<Integer> time_slots = new ArrayList<>(duration);
            for (int i = new_booked; i < duration + new_booked; i++)
                time_slots.add(i);
            event.assignTimeSlots(time_slots);
        });

        System.out.println(schedule);
    }
}