import org.junit.jupiter.api.Test;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class YutThrowerTest {
    @Test
    public void testThrowRandomResultVaild() {
        YutResult result = YutThrower.throwRandom();
        assertNotNull(result, "Random Throw 결과는 null이 될 수 없음.");
        assertNotNull(result.getType(), "Random Throw 결과의 타입은 null이 될 수 없음.");
        int resultValue = result.getValue();
        assertTrue((1 <= resultValue && resultValue <= 5) || resultValue == -1, "Random Throw 결과의 value는 -1 또는 1~5의 값이여야 함.");
    }
    @Test
    public void testThrowRandomResultDistribution() {
        HashSet<Integer> resultSet = new HashSet<>();

        for (int i = 0; i < 10000; i++) {
            YutResult result = YutThrower.throwRandom();
            resultSet.add(result.getValue());
        }

        assertTrue(resultSet.contains(-1),  "빽도가 1000번의 시행 중 한 번도 등장하지 않았음.");
        for (int i = 1; i <= 5; i++) {
            assertTrue(resultSet.contains(i), YutResultType.values()[i].getDisplayName() + "(이)가 1000번의 시행 중 한 번도 등장하지 않았음.");
        }
    }
}