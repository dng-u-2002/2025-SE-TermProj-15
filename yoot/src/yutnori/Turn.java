package yutnori;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private int turnNumber;
    private Player currentPlayer;
    private List<YutResult> results;

    public Turn(Player startingPlayer) {
        this.turnNumber = 1;
        this.currentPlayer = startingPlayer;
        this.results = new ArrayList<>();
    }

    public void nextTurn(List<Player> players) {
        int index = players.indexOf(currentPlayer);
        int nextIndex = (index + 1) % players.size();
        this.currentPlayer = players.get(nextIndex);
        this.turnNumber++;
        this.results.clear();

        System.out.println("==== 턴 " + turnNumber + " ====");
        System.out.println("현재 플레이어: " + currentPlayer.getName());
    }

    public boolean shouldEnd() {
        for (YutResult result : results) {
            if (!result.isUsed()) {
                return false;
            }
        }
        return true;
    }

    public void addResult(YutResult result) {
        results.add(result);
    }

    // Getter
    public int getTurnNumber() {
        return turnNumber;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<YutResult> getResults() {
        return results;
    }
}
