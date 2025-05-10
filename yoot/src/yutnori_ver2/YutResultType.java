package yutnori_ver2;

public enum YutResultType {
    BACK_DO(-1, "빽도", false),
    DO(1, "도", false),
    GAE(2, "개", false),
    GEOL(3, "걸", false),
    YUT(4, "윷", true),
    MO(5, "모", true);

    private final int value;
    private final String displayName;
    private final boolean extraTurn;

    YutResultType(int value, String displayName, boolean extraTurn) {
        this.value = value;
        this.displayName = displayName;
        this.extraTurn = extraTurn;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean hasExtraTurn() {
        return extraTurn;
    }
}
