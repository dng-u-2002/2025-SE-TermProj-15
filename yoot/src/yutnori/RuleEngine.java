package yutnori;

import java.util.List;

public class RuleEngine {

    /**
     * 두 말이 업기 가능한지 확인한다.
     */
    public boolean canGroup(Piece p1, Piece p2) {
        return p1.getOwner().equals(p2.getOwner())
                && !p1.isFinished()
                && !p2.isFinished()
                && !p1.equals(p2);
    }

    /**
     * 말이 주어진 윷 결과로 상대 말을 잡을 수 있는지 판단한다.
     * 판단은 외부에서 현재 Board와 YutResult를 함께 넘겨주는 방식으로 설계.
     */
    public boolean canCapture(Piece attacker, YutResult result, Board board) {
        Location current = attacker.getLocation();
        List<Location> nextLocations = board.getNextLocations(current, result);

        for (Location loc : nextLocations) {
            for (Piece target : loc.getPiecesOnThisLocation()) {
                if (!target.getOwner().equals(attacker.getOwner())) {
                    System.out.println(attacker + "이(가) " + target + "을(를) 잡을 수 있습니다!");
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 게임이 끝났는지 판단. 한 명이라도 완주하지 못했으면 false.
     */
    public boolean isGameFinished(List<Player> players) {
        for (Player player : players) {
            if (!player.hasWon()) return false;
        }
        return true;
    }
}
