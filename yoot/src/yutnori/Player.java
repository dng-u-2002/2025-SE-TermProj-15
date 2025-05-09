package yutnori;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String name;
    private List<Piece> pieces;

    public Player(int id, String name, int pieceCount, Location startLocation) {
        this.id = id;
        this.name = name;
        this.pieces = new ArrayList<>();

        for (int i = 1; i <= pieceCount; i++) {
            Piece piece = new Piece(this, i, startLocation);
            pieces.add(piece);
            startLocation.addPiece(piece);
        }

        System.out.println(name + "의 말 " + pieceCount + "개가 초기화되었습니다.");
    }

    public boolean hasWon() {
        for (Piece p : pieces) {
            if (!p.isFinished()) return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        return "플레이어 " + name + " (ID: " + id + ")";
    }
}
