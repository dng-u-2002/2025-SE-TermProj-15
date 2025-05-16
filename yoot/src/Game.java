import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Game {
    public int numPlayers, piecesPerPlayer, boardType;
    private List<Player> players;
    public List<Integer> yutResult = new ArrayList<>();

    // YutScreen과 BoardPanel 참조 추가
    private YutScreen yutScreen;
    private BoardPanel boardPanel;

    // 현재 플레이어 인덱스 추가
    private int currentPlayerIndex = 0;

    // 선택된 말 및 관련 데이터
    private int selectedPiecePosition = -1;
    private List<Integer> possibleMoveLocations = new ArrayList<>();

    // 게임 상태를 위한 열거형 추가
    public enum GameState {
        WAITING_FOR_YUT,           // 윷 던지기 대기
        WAITING_FOR_PIECE_SELECTION, // 말 선택 대기
        WAITING_FOR_MOVE_SELECTION, // 이동 위치 선택 대기
        GAME_OVER                  // 게임 종료
    }

    private GameState currentState = GameState.WAITING_FOR_YUT;

    public Game(int numPlayers, int piecesPerPlayer, int boardType) {
        this.players = new java.util.ArrayList<>();
        this.numPlayers = numPlayers;
        this.piecesPerPlayer = piecesPerPlayer;
        this.boardType = boardType;
        System.out.println("게임을 시작합니다. 플레이어 수: " + numPlayers + ", 말 개수: " + piecesPerPlayer + ", 판 형태: " + boardType);

        // YutScreen 생성 및 참조 저장
        this.yutScreen = new YutScreen(numPlayers, piecesPerPlayer, boardType, this);
        this.boardPanel = yutScreen.getBoard();

        // BoardPanel에 게임 컨트롤러 참조 설정
        boardPanel.setGameController(this);

        initializeGame();
    }

    private void initializeGame() {
        // 플레이어 초기화
        for(int i = 0; i < numPlayers; i++) {
            Player player = new Player(i+1, piecesPerPlayer, boardType);
            players.add(player);
        }

        // 첫 번째 플레이어부터 시작
        currentPlayerIndex = 0;

        // 현재 플레이어 정보 BoardPanel에 설정
        boardPanel.setCurrentPlayer(getCurrentPlayer().getId());

        updateGameStatus("플레이어 " + players.get(currentPlayerIndex).getId() + "의 차례입니다. 윷을 던지세요.");
    }

    // 게임 상태 업데이트 및 화면에 표시하는 메서드
    public void updateGameStatus(String message) {
        yutScreen.updateStatusMessage(message);
    }

    // 윷 던지기 버튼 클릭 시 호출되는 메서드
    public void throwYutButtonClicked() {
        if (currentState != GameState.WAITING_FOR_YUT) {
            return; // 현재 상태가 윷 던지기 대기 상태가 아니면 무시
        }

        yutResult.clear(); // 이전 결과 초기화

        // 윷 던지기 다이얼로그 표시
        Object[] options = {"랜덤", "수동"};
        int choice = JOptionPane.showOptionDialog(
                yutScreen,
                "윷을 어떻게 던지시겠습니까?",
                "윷 던지기",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        

        boolean extra = true;
        boolean backStart = false;
        while (extra && !backStart) {
            YutResult result = (choice == 1) ? YutThrower.throwManual() : YutThrower.throwRandom();
            yutResult.add(result.getValue());
            Player currentPlayer = getCurrentPlayer();
            if(currentPlayer.allStart() && result.getValue() == -1 && yutResult.size() == 1) {
            	yutScreen.displayYutResult(result.getType().getDisplayName());
            	yutResult.clear();
            	JOptionPane.showMessageDialog(
            			yutScreen,
            			result.getType().getDisplayName() + "(가) 나와 건너뜁니다!",
            			"상대방 턴",
                        JOptionPane.INFORMATION_MESSAGE
                );
            	nextPlayerTurn();
            	updateBoard();
            	backStart = true;
            }

            // 화면에 윷 결과 표시
            yutScreen.displayYutResult(result.getType().getDisplayName());
            
            extra = result.getType().hasExtraTurn();
            if (extra) {
                // 추가 턴이 있는 경우 메시지 표시
                JOptionPane.showMessageDialog(
                        yutScreen,
                        result.getType().getDisplayName() + "이(가) 나와 한 번 더 던집니다!",
                        "추가 턴",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        
        if(!backStart) {
        	// 윷 결과 목록 표시
            StringBuilder resultMsg = new StringBuilder("이번 턴의 윷 결과 목록:\n");
            for (int i = 0; i < yutResult.size(); i++) {
                resultMsg.append((i + 1)).append(". ").append(yutResult.get(i)).append("\n");
            }
            updateGameStatus(resultMsg.toString());

            // 상태 변경: 말 선택 대기
            currentState = GameState.WAITING_FOR_PIECE_SELECTION;

            // 현재 플레이어의 말 상태 표시
            updateGameStatus(resultMsg + "\n플레이어 " + getCurrentPlayer().getId() +
                    "의 차례입니다. 이동할 말을 선택하세요.");

            // 보드 업데이트
            updateBoard();
        }
        
    }

    // 사용자가 보드에서 말을 선택했을 때 호출되는 메서드
    public void pieceSelected(int position) {
        if (currentState != GameState.WAITING_FOR_PIECE_SELECTION) {
            return; // 현재 상태가 말 선택 대기 상태가 아니면 무시
        }

        Player currentPlayer = getCurrentPlayer();

        // 선택한 위치가 시작점인 경우(999)
        if (position == 999) {
            if (currentPlayer.pieceAtStart <= 0) {
                updateGameStatus("시작점에 말이 없습니다. 다른 말을 선택하세요.");
                return;
            }
        } else {
            // 선택한 위치에 현재 플레이어의 말이 없는 경우
            if (!playerHasPieceAt(currentPlayer, position)) {
                updateGameStatus("해당 위치에 사용자의 말이 없습니다. 다른 말을 선택하세요.");
                return;
            }
        }

        // 선택한 말의 가능한 이동 위치 계산
        selectedPiecePosition = position;
        RuleEngine ruleEngine = new RuleEngine(position, boardType, yutResult);
        possibleMoveLocations = ruleEngine.getPossibleLocation();

        // 말이 골인 가능한 경우
        if (ruleEngine.isFinish) {
            handleFinishableMove(currentPlayer, position, ruleEngine);
        } else {
            // 이동 가능한 위치 없는 경우
            if (possibleMoveLocations.isEmpty()) {
                updateGameStatus("이동 가능한 위치가 없습니다.");
                nextPlayerTurn();
                return;
            }

            // 상태 변경: 이동 위치 선택 대기
            currentState = GameState.WAITING_FOR_MOVE_SELECTION;

            // 이동 가능한 위치 하이라이트
            boardPanel.selectPiece(position);
            boardPanel.setPossibleMoveLocations(possibleMoveLocations);

            updateGameStatus("이동할 위치를 선택하세요.");
        }
    }

    // 사용자가 보드에서 이동 위치를 선택했을 때 호출되는 메서드
    public void moveLocationSelected(int position) {
        if (currentState != GameState.WAITING_FOR_MOVE_SELECTION) {
            return; // 현재 상태가 이동 위치 선택 대기 상태가 아니면 무시
        }

        Player currentPlayer = getCurrentPlayer();

        // 선택한 이동 위치의 인덱스 찾기
        int selectedIndex = possibleMoveLocations.indexOf(position);
        if (selectedIndex == -1) {
            updateGameStatus("잘못된 위치를 선택했습니다. 다시 선택하세요.");
            return;
        }

        // 선택한 윷 결과 제거
        yutResult.remove(selectedIndex);

        // 상대방 말 잡기 처리
        boolean caughtOpponent = false;
        for (Player opponent : players) {
            if (currentPlayer.getId() != opponent.getId()) {
                caughtOpponent = opponent.pieceCaught(position);
                if (caughtOpponent) {
                    // 상대방 말을 잡아서 추가 턴
                    updateGameStatus("플레이어 " + currentPlayer.getId() + "가 상대방 말을 잡았습니다! 추가 턴이 주어집니다.");

                    // 추가 윷 던지기
                    throwYutForCatch();
                    break;
                }
            }
        }

        // 말 이동 처리
        if (selectedPiecePosition == 999) {
            // 시작점에서 나가는 경우
            currentPlayer.pieceIndex[position]++;
            currentPlayer.exitStart();
        } else {
            // 보드 상에서 이동하는 경우
            currentPlayer.pieceIndex[position] += currentPlayer.pieceIndex[selectedPiecePosition];
            currentPlayer.pieceIndex[selectedPiecePosition] = 0;
        }

        updateGameStatus("플레이어 " + currentPlayer.getId() + "의 말이 위치 " + position + "로 이동했습니다.");
        boardPanel.clearPossibleMoves();
        updateBoard();
       

        // 현재 플레이어가 승리했는지 확인
        if (currentPlayer.hasWon()) {
            // 게임 종료 - 남은 윷 결과와 관계없이 승리
            handleVictory(currentPlayer);
            return;
        }
        
        if(currentPlayer.allStart() && yutResult.get(0) == -1 && yutResult.size() == 1) {
        	yutResult.clear();
        	JOptionPane.showMessageDialog(
        			yutScreen,
        			"빽도(가) 남아 건너뜁니다!",
        			"상대방 턴",
                    JOptionPane.INFORMATION_MESSAGE
            );
        	nextPlayerTurn();
        	updateBoard();
        }

        // 잡기로 인한 추가 턴이 없으면 남은 윷 결과 처리 또는 다음 플레이어 턴
        if (!caughtOpponent) {
            continueOrNextPlayer();
        }
    }

    // 골인 가능한 말 처리
    private void handleFinishableMove(Player player, int piecePosition, RuleEngine ruleEngine) {
        if (yutResult.size() > 1) {
            // 여러 개의 윷 결과가 있는 경우, 어떤 결과로 골인할지 선택
            List<Integer> finishableYut = new ArrayList<>();
            List<String> options = new ArrayList<>();

            for (int i = 0; i < yutResult.size(); i++) {
                int yut = yutResult.get(i);
                if (ruleEngine.finishAble(yut)) {
                    finishableYut.add(i);
                    options.add(i + ". 값: " + yut);
                }
            }

            if (!options.isEmpty()) {
                String selected = (String) JOptionPane.showInputDialog(
                        yutScreen,
                        "어떤 윷 결과로 골인하시겠습니까?",
                        "골인 선택",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options.toArray(),
                        options.get(0)
                );

                if (selected != null) {
                    int selectedIndex = options.indexOf(selected);
                    int yutIndex = finishableYut.get(selectedIndex);

                    // 골인 처리
                    player.goal(player.pieceIndex[piecePosition]);
                    player.pieceIndex[piecePosition] = 0;
                    yutResult.remove(yutIndex);

                    updateGameStatus("플레이어 " + player.getId() + "의 말이 골인했습니다!");
                    updateBoard();

                    // 현재 플레이어가 승리했는지 확인
                    if (player.hasWon()) {
                        // 게임 종료 - 남은 윷 결과와 관계없이 승리
                        handleVictory(player);
                        return;
                    }

                    // 남은 윷 결과가 있으면 다음 말 선택, 없으면 다음 플레이어 턴
                    continueOrNextPlayer();
                }
            }
        } else {
            // 윷 결과가 한 개인 경우, 바로 골인
            player.goal(player.pieceIndex[piecePosition]);
            player.pieceIndex[piecePosition] = 0;
            yutResult.clear();

            updateGameStatus("플레이어 " + player.getId() + "의 말이 골인했습니다!");
            updateBoard();

            // 현재 플레이어가 승리했는지 확인
            if (player.hasWon()) {
                // 게임 종료 - 승리
                handleVictory(player);
                return;
            }

            // 다음 플레이어 턴
            nextPlayerTurn();
        }
    }

    // 말 잡았을 때 추가 윷 던지기
    private void throwYutForCatch() {
        Object[] options = {"랜덤", "수동"};
        int choice = JOptionPane.showOptionDialog(
                yutScreen,
                "말을 잡아 추가 윷을 던집니다. 방식을 선택하세요:",
                "추가 윷 던지기",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        boolean extra = true;
        while (extra) {
            YutResult result = (choice == 1) ? YutThrower.throwManual() : YutThrower.throwRandom();
            yutResult.add(result.getValue());

            // 화면에 윷 결과 표시
            yutScreen.displayYutResult(result.getType().getDisplayName());

            extra = result.getType().hasExtraTurn();
            if (extra) {
                JOptionPane.showMessageDialog(
                        yutScreen,
                        result.getType().getDisplayName() + "이(가) 나와 한 번 더 던집니다!",
                        "추가 턴",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }

        // 윷 결과 표시 및 다음 상태로 이동
        StringBuilder resultMsg = new StringBuilder("이번 턴의 윷 결과 목록:\n");
        for (int i = 0; i < yutResult.size(); i++) {
            resultMsg.append((i + 1)).append(". ").append(yutResult.get(i)).append("\n");
        }
        updateGameStatus(resultMsg.toString());

        // 다시 말 선택 상태로
        currentState = GameState.WAITING_FOR_PIECE_SELECTION;
        updateGameStatus(resultMsg + "\n플레이어 " + getCurrentPlayer().getId() + "의 차례입니다. 이동할 말을 선택하세요.");
    }

    // 특정 위치에 플레이어의 말이 있는지 확인
    private boolean playerHasPieceAt(Player player, int position) {
        return player.pieceIndex[position] > 0;
    }

    // 남은 윷 결과가 있으면 계속, 없으면 다음 플레이어 턴
    private void continueOrNextPlayer() {
        if (yutResult.isEmpty()) {
            // 모든 윷 결과 사용 완료, 다음 플레이어 턴
            nextPlayerTurn();
        } else if(yutResult.size() == 1 && yutResult.get(0) == -1) {
        	nextPlayerTurn();
        } else {
            // 남은 윷 결과가 있음, 말 선택 계속
            currentState = GameState.WAITING_FOR_PIECE_SELECTION;
            updateGameStatus("윷 결과가 남아있습니다. 다음으로 이동할 말을 선택하세요.");
        }
    }

    // 승리 처리
    private void handleVictory(Player player) {
        // 게임 종료
        currentState = GameState.GAME_OVER;
        updateGameStatus("🎉 게임 종료! 승자: 플레이어 " + player.getId());

        // 게임 종료 메시지 표시
        JOptionPane.showMessageDialog(
                yutScreen,
                "플레이어 " + player.getId() + "가 승리했습니다!",
                "게임 종료",
                JOptionPane.INFORMATION_MESSAGE
        );

        // 남은 윷 결과 모두 지우기
        yutResult.clear();

        // 재시작 옵션 제공
        int restart = JOptionPane.showConfirmDialog(
                yutScreen,
                "게임을 다시 시작하시겠습니까?",
                "재시작",
                JOptionPane.YES_NO_OPTION
        );

        if (restart == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            yutScreen.dispose(); // 창 닫기
        }
    }

    // 다음 플레이어 턴으로 넘기기
    private void nextPlayerTurn() {
        // 다음 플레이어로 이동
        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
        currentState = GameState.WAITING_FOR_YUT;

        // 현재 플레이어 ID를 보드에 설정
        boardPanel.setCurrentPlayer(getCurrentPlayer().getId());

        updateGameStatus("플레이어 " + getCurrentPlayer().getId() + "의 차례입니다. 윷을 던지세요.");

        // 선택 상태 초기화
        boardPanel.clearPossibleMoves();
    }

    // 보드 상태 업데이트
    private void updateBoard() {
        // 각 플레이어의 말 위치를 보드에 표시
        boardPanel.clearPieces(); // 기존 말 지우기

        for (Player player : players) {
            for (int i = 0; i < boardType*100; i++) {
                if (player.pieceIndex[i] > 0) {
                    // 플레이어 ID와 말 개수 정보로 보드 업데이트
                    boardPanel.addPiece(i, player.getId(), player.pieceIndex[i]);
                }
            }
        }

        boardPanel.repaint();
    }

    public void restartGame() {
        System.out.println("게임을 재시작합니다.");

        // 플레이어 초기화
        players.clear();
        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player(i+1, piecesPerPlayer, boardType);
            players.add(player);
        }

        // 게임 상태 초기화
        currentPlayerIndex = 0;
        currentState = GameState.WAITING_FOR_YUT;
        yutResult.clear();

        // 현재 플레이어 ID 보드에 설정
        boardPanel.setCurrentPlayer(getCurrentPlayer().getId());

        // 보드 초기화
        boardPanel.clearPieces();
        boardPanel.clearPossibleMoves();
        boardPanel.repaint();

        updateGameStatus("게임이 재시작되었습니다. 플레이어 " + players.get(currentPlayerIndex).getId() + "의 차례입니다.");
    }

    // 현재 플레이어 반환
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    // 현재 게임 상태 반환
    public GameState getCurrentState() {
        return currentState;
    }
}