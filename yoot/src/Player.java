public class Player {
    private final int id;
    public int pieceNum;
    public int pieceAtStart;
    public int pieceAtEnd;
    public int boardType = 1;
    public int[] pieceIndex;

    public Player(int id, int pieceNum, int gameType) {
        this.id = id;
        this.pieceNum = pieceNum;
        this.pieceAtStart = pieceNum;
        this.pieceAtEnd = 0;
        this.boardType = gameType;
        this.pieceIndex = new int[gameType*100];
        System.out.println("player " + id + " entered");
    }

    public boolean hasWon() {
        return pieceAtEnd == pieceNum;
    }

    public int getId() {
        return id;
    }
    
    public boolean allStart() {
    	return pieceAtStart + pieceAtEnd == pieceNum;
    }


    //public int getPiecePlace() {}

    public void goal(int num){ pieceAtEnd+= num; }

    public void exitStart(){ pieceAtStart --; }

    public boolean pieceCaught(int index){
        if(pieceIndex[index] != 0){
            pieceAtStart += pieceIndex[index];
            pieceIndex[index] = 0;
            System.out.println("잡았다");
            return true;
        } else{
            return false;
        }
    }
    public void getPlayerInfo() {
        System.out.println("player" + id + "pieceNum: " + pieceNum);
        System.out.println("starting: " + pieceAtStart + ", ended: " + pieceAtEnd);
        for (int i = 0; i < boardType*100; i++){
            if(pieceIndex[i] != 0){
                System.out.println(pieceIndex[i] + "piece(s) at" + i);
            }
        }
    }
}
