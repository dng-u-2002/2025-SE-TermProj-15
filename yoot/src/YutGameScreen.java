import javax.swing.*;
import java.util.Objects;

public class YutGameScreen extends JFrame {
    BoardPanel board;


    YutGameScreen(){
        board = new BoardPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    YutGameScreen(String boardType){
        int point = 0;
        if(Objects.equals(boardType, "사각형"))
            point = 4;
        else if(Objects.equals(boardType, "오각형"))
            point = 5;
        else if(Objects.equals(boardType, "육각형"))
            point = 6;

        board = new BoardPanel(point);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    YutGameScreen(int playerCount, int piecesPerPlayer, String boardType){
        int point = 0;

        if(Objects.equals(boardType, "사각형"))
            point = 4;
        else if(Objects.equals(boardType, "오각형"))
            point = 5;
        else if(Objects.equals(boardType, "육각형"))
            point = 6;

        board = new BoardPanel(point);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
