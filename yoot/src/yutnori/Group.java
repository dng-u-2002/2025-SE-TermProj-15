package yutnori;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Piece> pieces;

    public Group() {
        this.pieces = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        if (!pieces.contains(piece)) {
            pieces.add(piece);
            piece.setGroup(this);
            System.out.println(piece + "이(가) 그룹에 추가되었습니다.");
        }
    }

    public void disbandByForce() {
        System.out.println("그룹이 강제로 해체됩니다.");
        for (Piece piece : pieces) {
            piece.setGroup(null);
        }
        pieces.clear();
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        return "그룹(" + pieces.size() + "개 말 포함)";
    }
}
