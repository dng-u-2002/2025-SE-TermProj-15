package yutnori_ver2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleEngineTest {
    @Test
    public void testStartPositionMoves() {
        RuleEngine engine = new RuleEngine(999, 4, List.of(1, 2, 3));
        List<Integer> result = engine.getPossibleLocation();
        assertEquals(List.of(1, 2, 3), result);
    }

    @Test
    public void testCenterBackDoMove() {
        RuleEngine engine = new RuleEngine(200, 4, List.of(-1));
        List<Integer> result = engine.getPossibleLocation();
        assertEquals(List.of(199), result); // center에서 -1은 index - 1
    }

    @Test
    public void testCornerSquarePath() {
        RuleEngine engine = new RuleEngine(5, 4, List.of(1, 3));
        List<Integer> result = engine.getPossibleLocation();

        assertTrue(result.contains(51), "1칸 이동: index*10 + 1 → 5*10 + 1 = 51");
        assertTrue(result.contains(200), "걸: center로 이동");
    }

    @Test
    public void testCornerPentagonPath() {
        RuleEngine engine = new RuleEngine(10, 5, List.of(3));
        List<Integer> result = engine.getPossibleLocation();
        assertTrue(result.contains(250), "3일 경우 center로 이동");
    }

    @Test
    public void testCornerHexagonPath() {
        RuleEngine engine = new RuleEngine(15, 6, List.of(4));
        List<Integer> result = engine.getPossibleLocation();
        assertFalse(result.isEmpty(), "복잡한 식이지만 값이 있어야 함");
    }

    @Test
    public void testDiagonalPosition201() {
        RuleEngine engine = new RuleEngine(201, 4, List.of(3));
        List<Integer> result = engine.getPossibleLocation();
        assertTrue(engine.isFinish, "201에서 3이면 finish");
    }

    @Test
    public void testNormalMovementWithFinish() {
        int boardType = 4;
        int index = boardType * 5 - 1; // 최대 경로 전
        RuleEngine engine = new RuleEngine(index, boardType, List.of(2)); // 초과
        List<Integer> result = engine.getPossibleLocation();
        assertTrue(engine.isFinish);
    }

    @Test
    public void testFinishAbleTrueForStart() {
        RuleEngine engine = new RuleEngine(0, 4, List.of());
        assertTrue(engine.finishAble(3));
    }

    @Test
    public void testFinishAbleFalseFromNormal() {
        RuleEngine engine = new RuleEngine(1, 4, List.of());
        assertFalse(engine.finishAble(1));
    }

    @Test
    public void testFinishAbleFromHexDiagonal302() {
        RuleEngine engine = new RuleEngine(302, 6, List.of());
        assertTrue(engine.finishAble(3));
        assertTrue(engine.finishAble(4));
        assertTrue(engine.finishAble(5));
        assertFalse(engine.finishAble(1));
    }
}