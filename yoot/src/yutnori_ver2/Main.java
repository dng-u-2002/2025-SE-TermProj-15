package yutnori_ver2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("참여할 플레이어 수를 입력하세요 (2 ~ 4): ");
        int numPlayers = scanner.nextInt();

        System.out.print("플레이어당 말의 개수를 입력하세요 (2 ~ 5): ");
        int piecesPerPlayer = scanner.nextInt();

        System.out.println("판 형태를 선택하세요:");
        System.out.println("1. 사각형 (RECTANGLE)");
        System.out.println("2. 오각형 (PENTAGON)");
        System.out.println("3. 육각형 (HEXAGON)");
        int boardChoice = scanner.nextInt();

        switch (boardChoice) {
            case 2 -> boardChoice = 5;
            case 3 -> boardChoice = 6;
            default -> boardChoice = 4;
        }

        Game game = new Game(numPlayers, piecesPerPlayer, boardChoice);

        System.out.println("게임을 종료합니다.");
    }
}
