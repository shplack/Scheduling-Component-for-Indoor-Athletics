package com.SCIA.SimulatedAnneling;

public class Probability {
    public static double getProbability(double sigma, int conflicts)    {
        return Math.exp(conflicts/sigma);
    }
}
