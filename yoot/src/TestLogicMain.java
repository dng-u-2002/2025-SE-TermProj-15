package yoot;

import java.util.*;

public class TestLogicMain {
    public static void main(String[] args) {
        Game game = new Game();
        game.startGame(2, 2, BoardType.RECTANGLE);

        // Player_1의 턴: [모], [도]
        List<YutResult> turn1 = Arrays.asList(
                new YutResult(YutResultType.MO),
                new YutResult(YutResultType.DO)
        );
        game.chooseNextResult(turn1);
        game.proceedTurn();  // [모]
        game.proceedTurn();  // [도]

        // Player_2의 턴: [빽도]
        List<YutResult> turn2 = Arrays.asList(
                new YutResult(YutResultType.BACKDO)
        );
        game.chooseNextResult(turn2);
        game.proceedTurn();  // [빽도]
    }
}
