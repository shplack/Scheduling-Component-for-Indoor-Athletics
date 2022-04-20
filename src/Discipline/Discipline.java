package Discipline;

public abstract class Discipline {
    private final String name;

    protected Discipline(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract public String getPersonalBest();
}