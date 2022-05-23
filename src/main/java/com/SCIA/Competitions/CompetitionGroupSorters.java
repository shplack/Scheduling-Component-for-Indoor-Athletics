package com.SCIA.Competitions;

import java.util.Comparator;

public class CompetitionGroupSorters {
    /**
     * Sort competition group by discipline, age group, and gender respectively.
     */
    public static class DisciplineAgeGroupGender implements Comparator<CompetitionGroup> {

        @Override
        public int compare(CompetitionGroup cg1, CompetitionGroup cg2) {
            if (cg1.discipline() == cg2.discipline()) {
                if (cg1.age_group() == cg2.age_group()) {
                    if (cg1.gender() == cg2.gender()) {
                        return 0;
                    }
                    return cg1.gender().ordinal() - cg2.gender().ordinal();
                }
                return cg1.age_group().ordinal() - cg2.age_group().ordinal();
            }
            return cg1.discipline().ordinal() - cg2.discipline().ordinal();
        }
    }
}
