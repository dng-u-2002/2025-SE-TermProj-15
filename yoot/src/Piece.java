package yoot;

public class Piece {
    private String id;
    private Player owner;
    private Location location;
    private boolean isGrouped;
    private boolean isFinished;
    private Group group;

    public Piece(String id, Player owner) {
        this.id = id;
        this.owner = owner;
        this.location = null;
        this.isGrouped = false;
        this.isFinished = false;
        this.group = null;
    }

    public void move(YutResult result, Board board, RuleEngine ruleEngine) {
        if (isFinished) return;

        Location current = (location == null) ? board.getStartLocation() : location;
        Location next = board.getNextLocation(current, result);

        if (next == null) {
            System.out.println(id + "은(는) 이동할 수 없습니다.");
            return;
        }

        if (next.isEnd()) {
            isFinished = true;
            location = null;
            System.out.println(owner.getName() + "의 말 " + id + "이(가) 도착지점에 도달했습니다!");
        } else {
            if (location != null) location.removePiece(this);
            next.addPiece(this);
            location = next;
            System.out.println(owner.getName() + "의 말 " + id + "이(가) " + next.getId() + "로 이동했습니다.");
        }

        // 잡기/업기 등의 규칙 적용은 RuleEngine에 위임
        ruleEngine.checkCaptureOrGroup(this, next);
    }

    public void groupWith(Piece other) {
        if (this.group == null) {
            this.group = new Group();
            this.group.addPiece(this);
        }
        this.group.addPiece(other);
        this.isGrouped = true;
        other.isGrouped = true;
        other.group = this.group;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public String getId() {
        return id;
    }

    public Player getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isGrouped() {
        return isGrouped;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setFinished(boolean finished) {
        this.isFinished = finished;
    }
}
