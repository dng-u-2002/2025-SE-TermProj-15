package yutnori_ver2;

import javax.swing.*;
import java.util.Objects;

public class YutScreen extends JFrame {
    BoardPanel board;


    YutScreen(){
        board = new BoardPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    YutScreen(int point){

        board = new BoardPanel(point);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    YutScreen(int playerCount, int piecesPerPlayer, int point){

        board = new BoardPanel(point);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
