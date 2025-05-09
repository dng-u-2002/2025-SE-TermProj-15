package yutnori_ver2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {

    public int n = 500;
    public int center = n/2;
    public int point = 4;
    public int radius = (n - 50) / 2;
    private final int[] arrX = new int[point];
    private final int[] arrY = new int[point];
    private final int[] locationX = new int[point*100];
    private final int[] locationY = new int[point*100];
    private int pieceIndex = 0;

    BoardPanel(int newPoint){
        this.point = newPoint;
        this.setPreferredSize(new Dimension(n, n));
        this.setPoints();
        this.setEdges();

        this.addMouseListener(new MouseAdapter() {      //마우스 입력값 받아오기ㅐ
            public void mouseClicked(MouseEvent e){
                int mouseX = e.getX();
                int mouseY = e.getY();
                updatePiecePosition(mouseX, mouseY);
            }
        });
    }

    BoardPanel(){
        this.setPreferredSize(new Dimension(n, n));
        this.setPoints();
        this.setEdges();

        this.addMouseListener(new MouseAdapter() {      //마우스 입력값 받아오기ㅐ
            public void mouseClicked(MouseEvent e){
                int mouseX = e.getX();
                int mouseY = e.getY();
                updatePiecePosition(mouseX, mouseY);
            }
        });
    }

    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawPolygon(arrX, arrY, point);     //도형 선 그려주기
        for(int i = 0; i<point; i++){           //도형 내부 선 그려주기
            g2D.drawLine(arrX[i], arrY[i], center, center);
        }
        for(int i = 0; i < point*100; i++){     //도형 위에 원 그리기
            if(locationX[i] != 0){
                //도형 위에 흰색 원으로 덮고
                g2D.setPaint(Color.white);
                g2D.fillOval(locationX[i] - 17, locationY[i] - 17, 34, 34);
                //도형 테두리 검정색 원으로 그리기
                g2D.setPaint(Color.black);
                g2D.drawOval(locationX[i] - 17, locationY[i] - 17, 34, 34);
                g2D.drawString(Integer.toString(i), locationX[i] - 10, locationY[i]);         //각 원 인덱스 확인용(디버깅 때 사용)
                if(i % 5 == 0) {       //갈림길과 출발점에 더블 원 그리기
                    g2D.drawOval(locationX[i] - 15, locationY[i] - 15, 30, 30);
                }
                if(i == 0){     //출발 표기
                    g2D.drawString("출발", locationX[i] - 12, locationY[i] +6);
                }
            }
        }
        if(locationX[pieceIndex] != 0){     //말 표시
            g2D.setPaint(Color.RED);
            g2D.fillOval(locationX[pieceIndex] - 10, locationY[pieceIndex] - 10, 20, 20);
        }
    }

    public void setPoints(){                            //도형의 꼭짓점 좌표 저장
        double angle = 2*Math.PI / point;
        double offset = 0;
        if (point%4 == 0) {
            offset = Math.PI / 4;
        } else if(point%2==0){
            offset = 0;
        } else{
            offset = -Math.PI / 2;
        }
        for(int i = 0; i < point; i++){
            double theta = -i * angle + offset;
            double x, y;
            x = center + radius * Math.cos(theta);
            y = center + radius * Math.sin(theta);
            arrX[i] = (int)x;
            arrY[i] = (int)y;
        }
    }

    public void setEdges(){                             //각 원의 위치 구하기
        for(int i = 0; i < point; i++){
            int next = (i+1) % point;
            locationX[5*i] = arrX[i];       //꼭짓점 좌표 저장
            locationY[5*i] = arrY[i];
            for(int j = 1; j<5; j++){       //도형의 선분 위에 있는 점들 내분점 공식으로 구해줌
                locationX[5*i + j] = (j*arrX[next] + (5-j)*arrX[i])/5;
                locationY[5*i + j] = (j*arrY[next] + (5-j)*arrY[i])/5;
            }
            if(i != 0){     //도형 내부에 있는 원들 내분점 공식 이용해 위치 특정
                locationX[50*i + 1] = (2*arrX[i] + center)/3;
                locationX[50*i + 2] = (arrX[i] + 2*center)/3;
                locationY[50*i + 1] = (2*arrY[i] + center)/3;
                locationY[50*i + 2] = (arrY[i] + 2*center)/3;
            } else{     //출발점과 중심점에 해당하는 원들의 좌표
                locationX[50*point] = center;
                locationX[50*point + 1] = (arrX[i] + 2*center)/3;
                locationX[50*point + 2] = (2*arrX[i] + center)/3;
                locationY[50*point] = center;
                locationY[50*point + 1] = (arrY[i] + 2*center)/3;
                locationY[50*point + 2] = (2*arrY[i] + center)/3;
            }
        }
    }

    public void updatePiecePosition(int mouseX, int mouseY){        //클릭한 위치로 말 이동
        int threshold = 17;
        for (int i = 0; i<point*100; i++){
            if(locationX[i] != 0){
                int dx = locationX[i] - mouseX;
                int dy = locationY[i] - mouseY;
                double distance = Math.sqrt(dx*dx + dy*dy);
                if (distance <= threshold){
                    pieceIndex = i;
                    repaint();
                    break;
                }
            }
        }
    }
}
