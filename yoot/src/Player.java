package yoot;

import java.util.*;

public class Player {
    private int id;
    private String name;
    private List<Piece> pieces;

    public Player(int id, String name, int pieceCount) {
        this.id = id;
        this.name = name;
        this.pieces = new ArrayList<>();
        for (int i = 0; i < pieceCount; i++) {
            pieces.add(new Piece(id + "_" + i, this));
        }
    }

    public boolean hasWon() {
        for (Piece piece : pieces) {
            if (!piece.isFinished()) return false;
        }
        return true;
    }

    public void takeAction(YutResult result, Board board, RuleEngine ruleEngine) {
        // 예시: 가장 먼저 움직일 수 있는 말을 선택
        for (Piece piece : pieces) {
            if (!piece.isFinished()) {
                piece.move(result, board, ruleEngine);
                break;
            }
        }
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public List<Piece> getPieces() { return pieces; }

    public int getPieceCount() { return pieces.size(); }
}
