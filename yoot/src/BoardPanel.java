import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public int n = 500;
    public int center = n/2;
    public int point = 6;
    public int radius = (n - 100) / 2;
    public int circleSize = 20;
    private int[] arrX = new int[point];
    private int[] arrY = new int[point];
    private int[] locationX = new int[point*100];
    private int[] locationY = new int[point*100];


    BoardPanel(){
        this.setPreferredSize(new Dimension(n, n));
        this.setPoints();
        this.setEdges();

    }

    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawPolygon(arrX, arrY, point);
        for(int i = 0; i<point; i++){
            g2D.drawLine(arrX[i], arrY[i], center, center);
        }
        for(int i = 0; i < point*100; i++){
            if(locationX[i] != 0){
                g2D.setPaint(Color.white);
                g2D.fillOval(locationX[i] - 15, locationY[i] - 15, 30, 30);
                g2D.setPaint(Color.black);
                g2D.drawOval(locationX[i] - 15, locationY[i] - 15, 30, 30);
                //g2D.drawString(Integer.toString(i), locationX[i] - 10, locationY[i]);
                if(i % 5 == 0){
                    g2D.drawOval(locationX[i] - 13, locationY[i] - 13, 26, 26);
                }
            }
        }




    }

    public void setPoints(){
        double angle = 2*Math.PI / point;
        double offset = 0;
        if (point%2 == 0) {
            offset = 0;
        } else{
            offset = -Math.PI / 2;
        }
        for(int i = 0; i < point; i++){
            double theta = i * angle + offset;
            double x, y;
            x = center + radius * Math.cos(theta);
            y = center + radius * Math.sin(theta);
            arrX[i] = (int)x;
            arrY[i] = (int)y;
        }
    }

    public void setEdges(){
        for(int i = 0; i < point; i++){
            int next = (i+1) % point;
            locationX[5*i] = arrX[i];
            locationY[5*i] = arrY[i];
            for(int j = 1; j<5; j++){
                locationX[5*i + j] = (j*arrX[next] + (5-j)*arrX[i])/5;
                locationY[5*i + j] = (j*arrY[next] + (5-j)*arrY[i])/5;
            }
            if(i != 0){
                locationX[50*i + 1] = (arrX[i] + 2*center)/3;
                locationX[50*i + 2] = (2*arrX[i] + center)/3;
                locationY[50*i + 1] = (arrY[i] + 2*center)/3;
                locationY[50*i + 2] = (2*arrY[i] + center)/3;
            } else{
                locationX[50*point] = center;
                locationX[50*point + 1] = (arrX[i] + 2*center)/3;
                locationX[50*point + 2] = (2*arrX[i] + center)/3;
                locationY[50*point] = center;
                locationY[50*point + 1] = (arrY[i] + 2*center)/3;
                locationY[50*point + 2] = (2*arrY[i] + center)/3;
            }
        }
    }
}
