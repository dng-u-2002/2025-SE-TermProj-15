import java.util.ArrayList;
import java.util.List;

public class RuleEngine {
    private int index;
    private int boardType;
    private List<Integer> yutResult;
    public List<Integer> possibleLocation = new ArrayList<>();
    private boolean isCorner = false;
    private boolean isCenter = false;
    private boolean isDiagonal = false;
    public boolean isFinish = false;
    public boolean isStart = false;

    public RuleEngine(int index, int boardType, List<Integer> yutResult){
        this.index = index;
        this.boardType = boardType;
        this.yutResult = yutResult;
        if(index == 999){
            isStart = true;
        }else if(index > 5*boardType && index % 5 == 0){
            isCenter = true;
        } else if(index % 5 == 0 && index/5 < boardType/2 + 1){
            isCorner = true;
        } else if(index >= 5*boardType){
            isDiagonal = true;
        } 
    }

    public List<Integer> getPossibleLocation(){
        for(int yut: yutResult){
        	int result = index + yut*2;
            if(isStart && yut != -1){
                possibleLocation.add(yut);
            } else if(isCenter){
                index = (boardType - 3)*50 + 5;
                if((yut*2 + index)%10 != yut%10 && (yut*2 + index)%10 == 1){
                    possibleLocation.add(5*boardType);
                } else if((yut*2 + index)%10 != yut%10 ){
                    isFinish = true;
                } else{
                    possibleLocation.add(yut*2 + index);
                }
            } else if(index == 5*boardType){
                if(yut == -1){
                    possibleLocation.add(yut + index);
                } else{
                    isFinish = true;
                }
            } else if(isCorner){
                if(yut == -1){
                    possibleLocation.add(index + yut);
                } else{
                    possibleLocation.add(index*10 + 2*yut - 1);
                }
            } else if(isDiagonal){
                if(index%10 == 1 && yut == -1){
                    possibleLocation.add((index + yut)/10);
                } else if(result / 10 != index /10){
                	boolean even = boardType % 2 == 0;
                    if(result%10 == 1 && even && (boardType-2)/2 <= index/50){
                        possibleLocation.add(5*boardType - 5);
                    } else if(even && (boardType-2)/2 <= index/50) {
                    	possibleLocation.add((result/10+1)/2+boardType*5 -6);
                    } else if((int)Math.ceil((boardType-2)/2) <= index/50){
                    	possibleLocation.add((result/10+1)/2+boardType*5 -6);
                    } else {
                    	isFinish = true;
                    }
                } else{
                    possibleLocation.add(result);
                }
            } else{
                if(index + yut > boardType*5){
                    isFinish = true;
                } else{
                    possibleLocation.add(index + yut);
                }
            }
        }
        for(int i = 0; i < possibleLocation.size(); i++){
            System.out.println((i+1) + ". possible location: " + possibleLocation.get(i));
        }
        return possibleLocation;
    }

    public boolean finishAble(int yut){
        List<Integer> yutOne = new ArrayList<>();
        yutOne.add(yut);
        RuleEngine finish = new RuleEngine(index, boardType, yutOne);
        finish.getPossibleLocation();
        return finish.isFinish;
    }
}
