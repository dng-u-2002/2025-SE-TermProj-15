import Model.RuleEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleEngineTest {
    RuleEngine ruleEngine;
    List<Integer> yutResult = new ArrayList<Integer>();
    @BeforeAll
    static void init() {

    }
    @BeforeEach
    void setUp() {
        yutResult.add(1);
        ruleEngine = new RuleEngine(0, 4, yutResult);
    }
    @Test
    void getPossibleLocationTest() {
        List<Integer> expected = new ArrayList<Integer>(){{add(1);}};
        //assertIterableEquals(ruleEngine.getPossibleLocation(), expected);
        assertEquals(expected.get(0), ruleEngine.getPossibleLocation().get(0));
    }

    @Test
    void finishAbleTest() {
    }

    @AfterEach
    void tearDown() {
        System.gc();
    }
}