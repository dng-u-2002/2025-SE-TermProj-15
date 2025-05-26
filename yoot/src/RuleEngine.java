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
        }else if(index == 50*boardType){
            isCenter = true;
        } else if(index % 5 == 0 && (boardType - 1) * 5 != index){
            isCorner = true;
        } else if(index >= 50){
            isDiagonal = true;
        }
    }

    public List<Integer> getPossibleLocation(){
        for(int yut: yutResult){
            if(isStart && yut != -1){
                possibleLocation.add(yut);
            } else if(isCenter){
                switch(yut){
                    case -1 -> possibleLocation.add(52); //빽도 구현 미완성 지점
                    case 1, 2 -> possibleLocation.add(index + yut);
                    case 3 -> possibleLocation.add(0);
                    case 4, 5 -> isFinish = true;
                }
            } else if(index == 0){
                switch(yut){
                    case -1 -> possibleLocation.add(boardType*5 - 1);
                    case 1, 2, 3, 4, 5 -> isFinish = true;
                }
            } else if(isCorner && boardType == 4){ // 사각형 코너
                switch(yut){
                    case -1 -> possibleLocation.add(index + yut);
                    case 1, 2 -> possibleLocation.add(index*10 + yut);
                    case 3 -> possibleLocation.add(boardType*50);
                    case 4 -> possibleLocation.add((index+10)*10 + (index % 2)+1);
                    case 5 -> possibleLocation.add((index+10)*10 + (index / 5));
                }
            } else if(isCorner && boardType == 5){ // 오각형 코너
                switch(yut){
                    case -1 -> possibleLocation.add(index + yut);
                    case 1, 2 -> possibleLocation.add(index*10 + yut);
                    case 3 -> possibleLocation.add(boardType*50);
                    case 4 -> possibleLocation.add((251*index*index*index - 6795*index*index + 58000*index) / 750);
                    case 5 -> possibleLocation.add((252*index*index*index - 6795*index*index + 57825*index) / 750);
                }
            } else if(isCorner && boardType == 6){ // 육각형 코너
                switch(yut){
                    case -1 -> possibleLocation.add(index + yut);
                    case 1, 2 -> possibleLocation.add(index*10 + yut);
                    case 3 -> possibleLocation.add(boardType*50);
                    case 4 -> possibleLocation.add((-98*index*index*index + 3675*index*index - 37975*index + 299250) / 750);
                    case 5 -> possibleLocation.add((-102*index*index*index + 3825*index*index - 39525*index + 303000) / 750);
                }
            }else if(isDiagonal){
                int temp = index % 10;
                if(boardType == 4){
                    if(index == 51){
                        switch(yut){
                            case -1 -> possibleLocation.add((index + yut) / 10);
                            case 1 -> possibleLocation.add(index + yut);
                            case 2 -> possibleLocation.add(boardType*50);
                            case 3 -> possibleLocation.add(152);
                            case 4 -> possibleLocation.add(151);
                            case 5 -> possibleLocation.add(15);
                        }
                    }else if(index == 101){
                        switch(yut){
                            case -1 -> possibleLocation.add((index + yut) / 10);
                            case 1 -> possibleLocation.add(index + yut);
                            case 2 -> possibleLocation.add(boardType*50);
                            case 3 -> possibleLocation.add(201);
                            case 4 -> possibleLocation.add(202);
                            case 5 -> possibleLocation.add(0);
                        }
                    } else if(index == 102){
                        switch(yut){
                            case -1 -> possibleLocation.add(index + yut);
                            case 1 -> possibleLocation.add(boardType*50);
                            case 2 -> possibleLocation.add(201);
                            case 3 -> possibleLocation.add(202);
                            case 4 -> possibleLocation.add(0);
                            case 5 -> isFinish = true;
                        }
                    } else if(index == 52){
                        switch(yut){
                            case -1 -> possibleLocation.add(index + yut);
                            case 1 -> possibleLocation.add(boardType*50);
                            case 2 -> possibleLocation.add(152);
                            case 3 -> possibleLocation.add(151);
                            case 4 -> possibleLocation.add(15);
                            case 5 -> possibleLocation.add(16);
                        }
                    } else if(index == 152){
                        switch(yut){
                            case -1 -> possibleLocation.add(boardType*50);
                            case 1 -> possibleLocation.add(index - yut);
                            case 2, 3, 4, 5 -> possibleLocation.add(13 + yut);
                        }
                    } else if(index == 151){
                        switch(yut){
                            case -1 -> possibleLocation.add(index - yut);
                            case 1, 2, 3, 4, 5 -> possibleLocation.add(14 + yut);
                        }
                    } else if(index == 201){
                        switch(yut){
                            case -1, 1 -> possibleLocation.add(index + yut);
                            case 2 -> possibleLocation.add(0);
                            case 3, 4, 5 -> isFinish = true;
                        }
                    } else if(index == 202){
                        switch(yut){
                            case -1 -> possibleLocation.add(index + yut);
                            case 1 -> possibleLocation.add(0);
                            case 2, 3, 4, 5 -> isFinish = true;
                        }
                    }
                } else if(boardType == 5){              //오각형
                    if(temp == 1 && index < 150){
                        switch (yut){
                            case -1 -> possibleLocation.add((index + yut) / 10);
                            case 1 -> possibleLocation.add(index + yut);
                            case 2 -> possibleLocation.add(boardType*50);
                            case 3 -> possibleLocation.add(202);
                            case 4 -> possibleLocation.add(201);
                            case 5 -> possibleLocation.add(20);
                        }
                    } else if(temp == 2 && index < 150){
                        switch (yut){
                            case -1 -> possibleLocation.add(index + yut);
                            case 1 -> possibleLocation.add(boardType*50);
                            case 2 -> possibleLocation.add(202);
                            case 3 -> possibleLocation.add(201);
                            case 4 -> possibleLocation.add(20);
                            case 5 -> possibleLocation.add(21);
                        }
                    } else if (index == 151){
                        switch (yut){
                            case -1 -> possibleLocation.add((index + yut) / 10);
                            case 1 -> possibleLocation.add(index + yut);
                            case 2 -> possibleLocation.add(boardType*50);
                            case 3 -> possibleLocation.add(251);
                            case 4 -> possibleLocation.add(252);
                            case 5 -> possibleLocation.add(0);
                        }
                    } else if (index == 152){
                        switch (yut){
                            case -1 -> possibleLocation.add(index + yut);
                            case 1 -> possibleLocation.add(boardType*50);
                            case 2 -> possibleLocation.add(251);
                            case 3 -> possibleLocation.add(252);
                            case 4 -> possibleLocation.add(0);
                            case 5 -> isFinish = true;
                        }
                    } else if (index == 202){
                        switch (yut){
                            case -1 -> possibleLocation.add(boardType*50);
                            case 1 -> possibleLocation.add(201);
                            case 2, 3, 4, 5 -> possibleLocation.add(18 + yut);
                        }
                    } else if (index == 201){
                        switch (yut){
                            case -1 -> possibleLocation.add(index - yut);
                            case 1, 2, 3, 4, 5 -> possibleLocation.add(19 + yut);
                        }
                    } else if(index == 251){
                        switch (yut){
                            case -1 -> possibleLocation.add(boardType*50);
                            case 1 -> possibleLocation.add(252);
                            case 2 -> possibleLocation.add(0);
                            case 3, 4, 5 -> isFinish = true;
                        }
                    } else if(index == 252){
                        switch (yut){
                            case -1 -> possibleLocation.add(251);
                            case 1 -> possibleLocation.add(0);
                            case 2, 3, 4, 5 -> isFinish = true;
                        }
                    }
                } else{         //육각형
                    if(temp == 1 && index < 150){
                        switch (yut){
                            case -1 -> possibleLocation.add((index + yut) / 10);
                            case 1 -> possibleLocation.add(index + yut);
                            case 2 -> possibleLocation.add(boardType*50);
                            case 3 -> possibleLocation.add(252);
                            case 4 -> possibleLocation.add(251);
                            case 5 -> possibleLocation.add(25);
                        }
                    } else if(temp == 2 && index < 150){
                        switch (yut){
                            case -1 -> possibleLocation.add(index + yut);
                            case 1 -> possibleLocation.add(boardType*50);
                            case 2 -> possibleLocation.add(252);
                            case 3 -> possibleLocation.add(251);
                            case 4 -> possibleLocation.add(25);
                            case 5 -> possibleLocation.add(26);
                        }
                    } else if(temp == 1 && index > 150 && index < 250){
                        switch (yut){
                            case -1 -> possibleLocation.add((index + yut) / 10);
                            case 1 -> possibleLocation.add(index + yut);
                            case 2 -> possibleLocation.add(boardType*50);
                            case 3 -> possibleLocation.add(301);
                            case 4 -> possibleLocation.add(302);
                            case 5 -> possibleLocation.add(0);
                        }
                    } else if(temp == 2 && index > 150 && index < 250){
                        switch (yut){
                            case -1 -> possibleLocation.add(index + yut);
                            case 1 -> possibleLocation.add(boardType*50);
                            case 2 -> possibleLocation.add(301);
                            case 3 -> possibleLocation.add(302);
                            case 4 -> possibleLocation.add(0);
                            case 5 -> isFinish = true;
                        }
                    } else if(index == 252){
                        switch (yut){
                            case -1 -> possibleLocation.add(boardType*50);
                            case 1 -> possibleLocation.add(251);
                            case 2, 3, 4, 5 -> possibleLocation.add(23 + yut);
                        }
                    } else if(index == 251){
                        switch (yut){
                            case -1 -> possibleLocation.add(252);
                            case 1, 2, 3, 4, 5 -> possibleLocation.add(24 + yut);
                        }
                    } else if(index == 301){
                        switch (yut){
                            case -1 -> possibleLocation.add(boardType*50);
                            case 1 -> possibleLocation.add(302);
                            case 2 -> possibleLocation.add(0);
                            case 3, 4, 5 -> isFinish = true;
                        }
                    } else if(index == 302){
                        switch (yut){
                            case -1 -> possibleLocation.add(301);
                            case 1 -> possibleLocation.add(0);
                            case 2, 3, 4, 5 -> isFinish = true;
                        }
                    }
                }
            } else {
                if(index + yut > boardType*5){
                    isFinish = true;
                } else if(index + yut == boardType*5) {
                    possibleLocation.add(0);
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
        boolean result = false;
        if(isCenter){
            switch(yut){
                case 4, 5 -> result = true;
            }
        } else if(index == 0){
            switch(yut){
                case 1, 2, 3, 4, 5 -> result = true;
            }
        } else if(isDiagonal){
            int temp = index % 10;
            if(boardType == 4){
                if(index == 201){
                    switch(yut){
                        case 3, 4, 5 -> result = true;
                    }
                } else if(index == 202){
                    switch(yut){
                        case 2, 3, 4, 5 -> result = true;
                    }
                } else if(index == 102 && yut == 5){
                    result = true;
                }
            } else if(boardType == 5){              //오각형
                if (index == 152){
                    if (yut == 5) {
                        result = true;
                    }
                } else if(index == 251){
                    switch (yut){
                        case 3, 4, 5 -> result = true;
                    }
                } else if(index == 252){
                    switch (yut){
                        case 2, 3, 4, 5 -> result = true;
                    }
                }
            } else{         //육각형
                if(temp == 2 && index > 150 && index < 250){
                    if (yut == 5) {
                        result = true;
                    }
                } else if(index == 301){
                    switch (yut){
                        case 3, 4, 5 -> result = true;
                    }
                } else if(index == 302){
                    switch (yut){
                        case 2, 3, 4, 5 -> result = true;
                    }
                }
            }
        } else {
            if(index + yut > boardType*5){
                result = true;
            }
        }
        return result;
    }
}
