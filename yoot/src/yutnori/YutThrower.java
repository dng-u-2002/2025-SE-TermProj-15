package yutnori;

import java.util.Random;
import java.util.Scanner;

public class YutThrower {
    private static final Random random = new Random();

    public static YutResult throwRandom() {
        int roll = random.nextInt(6);  // 0 ~ 5
        YutResultType type = YutResultType.values()[roll];
        System.out.println("랜덤 윷 던지기 결과: [" + type.getDisplayName() + "]");
        return new YutResult(type);
    }

    public static YutResult throwManual() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("윷 결과를 수동으로 선택하세요:");
        for (int i = 0; i < YutResultType.values().length; i++) {
            System.out.println((i + 1) + ". " + YutResultType.values()[i].getDisplayName());
        }

        int choice;
        while (true) {
            System.out.print("번호 입력 (1 ~ 6): ");
            choice = scanner.nextInt();
            if (choice >= 1 && choice <= 6) break;
            System.out.println("잘못된 선택입니다.");
        }

        YutResultType chosen = YutResultType.values()[choice - 1];
        System.out.println("수동 선택 결과: [" + chosen.getDisplayName() + "]");
        return new YutResult(chosen);
    }
}
