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

    // 윷 결과를 반환하고 추가 턴이 필요한지 여부를 알려주는 메소드
    public YutThrowResult throwYutWithDialog(boolean isFirstThrow) {
        // 윷 던지기 방식 선택 (첫 번째 던지기일 때만)
        boolean isRandom;
        if (isFirstThrow) {
            Alert choiceAlert = new Alert(Alert.AlertType.CONFIRMATION);
            choiceAlert.setTitle("윷 던지기");
            choiceAlert.setHeaderText("윷을 어떻게 던지시겠습니까?");

            ButtonType randomType = new ButtonType("랜덤");
            ButtonType manualType = new ButtonType("수동");

            choiceAlert.getButtonTypes().setAll(randomType, manualType);

            Optional<ButtonType> choiceResult = choiceAlert.showAndWait();
            isRandom = choiceResult.isPresent() && choiceResult.get() == randomType;
        } else {
            // 추가 턴일 경우 이전과 동일한 방식으로 던지기
            isRandom = lastThrowWasRandom;
        }
        
        // 마지막 던지기 방식 저장
        lastThrowWasRandom = isRandom;

        // 윷 결과 가져오기
        YutResult result;
        if (isRandom) {
            result = YutThrower.throwRandom();
        } else {
            // 수동 선택 대화상자 표시
            result = showManualYutSelectionDialog();
        }

        // 결과가 null이면 (취소 시) 임시로 DO 반환
        if (result == null) {
            result = new YutResult(YutResultType.DO);
        }

        // 결과 표시
        displayYutResult(result.getType().getDisplayName());

        // 추가 턴이 있는 경우
        boolean hasExtraTurn = result.getType().hasExtraTurn();
        if (hasExtraTurn) {
            showExtraTurnMessage(result.getType().getDisplayName());
        }

        // 결과와 추가 턴 여부 반환
        return new YutThrowResult(result, hasExtraTurn);
    }

    // 수동 윷 선택 대화창 (YutResult 반환)
    private YutResult showManualYutSelectionDialog() {
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
        
        // 대화창에 버튼 추가
        manualAlert.getButtonTypes().setAll(
            backDoType, doType, gaeType, gulType, yutType, moType, cancelType
        );
        
        // 대화창 표시 및 결과 처리
        Optional<ButtonType> result = manualAlert.showAndWait();
        
        if (result.isPresent()) {
            ButtonType selectedButton = result.get();
            
            if (selectedButton == backDoType) {
                return new YutResult(YutResultType.BACK_DO);
            } else if (selectedButton == doType) {
                return new YutResult(YutResultType.DO);
            } else if (selectedButton == gaeType) {
                return new YutResult(YutResultType.GAE);
            } else if (selectedButton == gulType) {
                return new YutResult(YutResultType.GEOL);
            } else if (selectedButton == yutType) {
                return new YutResult(YutResultType.YUT);
            } else if (selectedButton == moType) {
                return new YutResult(YutResultType.MO);
            }
        }
        
        // 취소 또는 창 닫힘 (default : DO)
        return new YutResult(YutResultType.DO);
    }

    // 기존 showYutThrowDialog 함수
    public boolean showYutThrowDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("윷 던지기");
        alert.setHeaderText("윷을 어떻게 던지시겠습니까?");

        ButtonType randomType = new ButtonType("랜덤");
        ButtonType manualType = new ButtonType("수동");

        alert.getButtonTypes().setAll(randomType, manualType);

        Optional<ButtonType> result = alert.showAndWait();
        lastThrowWasRandom = result.isPresent() && result.get() == randomType;
        return lastThrowWasRandom;
    }

    // 변수 추가: 마지막 윷 던지기 방식
    private boolean lastThrowWasRandom = true;

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