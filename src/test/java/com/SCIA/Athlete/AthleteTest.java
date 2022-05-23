package com.SCIA.Athlete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class AthleteTest {

    Athlete athlete;
    Athlete copy;

    @BeforeEach
    void setUp() {
        athlete = new Athlete(1, "ÖFK", "Alexander", "Hedberg", GenderGroup.MALE, 29);
        copy = athlete.deepCopy();
    }

    @Test
    void deepCopy() {
        assertEquals(athlete, copy);
    }

    @Test
    void equals() {
        assertEquals(athlete.club(), copy.club());
        assertEquals(athlete.name(), copy.name());
        assertEquals(athlete.surname(), copy.surname());
        assertSame(athlete.gender(), copy.gender());
        assertEquals(athlete.age(), copy.age());
    }
}