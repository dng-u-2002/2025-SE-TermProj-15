import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ButtonBar;

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

        // 사용자 정의 버튼
        ButtonType randomType = new ButtonType("랜덤");
        ButtonType manualType = new ButtonType("수동");

        alert.getButtonTypes().setAll(randomType, manualType);

        Optional<ButtonType> result = alert.showAndWait();
        
        // 사용자가 수동 선택을 했을 경우 수동 윷 선택 대화상자 표시
        if (result.isPresent() && result.get() == manualType) {
            showManualYutSelectionDialog();
        }
        
        // 랜덤 선택 여부 반환 (true: 랜덤, false: 수동)
        return result.isPresent() && result.get() == randomType;
    }

    // 수동 윷 선택 대화상자 (별도의 메소드로 분리)
    private void showManualYutSelectionDialog() {
        Alert manualAlert = new Alert(Alert.AlertType.CONFIRMATION);
        manualAlert.setTitle("윷 수동 선택");
        manualAlert.setHeaderText("원하는 윷 결과를 선택하세요");
        
        // 도개걸윷모 버튼 생성
        ButtonType backDoType = new ButtonType("빽도");
        ButtonType doType = new ButtonType("도");
        ButtonType gaeType = new ButtonType("개");
        ButtonType gulType = new ButtonType("걸");
        ButtonType yutType = new ButtonType("윷");
        ButtonType moType = new ButtonType("모");
        ButtonType cancelType = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        // 대화상자에 버튼 추가
        manualAlert.getButtonTypes().setAll(
            backDoType, doType, gaeType, gulType, yutType, moType, cancelType
        );
        
        // 대화상자 표시 및 결과 처리
        Optional<ButtonType> result = manualAlert.showAndWait();
        
        // YutThrower.throwManual() 메소드 수정
        if (result.isPresent()) {
            ButtonType selectedButton = result.get();
            YutResultType selectedType = null;
            
            if (selectedButton == backDoType) {
                selectedType = YutResultType.BACK_DO;
            } else if (selectedButton == doType) {
                selectedType = YutResultType.DO;
            } else if (selectedButton == gaeType) {
                selectedType = YutResultType.GAE;
            } else if (selectedButton == gulType) {
                selectedType = YutResultType.GEOL;
            } else if (selectedButton == yutType) {
                selectedType = YutResultType.YUT;
            } else if (selectedButton == moType) {
                selectedType = YutResultType.MO;
            }
            
            // selectedType이 null이 아니면 YutThrower 클래스의 정적 변수에 저장
            if (selectedType != null) {
                YutThrower.selectedManualResult = selectedType;
            }
        }
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