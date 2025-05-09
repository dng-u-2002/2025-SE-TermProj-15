import java.util.Random;

public class Yoot {

    // 윷을 던져서 나온 값에 해당하는 플래그
    public static final int BACKDO = -1;    // 빽도: 1칸 뒤로
    public static final int DO = 1;     // 도: 1칸 전진
    public static final int GAE = 2;    // 개: 2칸 전진
    public static final int GUL = 3;    // 걸: 3칸 전진
    public static final int YOOT = 4;   // 윷: 4칸 전진
    public static final int MO = 5;     // 모: 5칸 전진

    public static final int SIDE_FLAT = 1;      // 평평한 면을 나타내는 플래그 변수
    public static final int SIDE_ROUND = 0;     // 둥근 면을 나타내는 플래그 변수

    private static final float PROB_FLAT = 0.6f;        // 평평한 면이 나올 확률, 대체로  평평한 면이 나올 확률이 60%, 둥근 면이 나올 확률을 40%로 한다.

    private static int[] yootSticks = new int[4];       //윷가락을 4개로 하고 yootSticks[0]을 빽도가 있는 윷가락으로 설정한다.


    public static int yootRandom() {
        Random random = new Random();	// 윷이 랜덤하게 던져질 수 있도록 랜덤값을 생성한다.
        int yootsum = 0;

        for(int i = 0; i < 4; i++)// 총 4번(윷가락의 개수) 반복하면서 각각의 윷가락에 해당되는 면을 랜덤하게 바꿔주고 모든 값을 더해준다.
        {
            if(random.nextFloat() <= PROB_FLAT)    // 평평한 면이 나온 경우
                yootSticks[i] = SIDE_FLAT;
            else    // 둥근 면이 나온 경우
                yootSticks[i] = SIDE_ROUND;
            yootsum += yootSticks[i]; //총합이 평평한면이 나온 갯수
        }
        if(yootsum == 4)
        {
            return YOOT;// 윷이 나온 경우
        }
        else if(yootsum == 3)
        {
            return GUL;// 걸이 나온 경우
        }
        else if(yootsum == 2)
        {
            return GAE;// 개가 나온 경우
        }
        else if(yootsum == 1)
        {
            if(yootSticks[0] == 1)
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
}
