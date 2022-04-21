package Discipline;

public class Running extends Discipline {
    private final int distance;
    private String best_time;

    public Running(String name, int distance, String best_time) {
        super(name);
        this.distance = distance;
        this.best_time = best_time;
    }

    @Override
    public String getPersonalBest() {
        return best_time;
    }

    public int getDistance() {
        return distance;
    }

    public String getBest_time() {
        return best_time;
    }

    public void setBest_time(String best_time) {
        this.best_time = best_time;
    }
}
