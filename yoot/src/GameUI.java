//import javax.swing.*;
//import java.util.List;
//
//
//public class GameUI {
//    private Game gameController;
//    private YutScreen yutScreen;
//    private BoardPanel boardPanel;
//
//    public GameUI(Game gameController, int numPlayers, int piecesPerPlayer, int boardType) {
//        this.gameController = gameController;
//
//        // YutScreen 생성 및 참조 저장
//        this.yutScreen = new YutScreen(numPlayers, piecesPerPlayer, boardType, gameController);
//        this.boardPanel = yutScreen.getBoard();
//
//        // BoardPanel에 게임 컨트롤러 참조 설정
//        boardPanel.setGameController(gameController);
//
//        // 초기 상태 메시지 설정
//        updateGameStatus("플레이어 1의 차례입니다. 윷을 던지세요.");
//    }
//
//    // 게임 상태 업데이트 및 화면에 표시하는 메서드
//    public void updateGameStatus(String message) {
//        if (yutScreen != null) {
//            yutScreen.updateStatusMessage(message);
//        }
//    }
//
//    // 윷 결과 표시
//    public void displayYutResult(String result) {
//        if (yutScreen != null) {
//            yutScreen.displayYutResult(result);
//        }
//    }
//
//    // 선택된 말 표시 및 가능한 이동 위치 하이라이트
//    public void selectPiece(int position) {
//        if (boardPanel != null) {
//            boardPanel.selectPiece(position);
//        }
//    }
//
//    // 이동 가능한 위치 설정
//    public void setPossibleMoveLocations(List<Integer> locations) {
//        if (boardPanel != null) {
//            boardPanel.setPossibleMoveLocations(locations);
//        }
//    }
//
//    // 이동 가능한 위치 지우기
//    public void clearPossibleMoves() {
//        if (boardPanel != null) {
//            boardPanel.clearPossibleMoves();
//        }
//    }
//
//    // 현재 플레이어 설정
//    public void setCurrentPlayer(int playerId) {
//        if (boardPanel != null) {
//            boardPanel.setCurrentPlayer(playerId);
//        }
//    }
//
//    // 보드 상태 업데이트
//    public void updateBoard(List<Player> players, int boardType) {
//        if (boardPanel != null) {
//            // 각 플레이어의 말 위치를 보드에 표시
//            boardPanel.clearPieces(); // 기존 말 지우기
//
//            for (Player player : players) {
//                for (int i = 0; i < boardType*100; i++) {
//                    if (player.pieceIndex[i] > 0) {
//                        // 플레이어 ID와 말 개수 정보로 보드 업데이트
//                        boardPanel.addPiece(i, player.getId(), player.pieceIndex[i]);
//                    }
//                }
//            }
//
//            boardPanel.repaint();
//        }
//    }
//
//    // 윷 던지기 다이얼로그 표시 및 결과 반환
//    public int showYutThrowDialog() {
//        Object[] options = {"랜덤", "수동"};
//        return JOptionPane.showOptionDialog(
//                yutScreen,
//                "윷을 어떻게 던지시겠습니까?",
//                "윷 던지기",
//                JOptionPane.YES_NO_OPTION,
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                options,
//                options[0]
//        );
//    }
//
//    // 말 잡기 알림 표시
//    public void showCatchMessage(int playerId) {
//        JOptionPane.showMessageDialog(
//                yutScreen,
//                "플레이어 " + playerId + "가 상대방 말을 잡았습니다! 추가 턴이 주어집니다.",
//                "말 잡기",
//                JOptionPane.INFORMATION_MESSAGE
//        );
//    }
//
//    // 추가 턴 알림 표시
//    public void showExtraTurnMessage(String resultName) {
//        JOptionPane.showMessageDialog(
//                yutScreen,
//                resultName + "이(가) 나와 한 번 더 던집니다!",
//                "추가 턴",
//                JOptionPane.INFORMATION_MESSAGE
//        );
//    }
//
//    // 빽도 관련 알림 표시
//    public void showBackDoSkipMessage() {
//        JOptionPane.showMessageDialog(
//                yutScreen,
//                "빽도(가) 나와 건너뜁니다!",
//                "상대방 턴",
//                JOptionPane.INFORMATION_MESSAGE
//        );
//    }
//
//    // 골인 선택 다이얼로그 표시
//    public String showFinishSelectDialog(List<String> options) {
//        if (options.isEmpty()) {
//            return null;
//        }
//
//        return (String) JOptionPane.showInputDialog(
//                yutScreen,
//                "어떤 윷 결과로 골인하시겠습니까?",
//                "골인 선택",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                options.toArray(),
//                options.get(0)
//        );
//    }
//
//    // 게임 종료 알림
//    public void showVictoryMessage(int playerId) {
//        JOptionPane.showMessageDialog(
//                yutScreen,
//                "플레이어 " + playerId + "가 승리했습니다!",
//                "게임 종료",
//                JOptionPane.INFORMATION_MESSAGE
//        );
//    }
//
//    // 게임 재시작 확인 다이얼로그
//    public boolean showRestartDialog() {
//        int response = JOptionPane.showConfirmDialog(
//                yutScreen,
//                "게임을 다시 시작하시겠습니까?",
//                "재시작",
//                JOptionPane.YES_NO_OPTION
//        );
//        return response == JOptionPane.YES_OPTION;
//    }
//
//    // 창 닫기
//    public void closeWindow() {
//        if (yutScreen != null) {
//            yutScreen.dispose();
//        }
//    }
//
//    // 게임 화면 가져오기
//    public YutScreen getYutScreen() {
//        return yutScreen;
//    }
//}