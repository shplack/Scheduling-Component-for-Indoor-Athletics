package com.SCIA;

import com.SCIA.CSV.CSV;
import com.SCIA.Competitions.CompetitionGroup;
import com.SCIA.Competitions.CompetitionGroupsMaker;

import java.io.IOException;
import java.util.List;

public class TestCompetitionGroupsMaker {
    public static void main(String[] args) throws IOException {
        CSV csv = new CSV(args[0]);
        List<CompetitionGroup> competitionGroupList = CompetitionGroupsMaker.makeCompetitionGroups(csv.getRecords());
        for (CompetitionGroup competitionGroup : competitionGroupList) {
            System.out.println(competitionGroup.toString() + "\n");
        }
    }
}
