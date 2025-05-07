package yutnori;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🎮 윷놀이 게임에 오신 것을 환영합니다!");
        System.out.print("참여할 플레이어 수를 입력하세요 (2 ~ 4): ");
        int numPlayers = scanner.nextInt();

        System.out.print("플레이어당 말의 개수를 입력하세요 (2 ~ 5): ");
        int piecesPerPlayer = scanner.nextInt();

        System.out.println("판 형태를 선택하세요:");
        System.out.println("1. 사각형 (RECTANGLE)");
        System.out.println("2. 오각형 (PENTAGON)");
        System.out.println("3. 육각형 (HEXAGON)");
        int boardChoice = scanner.nextInt();

        BoardType boardType;
        switch (boardChoice) {
            case 2 -> boardType = BoardType.PENTAGON;
            case 3 -> boardType = BoardType.HEXAGON;
            default -> boardType = BoardType.RECTANGLE;
        }

        Game game = new Game();
        game.startGame(numPlayers, piecesPerPlayer, boardType);

        // 본격적인 게임 루프
        while (true) {
            game.playTurn(scanner);

            Player winner = game.checkWinner();
            if (winner != null) {
                System.out.println("🎉 게임 종료! 승자: " + winner.getName());
                break;
            }
        }

        System.out.println("게임을 종료합니다.");
    }
}
