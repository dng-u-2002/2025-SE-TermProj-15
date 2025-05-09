package yutnori;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String id;
    private boolean isCenter;
    private boolean isEnd;
    private boolean isStart;
    private List<Piece> piecesOnThisLocation;

    public Location(String id, boolean isCenter, boolean isEnd, boolean isStart) {
        this.id = id;
        this.isCenter = isCenter;
        this.isEnd = isEnd;
        this.isStart = isStart;
        this.piecesOnThisLocation = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean isStart() {
        return isStart;
    }

    public void addPiece(Piece piece) {
        piecesOnThisLocation.add(piece);
    }

    public void removePiece(Piece piece) {
        piecesOnThisLocation.remove(piece);
    }

    public List<Piece> getPiecesOnThisLocation() {
        return piecesOnThisLocation;
    }

    public List<Piece> getPiecesByPlayer(Player player) {
        List<Piece> result = new ArrayList<>();
        for (Piece p : piecesOnThisLocation) {
            if (p.getOwner().equals(player)) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "위치 " + id;
    }

}
