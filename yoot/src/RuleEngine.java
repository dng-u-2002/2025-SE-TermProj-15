package yoot;

import java.util.*;

public class RuleEngine {

    public boolean canGroup(Piece p1, Piece p2) {
        // 같은 위치, 같은 플레이어, 둘 다 완주하지 않았을 때만 가능
        return p1.getOwner().equals(p2.getOwner())
                && p1.getLocation() != null
                && p1.getLocation().equals(p2.getLocation())
                && !p1.isFinished() && !p2.isFinished();
    }

    public boolean canCapture(Piece attacker, Piece target) {
        // 다른 플레이어의 말이 같은 위치에 있을 때
        return !attacker.getOwner().equals(target.getOwner())
                && attacker.getLocation() != null
                && attacker.getLocation().equals(target.getLocation());
    }

    public void checkCaptureOrGroup(Piece movedPiece, Location location) {
        List<Piece> others = location.getPiecesOnThisLocation();

        for (Piece other : others) {
            if (other == movedPiece) continue;

            if (canCapture(movedPiece, other)) {
                capture(other);
                System.out.println(movedPiece.getOwner().getName() + "의 말이 " +
                        other.getOwner().getName() + "의 말을 잡았습니다!");
                break; // 한 번만 잡을 수 있음
            }

            if (canGroup(movedPiece, other)) {
                movedPiece.groupWith(other);
                System.out.println(movedPiece.getOwner().getName() + "의 말이 " +
                        other.getId() + "과 업어졌습니다!");
            }
        }
    }

    private void capture(Piece target) {
        // 잡힌 말은 시작 위치로 보내고 그룹 해체
        target.setLocation(null);
        target.setFinished(false);
        if (target.getGroup() != null) {
            target.getGroup().disbandByForce();
        }
    }

    public boolean isGameFinished(List<Player> players) {
        for (Player player : players) {
            if (player.hasWon()) {
                return true;
            }
        }
        return false;
    }
}
