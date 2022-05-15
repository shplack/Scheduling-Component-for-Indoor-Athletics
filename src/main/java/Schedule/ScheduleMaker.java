package Schedule;

import Athlete.Athlete;
import Athlete.AthleteRecord;
import Competitions.CompetitionGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Competitions.Trials.Trial;
import static Competitions.Trials.getTrials;
import static Discipline.DisciplineStation.getStation;
import static Discipline.Disciplines.Discipline;
import static Discipline.Stations.Station;
import static Discipline.Stations.getAthleteLimit;

public class ScheduleMaker {
    public static Schedule makeSchedule(List<CompetitionGroup> competitionGroups) {
        List<Event> eventList = new ArrayList<>();

        for (CompetitionGroup competitionGroup : competitionGroups) {
            Discipline discipline = competitionGroup.discipline();
            Station[] stations = getStation(discipline);

            List<AthleteRecord> sortedAthleteRecords = new ArrayList<>(competitionGroup.athleteRecordsList());
            sortedAthleteRecords.sort(Comparator.comparing(ar -> ar.getDisciplineRecords().getBestRecord(discipline)));

            int athlete_limit = getAthleteLimit(discipline);
            Trial[] trials = getTrials(discipline);

            for (int i = 0; i < trials.length; i++) {
                Trial trial = trials[i];
//                ArrayList<ArrayList<Athlete>> athlete_groups = new ArrayList<>();
                int num_groups = (int) Math.ceil((double) sortedAthleteRecords.size() / athlete_limit);
                int remainder = athlete_limit * num_groups - sortedAthleteRecords.size();
                int group_num = 0;

                outer_loop:
                for (int j = 0, group_size; j < num_groups; j += group_size, group_num++) {
                    int[] time_slots;
                    int max_time = 0;
                    ArrayList<Athlete> athletes = new ArrayList<>();
                    group_size = athlete_limit;
                    if (remainder > 0 && group_num < remainder)
                        group_size--;

                    for (int k = j; k < j + group_size; k++) {
                        if (k >= sortedAthleteRecords.size())
                            break outer_loop;
                        AthleteRecord athleteRecord = sortedAthleteRecords.get(k);
                        if (discipline.isMeasuredInTime()) {
                            float worst_time = athleteRecord.getDisciplineRecords().getWorstRecord(discipline);
                            max_time = Math.max(max_time, (int) (worst_time * 60 * TimeSlot.INCREMENT));
                        }
                        athletes.add(athleteRecord.getAthlete());
                    }

//                    athlete_groups.add(athletes);
                    int num_time_slots = (int) Math.ceil((double) max_time / TimeSlot.INCREMENT);
                    time_slots = new int[num_time_slots];
                    for (int k = 0; k < num_time_slots; k++)
                        time_slots[k] = k;

                    Event event = new Event(time_slots, athletes, stations[i % stations.length], discipline, trial);
                    eventList.add(event);
                }

            }

        }

        return new Schedule(eventList);
    }
}
