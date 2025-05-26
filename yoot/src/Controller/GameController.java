package Controller;

import Model.Player;
import View.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class GameController {
    private static GameController gameController;
    private Game game;
    private YutScreen yutScreen;
    private Player player;
    private BoardPanel boardPanel;
    private ActionListener throwButtonListener;

    private GameController() {
        createStartFrame();
        createYutScreen();
    }

    // GameController의 인스턴스를 하나로 유지하는 singleton 패턴 구현
    public static GameController getInstance() {
        if (gameController == null) {
            gameController = new GameController();
        }
        return gameController;
    }

    private void createStartFrame() {
        StartFrame startFrame = new StartFrame();
        startFrame.setVisible(true);
    }

    private void createYutScreen() {
        this.yutScreen = new YutScreen(game.getNumPlayers(), game.getPiecesPerPlayer(), game.getBoardType());
        this.boardPanel = yutScreen.getBoard();
        this.yutScreen.setThrowButtonListener(e -> handleThrow());
        this.boardPanel.setClickListener(point -> handleBoardClick(point));
    }

    public void setGame(int numPlayers, int piecesPerPlayer, int boardTypeInt) {
        this.game = new Game(numPlayers, piecesPerPlayer, boardTypeInt);
    }

    public void handleThrow(){

    }

    public void handleBoardClick(Point mousePoint) {
        int pos = findBoardPosition(mousePoint);

        switch (game.getCurrentState()) {

            case WAITING_FOR_PIECE_SELECTION:
                if(pos == -2) {
                    //시작지점 말 선택
                    Player currentPlayer = game.getCurrentPlayer();
                    if (currentPlayer != null && currentPlayer.pieceAtStart > 0) {
                        // 시작 지점 말 선택
                        game.pieceSelected(999); // 999는 시작 지점을 나타내는 특별한 값
                        return;
                    }

                    // 말 선택 모드: 현재 플레이어의 말이 있는 위치만 선택 가능
                    if (pieces.containsKey(i) && pieces.get(i).containsKey(currentPlayerId)) {
                        selectedPosition = i;
                        game.pieceSelected(i);
                    }
                }
                else {

                    // 말 선택 모드: 현재 플레이어의 말이 있는 위치만 선택 가능
                    if (pieces.containsKey(i) && pieces.get(i).containsKey(currentPlayerId)) {
                        selectedPosition = i;
                        game.pieceSelected(i);
                    }

                }
                break;
            case WAITING_FOR_MOVE_SELECTION:
                if (possibleMoveLocations.contains(pos)) {
                    game.moveLocationSelected(pos);
                    game.clearPossibleMoves();
                }
                break;
        }
        boardPanel.repaint();
    }

    public int findBoardPosition(Point mouse) {
        int threshold = 17;
        int index;
        int pointN = boardPanel.getPoint();
        if(40 <= mouse.x && mouse.x <= 150 && 40 <= mouse.y && mouse.y <= 90) {
            return -2;
        }
        for (index = 0; index < pointN * 100; index++) {
            Point indexPoint = boardPanel.getPointofIndex(index);
            double distance = indexPoint.distance(mouse);
            if (distance < threshold) return index;
        }
        return -1;
    }
}

