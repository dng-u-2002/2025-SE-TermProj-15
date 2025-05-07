import java.awt.*;
import javax.swing.*;

public class BoardFrame extends JFrame {

    BoardPanel board;
    BoardFrame(){
        board = new BoardPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


}