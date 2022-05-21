package com.SCIA.SimulatedAnneling;

import com.SCIA.Athlete.AthleteRecord;
import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.CompetitionGroupsMaker;
import com.SCIA.Schedule.Event.Event;
import com.SCIA.Schedule.Schedule;
import com.SCIA.Schedule.ScheduleMaker;
import com.SCIA.Schedule.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulatedAnnealing {
    Schedule copySchedule;

    public static Schedule simulatedAnnealingFunction() throws IOException {
        int newConflicts = 10;
        int oldConflicts = 10;
        Schedule copySchedule;
        CSV csv = new CSV("registration-list.csv");
        ArrayList<AthleteRecord> athlete_records = csv.getRecords();
        ArrayList<CompetitionGroup> competition_groups = CompetitionGroupsMaker.makeCompetitionGroups(athlete_records);
        Schedule schedule = ScheduleMaker.makeSchedule(competition_groups);

        double heat = InitialHeat.initialHeat(schedule.eventList());
        double coolingFactor = 0.999;
        int i = 0;

        while (true) {
            copySchedule = schedule.deepCopy();
            List<Event> mutatedList = Mutation.MutationFunction(copySchedule.eventList());


            System.out.println("New conflics: " + newConflicts + " Old conflicts" + oldConflicts);
            if (newConflicts == 0)
            {
                schedule = copySchedule;
                System.out.println(newConflicts);
                break;
                //return schedule;
            }
            int score = newConflicts - oldConflicts;
            double probability = Probability.getProbability(heat, score);
            double random = Math.random()*(1);

            if(i == 20) {
                newConflicts = Judgement.getConflicts(mutatedList);
                oldConflicts = Judgement.getConflicts(schedule.eventList());
                i = 0;

                if (newConflicts < oldConflicts)
                    schedule = copySchedule;
                else {
                    if (random < probability)
                        schedule = copySchedule;
                }
                heat = heat * coolingFactor;
                System.out.println("This is heat: " + heat);
            }

            i++;
        }
        return schedule;
    }


}
