package yoot;

import java.util.*;

public class Location {
    private String id;
    private boolean isCenter;
    private boolean isEnd;
    private List<Piece> piecesOnThisLocation;

    public Location(String id, boolean isCenter, boolean isEnd) {
        this.id = id;
        this.isCenter = isCenter;
        this.isEnd = isEnd;
        this.piecesOnThisLocation = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        piecesOnThisLocation.add(piece);
        piece.setLocation(this);
    }

    public void removePiece(Piece piece) {
        piecesOnThisLocation.remove(piece);
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

    public String getId() {
        return id;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public List<Piece> getPiecesOnThisLocation() {
        return piecesOnThisLocation;
    }
}
