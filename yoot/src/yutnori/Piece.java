package yutnori;

public class Piece {
    private Player owner;
    private int id;
    private Location location;
    private boolean isGrouped;
    private boolean isFinished;
    private Group group;
    private boolean eligibleForBranch = false;  // 분기 가능 여부

    public Piece(Player owner, int id, Location startLocation) {
        this.owner = owner;
        this.id = id;
        this.location = startLocation;
        this.isGrouped = false;
        this.isFinished = false;
        this.group = null;
    }

    public void moveTo(Location newLocation) {
        if (this.location != null) {
            this.location.removePiece(this);
        }
        this.location = newLocation;
        newLocation.addPiece(this);
        System.out.println(owner.getName() + "의 말 " + id + "이(가) " + newLocation.getId() + "로 이동했습니다.");
    }

    public void groupWith(Piece other) {
        if (this.group == null && other.group == null) {
            Group newGroup = new Group();
            newGroup.addPiece(this);
            newGroup.addPiece(other);
            this.group = newGroup;
            other.group = newGroup;
            this.isGrouped = true;
            other.isGrouped = true;
        }
    }

    // Getter / Setter
    public Player getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isGrouped() {
        return isGrouped;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        this.isGrouped = (group != null);
    }

    // 🔽 분기 조건 관련 추가 메서드
    public boolean isEligibleForBranch() {
        return eligibleForBranch;
    }

    public void setEligibleForBranch(boolean eligibleForBranch) {
        this.eligibleForBranch = eligibleForBranch;
    }

    @Override
    public String toString() {
        return owner.getName() + "의 말 " + id +
                (isFinished ? " (완주)" : "") +
                (location != null ? " @ " + location.getId() : "");
    }
}
