// 윷 결과, 추가 턴 여부 묶어서 전달하는 클래스
public class YutThrowResult {
    private YutResult result;
    private boolean hasExtraTurn;
    
    public YutThrowResult(YutResult result, boolean hasExtraTurn) {
        this.result = result;
        this.hasExtraTurn = hasExtraTurn;
    }
    
    public YutResult getResult() {
        return result;
    }
    
    public boolean hasExtraTurn() {
        return hasExtraTurn;
    }
}