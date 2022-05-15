package Competitions;

public class AgeGroups {

    public enum AgeGroup {
        EIGHT_AND_UNDER,
        NINE_TO_TEN,
        ELEVEN_TO_TWELVE,
        THIRTEEN_TO_FOURTEEN,
        FIFTEEN_TO_SIXTEEN,
        SEVENTEEN_TO_EIGHTEEN,
        NINETEEN_AND_OVER
    }

    public static AgeGroup getAgeGroup(int age) {
        if (age <= 8) {
            return AgeGroup.EIGHT_AND_UNDER;
        } else if (age <= 10) {
            return AgeGroup.NINE_TO_TEN;
        } else if (age <= 12) {
            return AgeGroup.ELEVEN_TO_TWELVE;
        } else if (age <= 14) {
            return AgeGroup.THIRTEEN_TO_FOURTEEN;
        } else if (age <= 16) {
            return AgeGroup.FIFTEEN_TO_SIXTEEN;
        } else if (age <= 18) {
            return AgeGroup.SEVENTEEN_TO_EIGHTEEN;
        } else {
            return AgeGroup.NINETEEN_AND_OVER;
        }
    }

    public static boolean unisexGroup(AgeGroup ageGroup) {
        return switch(ageGroup) {
            case EIGHT_AND_UNDER, NINE_TO_TEN, ELEVEN_TO_TWELVE -> true;
            default -> false;
        };
    }

}