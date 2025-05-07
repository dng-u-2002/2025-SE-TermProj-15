package yoot;

import java.util.*;

public class Turn {
    private int turnNumber;
    private Player currentPlayer;
    private List<YutResult> results;

    public Turn(List<Player> players) {
        this.turnNumber = 0;
        this.currentPlayer = players.get(0);
        this.results = new ArrayList<>();
    }

    public void nextTurn(List<Player> players) {
        turnNumber++;
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        currentPlayer = players.get(nextIndex);
        results.clear();

        System.out.println("다음 턴입니다. 현재 플레이어: " + currentPlayer.getName());
    }

    public boolean shouldEnd() {
        for (YutResult result : results) {
            if (!result.isUsed()) {
                return false;
            }
        }
        return true;
    }

    public void setResults(List<YutResult> results) {
        this.results = results;
    }

    public List<YutResult> getResults() {
        return results;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
