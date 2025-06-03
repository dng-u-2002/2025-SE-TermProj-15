import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class YutResultTest {
    @Test
    public void testValueMatchesType() {
        assertEquals(-1, new YutResult(YutResultType.BACK_DO).getValue());
        assertEquals(1, new YutResult(YutResultType.DO).getValue());
        assertEquals(2, new YutResult(YutResultType.GAE).getValue());
        assertEquals(3, new YutResult(YutResultType.GEOL).getValue());
        assertEquals(4, new YutResult(YutResultType.YUT).getValue());
        assertEquals(5, new YutResult(YutResultType.MO).getValue());
    }

    @Test
    public void testTypeIsStoredCorrectly() {
        YutResult result = new YutResult(YutResultType.DO);
        assertEquals(YutResultType.DO, result.getType());
        result = new YutResult(YutResultType.GAE);
        assertEquals(YutResultType.GAE, result.getType());
        result = new YutResult(YutResultType.GEOL);
        assertEquals(YutResultType.GEOL, result.getType());
        result = new YutResult(YutResultType.YUT);
        assertEquals(YutResultType.YUT, result.getType());
        result = new YutResult(YutResultType.MO);
        assertEquals(YutResultType.MO, result.getType());
        result = new YutResult(YutResultType.BACK_DO);
        assertEquals(YutResultType.BACK_DO, result.getType());
    }

    @Test
    public void testDisplayNames() {
        assertEquals("빽도", YutResultType.BACK_DO.getDisplayName());
        assertEquals("도", YutResultType.DO.getDisplayName());
        assertEquals("개", YutResultType.GAE.getDisplayName());
        assertEquals("걸", YutResultType.GEOL.getDisplayName());
        assertEquals("윷", YutResultType.YUT.getDisplayName());
        assertEquals("모", YutResultType.MO.getDisplayName());
    }

}