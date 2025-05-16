public class YutResult {
    private YutResultType type;
    private int value;
    private boolean isUsed;

    public YutResult(YutResultType type) {
        this.type = type;
        this.value = type.getValue();
        this.isUsed = false;
    }

    public YutResultType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public String toString() {
        return type.getDisplayName() + (isUsed ? " (사용됨)" : " (미사용)");
    }
}
