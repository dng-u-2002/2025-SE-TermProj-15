package yutnori;

public class Path {
    private Location from;
    private Location to;
    private int stepCount;

    public Path(Location from, Location to, int stepCount) {
        this.from = from;
        this.to = to;
        this.stepCount = stepCount;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public int getStepCount() {
        return stepCount;
    }

    @Override
    public String toString() {
        return from.getId() + " --(" + stepCount + "칸)--> " + to.getId();
    }
}
