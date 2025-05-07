package yoot;

import java.util.*;

public class Game {
    private List<Player> players;
    private Board board;
    private Turn turn;
    private RuleEngine ruleEngine;

    public void startGame(int numPlayers, int piecesPerPlayer, BoardType boardType) {
        System.out.println("게임을 시작합니다.");

        players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            Player player = new Player(i, "Player_" + i, piecesPerPlayer);
            players.add(player);
        }

        board = new Board(boardType);
        ruleEngine = new RuleEngine();
        turn = new Turn(players);

        System.out.println("플레이어 수: " + numPlayers + ", 말 개수: " + piecesPerPlayer);
        System.out.println("판 형태: " + boardType.name());
        System.out.println("게임 초기화 완료. Player_1부터 시작합니다.");
    }

    public void chooseNextResult(List<YutResult> results) {
        turn.setResults(results);
    }

    public void proceedTurn() {
        Player currentPlayer = turn.getCurrentPlayer();
        System.out.println(currentPlayer.getName() + "의 턴입니다.");

        for (YutResult result : turn.getResults()) {
            if (!result.isUsed()) {
                System.out.println("[" + result.getType().getLabel() + "]가 나왔습니다.");
                currentPlayer.takeAction(result, board, ruleEngine);
                result.setUsed(true);
                break;
            }
        }

        if (ruleEngine.isGameFinished(players)) {
            checkWinner();
        } else if (turn.shouldEnd()) {
            turn.nextTurn(players);
        }
    }

    public void checkWinner() {
        for (Player player : players) {
            if (player.hasWon()) {
                System.out.println(player.getName() + "님이 승리하셨습니다!");
                return;
            }
        }
    }

    public void restartGame() {
        System.out.println("게임을 재시작합니다.");
        startGame(players.size(), players.get(0).getPieceCount(), board.getBoardType());
    }

    // getter methods
    public List<Player> getPlayers() { return players; }
    public Board getBoard() { return board; }
    public Turn getTurn() { return turn; }
    public RuleEngine getRuleEngine() { return ruleEngine; }
}
