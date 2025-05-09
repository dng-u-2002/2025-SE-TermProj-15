package yutnori_ver2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public int numPlayers, piecesPerPlayer, boardType;
    private List<Player> players;
    public List<Integer> yutResult = new ArrayList<>();



    public Game(int numPlayers, int piecesPerPlayer, int boardType) {
        this.players = new java.util.ArrayList<>();
        this.numPlayers = numPlayers;
        this.piecesPerPlayer = piecesPerPlayer;
        this.boardType = boardType;
        System.out.println("게임을 시작합니다. 플레이어 수: " + numPlayers + ", 말 개수: " + piecesPerPlayer + ", 판 형태: " + boardType);
        startGame();
    }

    public void startGame(){
        for(int i = 0; i<numPlayers; i++){
            Player player = new yutnori_ver2.Player(i+1, piecesPerPlayer, boardType);
            players.add(player);
        }
        while(checkWinner() == 0){
            for(Player player: players){
                System.out.println("[player " + player.getId() + "]의 차례입니다.");
                Scanner scanner = new Scanner(System.in);
                throwYut();
                playTurn(player, yutResult);
                yutResult.clear();
                if(player.hasWon()){
                    System.out.println("🎉 게임 종료! 승자: " +player.getId());
                    break;
                }
            }
        }
    }

    public int checkWinner() {
        for (Player player : players) {
            if (player.hasWon()) {
                return player.getId();
            }
        }
        return 0;
    }

    public void throwYut(){
        Scanner scanner = new Scanner(System.in);
        boolean extra = true;
        while (extra) {
            System.out.print("윷을 던지시겠습니까? (1: 랜덤, 2: 수동): ");
            int choice = scanner.nextInt();
            YutResult result = (choice == 2) ? YutThrower.throwManual() : YutThrower.throwRandom();
            yutResult.add(result.getValue());
            extra = result.getType().hasExtraTurn();
        }
        System.out.println("이번 턴의 윷 결과 목록:");
        for (int i = 0; i < yutResult.size(); i++) {
            System.out.println((i + 1) + ". " + yutResult.get(i));
        }
    }

    public void playTurn(Player player, List<Integer> yutResultAtTurn) {
        while(!yutResultAtTurn.isEmpty()){
            Scanner scanner = new Scanner(System.in);
            //말 위치 출력
            System.out.println("이동할 말을 선택하세요:");
            for (int i = 0; i < player.boardType*100; i++) {
                if(player.pieceIndex[i] != 0){
                    System.out.println(player.pieceIndex[i] + " pieces at " + i);
                }
            }
            System.out.println(player.pieceAtStart + " pieces at start");

            //이동할 말 선택
            int selected;
            while (true) {
                System.out.print("이동시킬 말 번호 선택: ");
                selected = scanner.nextInt();
                if(selected == 999) break;
                if (player.pieceIndex[selected] != 0) break;
                System.out.println("잘못된 선택입니다.");
            }
            //이동할 위치 출력
            RuleEngine printResult = new RuleEngine(selected, boardType, yutResultAtTurn);
            List<Integer> possibleLocation = printResult.getPossibleLocation();
            //들어가는 것 문제
            if(printResult.isFinish){

                List<Integer> finishableYut = new ArrayList<>();
                int count = 0;
                if(yutResultAtTurn.size() > 1){
                    for(int yut: yutResultAtTurn){
                        if(printResult.finishAble(yut)){
                            finishableYut.add(count);
                            System.out.println(count + ". Finish Yut: " + yut);
                        }
                        count++;
                    }
                    System.out.println("Select yut to finish: ");
                    count = scanner.nextInt();
                    yutResultAtTurn.remove(finishableYut.get(count));
                    player.goal(player.pieceIndex[selected]);
                    player.pieceIndex[selected] = 0;
                    continue;
                }
                player.goal(player.pieceIndex[selected]);
                player.pieceIndex[selected] = 0;

            }

            //실제 이동
            int moveTo;
            while (true) {
                System.out.println("이동할 위치를 선택하세요:");
                moveTo = scanner.nextInt();
                boolean valid = false;
                for (int loc : possibleLocation) {
                    if (loc == moveTo) {
                        valid = true;
                        break;
                    }
                }
                if (valid) break;
                System.out.println("잘못된 선택입니다.");
            }
            int removeIndex = possibleLocation.indexOf(moveTo);
            if(player.pieceNum - player.pieceAtEnd == player.pieceAtStart && selected == 999 && moveTo == -1){
                break;
            }
            possibleLocation.remove(removeIndex);
            yutResultAtTurn.remove(removeIndex);
            for(Player opponent: players){
                if(player.getId() != opponent.getId()){
                    boolean extra = opponent.pieceCaught(moveTo);
                    while (extra) {
                        System.out.print("윷을 던지시겠습니까? (1: 랜덤, 2: 수동): ");
                        int choice = scanner.nextInt();
                        YutResult result = (choice == 2) ? YutThrower.throwManual() : YutThrower.throwRandom();
                        yutResultAtTurn.add(result.getValue());
                        extra = result.getType().hasExtraTurn();
                    }
                    System.out.println("이번 턴의 윷 결과 목록:");
                    for (int i = 0; i < yutResultAtTurn.size(); i++) {
                        System.out.println((i + 1) + ". " + yutResultAtTurn.get(i));
                    }
                }
            }
            if(selected == 999){
                player.pieceIndex[moveTo]++;
                player.exitStart();
                continue;
            }
            player.pieceIndex[moveTo] += player.pieceIndex[selected];
            player.pieceIndex[selected] = 0;
        }

    }


    public void restartGame() {
        System.out.println("게임을 재시작합니다.");
    }
}