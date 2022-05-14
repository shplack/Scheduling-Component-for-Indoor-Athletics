package Athlete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AthleteTest {

    Athlete athlete;
    Athlete copy;

    @BeforeEach
    void setUp() {
        athlete = new Athlete(1, "ÖFK", "Alexander", "Hedberg", Gender.MALE, 29);
        copy = athlete.deepCopy();
    }

    @Test
    void deepCopy() {
        assertTrue(athlete.equals(copy));
    }

    @Test
    void equals() {
        assertTrue(athlete.club().equals(copy.club()));
        assertTrue(athlete.name().equals(copy.name()));
        assertTrue(athlete.surname().equals(copy.surname()));
        assertTrue(athlete.gender() == copy.gender());
        assertTrue(athlete.age() == copy.age());
    }
}