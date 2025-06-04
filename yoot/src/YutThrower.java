import java.util.Random;
import java.util.Scanner;

public class YutThrower {

    // 윷을 던져서 나온 값에 해당하는 플래그
    public static final int BACKDO = 0;    // 빽도: 1칸 뒤로
    public static final int DO = 1;     // 도: 1칸 전진
    public static final int GAE = 2;    // 개: 2칸 전진
    public static final int GUL = 3;    // 걸: 3칸 전진
    public static final int YUT = 4;   // 윷: 4칸 전진
    public static final int MO = 5;     // 모: 5칸 전진

    public static final int SIDE_FLAT = 1;      // 평평한 면을 나타내는 플래그 변수
    public static final int SIDE_ROUND = 0;     // 둥근 면을 나타내는 플래그 변수

    private static final float PROB_FLAT = 0.6f;        // 평평한 면이 나올 확률, 대체로  평평한 면이 나올 확률이 60%, 둥근 면이 나올 확률을 40%로 한다.

    private static int[] yutSticks = new int[4];       //윷가락을 4개로 하고 yutSticks[0]을 빽도가 있는 윷가락으로 설정한다.


    public static int yutRandom() {
        Random random = new Random();	// 윷이 랜덤하게 던져질 수 있도록 랜덤값을 생성한다.
        int yutsum = 0;

        for(int i = 0; i < 4; i++)// 총 4번(윷가락의 개수) 반복하면서 각각의 윷가락에 해당되는 면을 랜덤하게 바꿔주고 모든 값을 더해준다.
        {
            if(random.nextFloat() <= PROB_FLAT)    // 평평한 면이 나온 경우
                yutSticks[i] = SIDE_FLAT;
            else    // 둥근 면이 나온 경우
                yutSticks[i] = SIDE_ROUND;
            yutsum += yutSticks[i]; //총합이 평평한면이 나온 갯수
        }
        if(yutsum == 4)
        {
            return YUT;// 윷이 나온 경우
        }
        else if(yutsum == 3)
        {
            return GUL;// 걸이 나온 경우
        }
        else if(yutsum == 2)
        {
            return GAE;// 개가 나온 경우
        }
        else if(yutsum == 1)
        {
            if(yutSticks[0] == 1)
            {
                return BACKDO;// 0번 윷이 평평한면이 나오면 빽도가 나온 경우
            }
            else
            {
                return DO;// 도가 나온 경우
            }
        }
        else
        {
            return MO;// 모가 나온 경우
        }
    }

    public static YutResult throwRandom() {
        int roll = yutRandom();
        YutResultType type = YutResultType.values()[roll];
        System.out.println("랜덤 윷 던지기 결과: [" + type.getDisplayName() + "]");
        return new YutResult(type);
    }

    public static YutResult throwManual(int manual) {
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.println("윷 결과를 수동으로 선택하세요:");
        for (int i = 0; i < YutResultType.values().length; i++) {
            System.out.println((i) + ". " + YutResultType.values()[i].getDisplayName());
        }

        int choice;
        while (true) {
            System.out.print("번호 입력 (1 ~ 6): ");
            choice = scanner.nextInt();
            if (choice >= 0 && choice <= 5) break;
            System.out.println("잘못된 선택입니다.");
        }
        */
        if(manual == -1){
            manual = 0;
        }
        YutResultType chosen = YutResultType.values()[manual];
        System.out.println("수동 선택 결과: [" + chosen.getDisplayName() + "]");
        return new YutResult(chosen);

    }
}
