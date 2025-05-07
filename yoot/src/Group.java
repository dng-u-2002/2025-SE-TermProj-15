package yoot;

import java.util.*;

public class Group {
    private List<Piece> pieces;

    public Group() {
        this.pieces = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        if (!pieces.contains(piece)) {
            pieces.add(piece);
            piece.setGroup(this);
            piece.setGrouped(true);
        }
    }

    public void disbandByForce() {
        for (Piece piece : pieces) {
            piece.setGroup(null);
            piece.setGrouped(false);
        }
        pieces.clear();
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
