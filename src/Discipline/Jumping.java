package Discipline;

public class Jumping extends Discipline {
    private float best_distance;

    public Jumping(String name, float best_distance) {
        super(name);
        this.best_distance = best_distance;
    }

    @Override
    public String getPersonalBest() {
        return String.valueOf(best_distance);
    }

    public float getBest_distance() {
        return best_distance;
    }

    public void setBest_distance(float best_distance) {
        this.best_distance = best_distance;
    }
}
