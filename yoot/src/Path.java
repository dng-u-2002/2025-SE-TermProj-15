package yoot;

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
}
