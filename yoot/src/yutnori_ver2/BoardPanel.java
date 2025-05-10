package yutnori_ver2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class BoardPanel extends JPanel {

    public int n = 500;
    public int center = n/2;
    public int point = 4;
    public int radius = (n - 50) / 2;
    private final int[] arrX;
    private final int[] arrY;
    private final int[] locationX;
    private final int[] locationY;
    private int pieceIndex = 0;

    // 말 표시를 위한 데이터 구조 추가
    private Map<Integer, Map<Integer, Integer>> pieces = new HashMap<>(); // <위치, <플레이어ID, 말개수>>
    private Color[] playerColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW}; // 플레이어별 색상

    // 말 선택 관련 필드 추가
    private int selectedPosition = -1;  // 선택된 말의 위치
    private int currentPlayerId = 1;    // 현재 플레이어 ID
    private Game gameController;        // 게임 컨트롤러 참조

    // 이동 가능한 위치 하이라이트
    private java.util.List<Integer> possibleMoveLocations = new java.util.ArrayList<>();

    BoardPanel(int newPoint) {
        this.point = newPoint;
        this.arrX = new int[point];
        this.arrY = new int[point];
        this.locationX = new int[point*100];
        this.locationY = new int[point*100];

        this.setPreferredSize(new Dimension(n, n));
        this.setBackground(new Color(245, 245, 220)); // 베이지색 배경
        this.setPoints();
        this.setEdges();

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                handleMouseClick(mouseX, mouseY);
            }
        });
    }

    BoardPanel() {
        this.arrX = new int[point];
        this.arrY = new int[point];
        this.locationX = new int[point*100];
        this.locationY = new int[point*100];

        this.setPreferredSize(new Dimension(n, n));
        this.setBackground(new Color(245, 245, 220)); // 베이지색 배경
        this.setPoints();
        this.setEdges();

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                handleMouseClick(mouseX, mouseY);
            }
        });
    }

    // 게임 컨트롤러 설정
    public void setGameController(Game gameController) {
        this.gameController = gameController;
    }

    // 현재 플레이어 설정
    public void setCurrentPlayer(int playerId) {
        this.currentPlayerId = playerId;
    }

    public void paint(Graphics g) {
        super.paint(g); // 기존 그리기 내용 지우기

        Graphics2D g2D = (Graphics2D) g;

        // 안티앨리어싱 적용
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 도형 그리기
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(2)); // 선 두께 지정
        g2D.drawPolygon(arrX, arrY, point);     //도형 선 그려주기

        // 내부선 그리기
        for(int i = 0; i < point; i++) {
            g2D.drawLine(arrX[i], arrY[i], center, center);
        }

        // 경로 위의 원 그리기
        for(int i = 0; i < point*100; i++) {
            if(locationX[i] != 0) {
                // 이동 가능한 위치 하이라이트
                if (possibleMoveLocations.contains(i)) {
                    // 이동 가능한 위치는 밝은 노란색으로 표시
                    g2D.setPaint(new Color(255, 255, 150));
                    g2D.fillOval(locationX[i] - 19, locationY[i] - 19, 38, 38);
                }

                // 선택된 위치 하이라이트
                if (i == selectedPosition) {
                    // 선택된 위치는 밝은 파란색으로 표시
                    g2D.setPaint(new Color(173, 216, 230)); // 밝은 파란색
                    g2D.fillOval(locationX[i] - 19, locationY[i] - 19, 38, 38);
                }

                // 흰색 원 그리기
                g2D.setPaint(Color.white);
                g2D.fillOval(locationX[i] - 17, locationY[i] - 17, 34, 34);

                // 검정색 테두리 그리기
                g2D.setPaint(Color.black);
                g2D.drawOval(locationX[i] - 17, locationY[i] - 17, 34, 34);

                // 위치 번호 표시
                g2D.setFont(new Font("Arial", Font.PLAIN, 10));
                g2D.drawString(Integer.toString(i), locationX[i] - 10, locationY[i]);

                // 갈림길과 출발점에 더블 원 그리기
                if(i % 5 == 0) {
                    g2D.drawOval(locationX[i] - 15, locationY[i] - 15, 30, 30);
                }

                // 출발 표기
                if(i == 0) {
                    g2D.setFont(new Font("맑은 고딕", Font.BOLD, 12));
                    g2D.drawString("출발", locationX[i] - 12, locationY[i] + 6);
                }

                // 해당 위치에 말이 있으면 그리기
                if (pieces.containsKey(i)) {
                    drawPieces(g2D, i);
                }
            }
        }

        // 시작 지점에 말이 있는 경우 표시
        drawStartPieces(g2D);
    }

    // 시작 지점에 있는 말 그리기
    private void drawStartPieces(Graphics2D g2D) {
        // 시작 지점 위치 (BoardPanel 왼쪽 상단에 시각적으로 표시)
        int startX = 50;
        int startY = 50;

        g2D.setColor(Color.WHITE);
        g2D.fillRect(startX - 10, startY - 10, 100, 40);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(startX - 10, startY - 10, 100, 40);

        g2D.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        g2D.drawString("시작 지점", startX, startY + 5);

        // 현재 플레이어의 시작 말만 클릭 가능하게 하이라이트
        if (gameController != null) {
            Player currentPlayer = gameController.getCurrentPlayer();
            if (currentPlayer != null && currentPlayer.pieceAtStart > 0) {
                g2D.setColor(playerColors[(currentPlayer.getId() - 1) % playerColors.length]);
                g2D.fillOval(startX + 70, startY + 5, 15, 15);
                g2D.setColor(Color.WHITE);
                g2D.drawString(String.valueOf(currentPlayer.pieceAtStart), startX + 74, startY + 17);
            }
        }
    }

    // 특정 위치에 있는 모든 플레이어의 말 그리기
    private void drawPieces(Graphics2D g2D, int position) {
        Map<Integer, Integer> playerPieces = pieces.get(position);

        if (playerPieces == null || playerPieces.isEmpty()) {
            return;
        }

        // 위치당 플레이어 수에 따라 말 배치 조정
        int playerCount = playerPieces.size();
        int angle = 0;

        for (Map.Entry<Integer, Integer> entry : playerPieces.entrySet()) {
            int playerId = entry.getKey();
            int pieceCount = entry.getValue();

            // 플레이어 색상 설정 (1부터 시작하므로 인덱스 조정)
            g2D.setPaint(playerColors[(playerId - 1) % playerColors.length]);

            // 말 위치 계산 (원 주위에 배치)
            int offsetX = 0;
            int offsetY = 0;

            if (playerCount > 1) {
                // 여러 플레이어가 같은 위치에 있을 경우 원형으로 배치
                double radian = Math.toRadians(angle);
                offsetX = (int)(Math.cos(radian) * 15);
                offsetY = (int)(Math.sin(radian) * 15);
                angle += 360 / playerCount;
            }

            // 말 그리기
            g2D.fillOval(locationX[position] - 10 + offsetX,
                    locationY[position] - 10 + offsetY,
                    20, 20);

            // 말 개수가 1개 이상이면 숫자 표시
            if (pieceCount > 1) {
                g2D.setColor(Color.WHITE);
                g2D.setFont(new Font("Arial", Font.BOLD, 10));
                g2D.drawString(String.valueOf(pieceCount),
                        locationX[position] - 3 + offsetX,
                        locationY[position] + 4 + offsetY);
            }
        }
    }

    // 마우스 클릭 처리
    private void handleMouseClick(int mouseX, int mouseY) {
        if (gameController == null) {
            return;
        }

        Game.GameState currentState = gameController.getCurrentState();

        // 시작 지점 말 선택 처리
        if (currentState == Game.GameState.WAITING_FOR_PIECE_SELECTION) {
            // 시작 지점 영역 체크 (왼쪽 상단의 직사각형 영역)
            if (mouseX >= 40 && mouseX <= 150 && mouseY >= 40 && mouseY <= 90) {
                Player currentPlayer = gameController.getCurrentPlayer();
                if (currentPlayer != null && currentPlayer.pieceAtStart > 0) {
                    // 시작 지점 말 선택
                    gameController.pieceSelected(999); // 999는 시작 지점을 나타내는 특별한 값
                    return;
                }
            }
        }

        // 보드 위의 말 선택 처리
        int threshold = 17;
        for (int i = 0; i < point*100; i++) {
            if (locationX[i] != 0) {
                int dx = locationX[i] - mouseX;
                int dy = locationY[i] - mouseY;
                double distance = Math.sqrt(dx*dx + dy*dy);

                if (distance <= threshold) {
                    // 게임 상태에 따라 다르게 처리
                    if (currentState == Game.GameState.WAITING_FOR_PIECE_SELECTION) {
                        // 말 선택 모드: 현재 플레이어의 말이 있는 위치만 선택 가능
                        if (pieces.containsKey(i) && pieces.get(i).containsKey(currentPlayerId)) {
                            selectedPosition = i;
                            gameController.pieceSelected(i);
                        }
                    } else if (currentState == Game.GameState.WAITING_FOR_MOVE_SELECTION) {
                        // 이동 위치 선택 모드: 가능한 이동 위치만 선택 가능
                        if (possibleMoveLocations.contains(i)) {
                            gameController.moveLocationSelected(i);
                            clearPossibleMoves();
                        }
                    }

                    repaint();
                    break;
                }
            }
        }
    }

    // 선택된 말 표시 및 가능한 이동 위치 하이라이트
    public void selectPiece(int position) {
        selectedPosition = position;
        repaint();
    }

    // 이동 가능한 위치 설정
    public void setPossibleMoveLocations(java.util.List<Integer> locations) {
        possibleMoveLocations.clear();
        possibleMoveLocations.addAll(locations);
        repaint();
    }

    // 이동 가능한 위치 지우기
    public void clearPossibleMoves() {
        selectedPosition = -1;
        possibleMoveLocations.clear();
        repaint();
    }

    // 말 추가
    public void addPiece(int position, int playerId, int count) {
        if (!pieces.containsKey(position)) {
            pieces.put(position, new HashMap<>());
        }
        pieces.get(position).put(playerId, count);
        repaint();
    }

    // 말 제거
    public void removePiece(int position, int playerId) {
        if (pieces.containsKey(position)) {
            pieces.get(position).remove(playerId);
            if (pieces.get(position).isEmpty()) {
                pieces.remove(position);
            }
            repaint();
        }
    }

    // 모든 말 제거
    public void clearPieces() {
        pieces.clear();
        repaint();
    }

    public void setPoints() {                            //도형의 꼭짓점 좌표 저장
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

    public void updatePiecePosition(int mouseX, int mouseY) {
        int threshold = 17;
        for (int i = 0; i < point*100; i++) {
            if(locationX[i] != 0) {
                int dx = locationX[i] - mouseX;
                int dy = locationY[i] - mouseY;
                double distance = Math.sqrt(dx*dx + dy*dy);
                if (distance <= threshold) {
                    pieceIndex = i;
                    repaint();
                    break;
                }
            }
        }
    }

    // 특정 위치로 현재 선택된 말 이동
    public void updatePiecePosition(int position) {
        pieceIndex = position;
        repaint();
    }
}