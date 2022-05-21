package com.SCIA.SimulatedAnneling;

public class Probability {
    public static double getProbability(double sigma, int conflicts)    {
        double probability = Math.exp(-conflicts/sigma);
        return probability;
    }
}
