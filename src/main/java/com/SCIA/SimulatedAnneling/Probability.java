package com.SCIA.SimulatedAnneling;

public class Probability {
    public static double getProbability(float sigma, int conflicts)    {
        double probability = Math.exp(-conflicts/sigma);
        return probability;
    }
}
