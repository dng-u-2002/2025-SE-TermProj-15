import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.List;
import java.util.Optional;

public class FXGameUI {
    private Game gameController;
    private FXYutScreen yutScreen;
    private FXBoardPanel boardPanel;

    public FXGameUI(Game gameController, int numPlayers, int piecesPerPlayer, int boardType, javafx.stage.Stage stage) {
        this.gameController = gameController;

        // YutScreen 생성 및 참조 저장
        this.yutScreen = new FXYutScreen(numPlayers, piecesPerPlayer, boardType, gameController, this, stage);
        this.boardPanel = yutScreen.getBoard();

        // BoardPanel에 게임 컨트롤러 참조 설정
        boardPanel.setGameController(gameController);

        // 초기 상태 메시지 설정
        updateGameStatus("플레이어 1의 차례입니다. 윷을 던지세요.");
    }

    public void updateGameStatus(String message) {
        yutScreen.updateStatusMessage(message);
    }

    public void displayYutResult(String result) {
        yutScreen.displayYutResult(result);
    }

    public void selectPiece(int position) {
        boardPanel.selectPiece(position);
    }

    public void setPossibleMoveLocations(List<Integer> locations) {
        boardPanel.setPossibleMoveLocations(locations);
    }

    public void clearPossibleMoves() {
        boardPanel.clearPossibleMoves();
    }

    public void setCurrentPlayer(int playerId) {
        boardPanel.setCurrentPlayer(playerId);
    }

    public void updateBoard(List<Player> players, int boardType) {
        boardPanel.clearPieces();

        for (Player player : players) {
            for (int i = 0; i < boardType * 100; i++) {
                if (player.pieceIndex[i] > 0) {
                    boardPanel.addPiece(i, player.getId(), player.pieceIndex[i]);
                }
            }
        }
    }

    public boolean showYutThrowDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("윷 던지기");
        alert.setHeaderText("윷을 어떻게 던지시겠습니까?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    public void showCatchMessage(int playerId) {
        showInfoDialog("말 잡기", "플레이어 " + playerId + "가 상대방 말을 잡았습니다! 추가 턴이 주어집니다.");
    }

    public void showExtraTurnMessage(String resultName) {
        showInfoDialog("추가 턴", resultName + "이(가) 나와 한 번 더 던집니다!");
    }

    public void showBackDoSkipMessage() {
        showInfoDialog("상대방 턴", "빽도(가) 나와 건너뜁니다!");
    }

    public String showFinishSelectDialog(List<String> options) {
        if (options.isEmpty()) return null;
        return options.get(0); // 기본적으로 첫 번째 옵션 선택
    }

    public void showVictoryMessage(int playerId) {
        showInfoDialog("게임 종료", "플레이어 " + playerId + "가 승리했습니다!");
    }

    public boolean showRestartDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("재시작");
        alert.setHeaderText("게임을 다시 시작하시겠습니까?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void closeWindow() {
        yutScreen.close();
    }

    public FXYutScreen getYutScreen() {
        return yutScreen;
    }

    private void showInfoDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
