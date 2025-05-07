package yutnori;

import java.util.List;

public class Game {
    private List<Player> players;
    private Board board;
    private Turn turn;
    private RuleEngine ruleEngine;

    public Game() {
        this.players = new java.util.ArrayList<>();
        this.ruleEngine = new RuleEngine();
    }

    public void startGame(int numPlayers, int piecesPerPlayer, BoardType boardType) {
        System.out.println("게임을 시작합니다. 플레이어 수: " + numPlayers + ", 말 개수: " + piecesPerPlayer + ", 판 형태: " + boardType);
        this.board = new Board(boardType);
        this.players.clear();

        for (int i = 1; i <= numPlayers; i++) {
            Player player = new Player(i, "Player_" + i, piecesPerPlayer, board.getStartLocation());
            players.add(player);
        }

        this.turn = new Turn(players.get(0));
    }

    public void chooseNextResult(List<YutResult> results) {
        System.out.println(turn.getCurrentPlayer().getName() + "이(가) 윷 결과를 선택합니다.");
        if (results.size() > 1) {
            YutResult chosen = results.get(0); // 기본적으로 첫 번째 선택
            chosen.setUsed(true);
            System.out.println("선택된 결과: " + chosen.getType());
        }
    }

    public Player checkWinner() {
        for (Player player : players) {
            if (player.hasWon()) {
                System.out.println(player.getName() + "이(가) 승리했습니다!");
                return player;
            }
        }
        return null;
    }

    public void restartGame() {
        System.out.println("게임을 재시작합니다.");
        startGame(players.size(), players.get(0).getPieces().size(), board.getType());
    }

    private List<YutResult> chooseResultOrder(List<YutResult> results, java.util.Scanner scanner) {
        List<YutResult> ordered = new java.util.ArrayList<>();
        List<Integer> usedIndexes = new java.util.ArrayList<>();

        System.out.println("사용할 윷 결과들의 순서를 입력하세요:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        while (ordered.size() < results.size()) {
            System.out.print("순서 " + (ordered.size() + 1) + "번째로 사용할 윷 결과 번호: ");
            int idx = scanner.nextInt() - 1;
            if (idx < 0 || idx >= results.size() || usedIndexes.contains(idx)) {
                System.out.println("잘못된 선택입니다.");
                continue;
            }
            usedIndexes.add(idx);
            ordered.add(results.get(idx));
        }

        return ordered;
    }

    public void playTurn(java.util.Scanner scanner) {
        Player player = turn.getCurrentPlayer();
        System.out.println("[" + player.getName() + "]의 차례입니다.");

        boolean extra = true;
        while (extra) {
            System.out.print("윷을 던지시겠습니까? (1: 랜덤, 2: 수동): ");
            int choice = scanner.nextInt();
            YutResult result = (choice == 2) ? YutThrower.throwManual() : YutThrower.throwRandom();
            turn.addResult(result);
            extra = result.getType().hasExtraTurn();
        }

        List<YutResult> resultsToUse = new java.util.ArrayList<>();
        for (YutResult r : turn.getResults()) {
            if (!r.isUsed()) resultsToUse.add(r);
        }

        System.out.println("이번 턴의 윷 결과 목록:");
        for (int i = 0; i < resultsToUse.size(); i++) {
            System.out.println((i + 1) + ". " + resultsToUse.get(i));
        }

        List<Piece> movable = player.getPieces();
        System.out.println("이동할 말을 선택하세요:");
        for (int i = 0; i < movable.size(); i++) {
            System.out.println((i + 1) + ". " + movable.get(i));
        }

        int selected;
        while (true) {
            System.out.print("이동시킬 말 번호 선택: ");
            selected = scanner.nextInt();
            if (selected >= 1 && selected <= movable.size()) break;
            System.out.println("잘못된 선택입니다.");
        }

        Piece chosen = movable.get(selected - 1);
        List<YutResult> ordered = chooseResultOrder(resultsToUse, scanner);

        for (YutResult r : ordered) {
            applyResultToPiece(chosen, r);
        }

        if (turn.shouldEnd()) {
            if (checkWinner() == null) {
                turn.nextTurn(players);
            }
        }
    }

    private void applyResultToPiece(Piece piece, YutResult result) {
        // ✅ 1. 이전 이동에서 분기 가능 상태였다면, 먼저 분기 노드로 이동 (윷 결과를 소비하지 않음)
        if (piece.isEligibleForBranch()) {
            String from = piece.getLocation().getId();
            switch (from) {
                case "5" -> piece.moveTo(board.getLocationById("51"));
                case "10" -> piece.moveTo(board.getLocationById("101"));
                case "200" -> piece.moveTo(board.getLocationById("201"));
            }
            piece.setEligibleForBranch(false); // 한 번만 분기
            System.out.println("분기 경로로 이동합니다.");
            return; // 다음 윷 결과는 다음 호출에서 처리
        }

        // ✅ 2. 일반 이동 처리
        Location current = piece.getLocation();
        List<Location> nextLocs = board.getNextLocations(current, result);
        if (nextLocs.isEmpty()) {
            System.out.println("이동 가능한 위치가 없습니다.");
            result.setUsed(true);
            return;
        }

        Location destination = nextLocs.get(0);  // 단일 경로 선택 (복수 처리 시 개선 가능)
        System.out.println("말이 " + destination.getId() + "로 이동합니다.");

        // 잡기 처리
        for (Piece p : destination.getPiecesOnThisLocation()) {
            if (!p.getOwner().equals(piece.getOwner())) {
                System.out.println("잡기 발생! " + p + " 제거!");
                destination.removePiece(p);
                p.setFinished(true);
            }
        }

        piece.moveTo(destination);

        // ✅ 3. 정지 위치가 분기 가능 위치인지 확인 후 설정
        boolean shouldBranch = destination.getId().equals("5")
                || destination.getId().equals("10")
                || destination.getId().equals("200");
        piece.setEligibleForBranch(shouldBranch);

        // 완주 처리
        if (destination.isEnd()) {
            piece.setFinished(true);
            System.out.println(piece + "이(가) 완주했습니다!");
        }

        result.setUsed(true);
    }




    public Player getCurrentPlayer() {
        return turn.getCurrentPlayer();
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Turn getTurn() {
        return turn;
    }

    public RuleEngine getRuleEngine() {
        return ruleEngine;
    }
}
