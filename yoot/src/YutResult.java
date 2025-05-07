package yoot;

public class YutResult {
    private YutResultType type;
    private boolean isUsed;

    public YutResult(YutResultType type) {
        this.type = type;
        this.isUsed = false;
    }

    public YutResultType getType() {
        return type;
    }

    public int getValue() {
        return type.getValue();
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public String toString() {
        return "[" + type.getLabel() + "]" + (isUsed ? " (사용됨)" : "");
    }
}
