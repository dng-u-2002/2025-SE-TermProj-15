import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleEngineTest {

    public final int startPos = 999;
    public final int squareCenterPos = 200;
    public final int pentagonCenterPos = 250;
    public final int hexagonCenterPos = 300;

    @AfterAll
    public static void tearDown() {
        System.gc();
    }

    //Test for StartPosition
    @Test
    public void testSquareStartPositionMoves() {
        RuleEngine engine = new RuleEngine(startPos, 4, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> result = engine.getPossibleLocation();
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }
    @Test
    public void testPentagonStartPositionMoves() {
        RuleEngine engine = new RuleEngine(startPos, 5, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> result = engine.getPossibleLocation();
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }
    @Test
    public void testHexagonStartPositionMoves() {
        RuleEngine engine = new RuleEngine(startPos, 6, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> result = engine.getPossibleLocation();
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    //Test for Center Position
    @Test
    public void testSquareCenterMove() {
        RuleEngine engine = new RuleEngine(squareCenterPos, 4, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> result = engine.getPossibleLocation();
        assertEquals(List.of(52, 201, 202, 0), result);
    }
    @Test
    public void testPentagonCenterMove() {
        RuleEngine engine = new RuleEngine(pentagonCenterPos, 5, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> result = engine.getPossibleLocation();
        assertEquals(List.of(52, 251, 252, 0), result);
    }
    @Test
    public void testHexagonCenterMove() {
        RuleEngine engine = new RuleEngine(hexagonCenterPos, 6, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> result = engine.getPossibleLocation();
        assertEquals(List.of(52, 301, 302, 0), result);
    }

    //Test for Square Corner Position
    @Test
    public void testSquareCornerMove() {
        RuleEngine engine = new RuleEngine(5, 4, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner1 = engine.getPossibleLocation();
        assertEquals(List.of(4, 51, 52, 200, 152, 151), corner1);

        engine = new RuleEngine(10, 4, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner2 = engine.getPossibleLocation();
        assertEquals(List.of(9, 101, 102, 200, 201, 202), corner2);

        engine = new RuleEngine(15, 4, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner3 = engine.getPossibleLocation();
        assertEquals(List.of(14, 16, 17, 18, 19, 0), corner3);
    }
    //Test for Pentagon Corner Position
    @Test
    public void testPentagonCornerMove() {
        RuleEngine engine = new RuleEngine(5, 5, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner1 = engine.getPossibleLocation();
        assertEquals(List.of(4, 51, 52, 250, 202, 201), corner1);

        engine = new RuleEngine(10, 5, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner2 = engine.getPossibleLocation();
        assertEquals(List.of(9, 101, 102, 250, 202, 201), corner2);

        engine = new RuleEngine(15, 5, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner3 = engine.getPossibleLocation();
        assertEquals(List.of(14, 151, 152, 250, 251, 252), corner3);

        engine = new RuleEngine(20, 5, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner4 = engine.getPossibleLocation();
        assertEquals(List.of(19, 21, 22, 23, 24, 0), corner4);
    }
    //Test for Hexagon Corner Position
    @Test
    public void testHexagonCornerMove() {
        RuleEngine engine = new RuleEngine(5, 6, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner1 = engine.getPossibleLocation();
        assertEquals(List.of(4, 51, 52, 300, 252, 251), corner1);

        engine = new RuleEngine(10, 6, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner2 = engine.getPossibleLocation();
        assertEquals(List.of(9, 101, 102, 300, 252, 251), corner2);

        engine = new RuleEngine(15, 6, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner3 = engine.getPossibleLocation();
        assertEquals(List.of(14, 151, 152, 300, 301, 302), corner3);

        engine = new RuleEngine(20, 6, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner4 = engine.getPossibleLocation();
        assertEquals(List.of(19, 201, 202, 300, 301, 302), corner4);

        engine = new RuleEngine(25, 6, List.of(-1, 1, 2, 3, 4, 5));
        List<Integer> corner5 = engine.getPossibleLocation();
        assertEquals(List.of(24, 26, 27, 28, 29, 0), corner5);
    }

    //Test for normal finish
    @Test
    public void testFinishableTrueAtNormal(){
        //Square
        RuleEngine engine = new RuleEngine(19, 4, List.of());
        engine.getPossibleLocation();
        assertTrue(engine.finishAble(2));
        //Pentagon
        engine = new RuleEngine(24, 5, List.of());
        engine.getPossibleLocation();
        assertTrue(engine.finishAble(2));
        //Hexagon
        engine = new RuleEngine(29, 6, List.of());
        engine.getPossibleLocation();
        assertTrue(engine.finishAble(2));
    }

    //Test for finishing at zero
    @Test
    public void testFinishAbleTrueAtZero() {
        //Square
        RuleEngine engine = new RuleEngine(0, 4, List.of());
        assertTrue(engine.finishAble(1));
        //Pentagon
        engine = new RuleEngine(0, 5, List.of());
        assertTrue(engine.finishAble(2));
        //Hexagon
        engine = new RuleEngine(0, 6, List.of());
        assertTrue(engine.finishAble(3));
    }

    @Test
    public void testFinishAbleFalse() {
        RuleEngine engine = new RuleEngine(1, 4, List.of());
        assertFalse(engine.finishAble(1));
        engine = new RuleEngine(1, 5, List.of());
        assertFalse(engine.finishAble(2));
        engine = new RuleEngine(1, 6, List.of());
        assertFalse(engine.finishAble(3));
    }
}