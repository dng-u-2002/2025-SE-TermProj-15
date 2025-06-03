import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.*;

public class FXBoardPanel {
    private static final int SIZE = 500;
    private static final int CENTER = SIZE / 2;
    private static final int RADIUS = (SIZE - 50) / 2;

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final int point;

    private final int[] arrX;
    private final int[] arrY;
    private final int[] locationX;
    private final int[] locationY;

    private Map<Integer, Map<Integer, Integer>> pieces = new HashMap<>();
    private final Color[] playerColors = {Color.RED, Color.BLUE, Color.GREEN, Color.GOLD};

    private int selectedPosition = -1;
    private int currentPlayerId = 1;
    private Game gameController;
    private final List<Integer> possibleMoveLocations = new ArrayList<>();

    private final StackPane root;

    public FXBoardPanel(int point, int playerCount, int piecesPerPlayer) {
        this.point = point;
        this.arrX = new int[point];
        this.arrY = new int[point];
        this.locationX = new int[point * 100];
        this.locationY = new int[point * 100];

        this.canvas = new Canvas(SIZE, SIZE);
        this.gc = canvas.getGraphicsContext2D();
        this.root = new StackPane(canvas);

        setPoints();
        setEdges();
        drawBoard();

        canvas.setOnMouseClicked(this::handleMouseClick);
    }

    public StackPane getPane() {
        return root;
    }

    public void setGameController(Game gameController) {
        this.gameController = gameController;
    }

    public void setCurrentPlayer(int playerId) {
        this.currentPlayerId = playerId;
    }

    public void selectPiece(int position) {
        this.selectedPosition = position;
        drawBoard();
    }

    public void setPossibleMoveLocations(List<Integer> locations) {
        possibleMoveLocations.clear();
        possibleMoveLocations.addAll(locations);
        drawBoard();
    }

    public void clearPossibleMoves() {
        selectedPosition = -1;
        possibleMoveLocations.clear();
        drawBoard();
    }

    public void addPiece(int position, int playerId, int count) {
        pieces.computeIfAbsent(position, k -> new HashMap<>()).put(playerId, count);
        drawBoard();
    }

    public void clearPieces() {
        pieces.clear();
        drawBoard();
    }

    private void drawBoard() {
        gc.clearRect(0, 0, SIZE, SIZE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        gc.strokePolygon(toDoubleArray(arrX), toDoubleArray(arrY), point);
        for (int i = 0; i < point; i++) {
            gc.strokeLine(arrX[i], arrY[i], CENTER, CENTER);
        }

        for (int i = 0; i < point * 100; i++) {
            if (locationX[i] != 0) {
                if (possibleMoveLocations.contains(i)) {
                    gc.setFill(Color.LIGHTCORAL);
                    gc.fillOval(locationX[i] - 22, locationY[i] - 22, 44, 44);
                }
                if (i == selectedPosition) {
                    gc.setFill(Color.LIGHTBLUE);
                    gc.fillOval(locationX[i] - 19, locationY[i] - 19, 38, 38);
                }

                gc.setFill(Color.WHITE);
                gc.fillOval(locationX[i] - 17, locationY[i] - 17, 34, 34);
                gc.setStroke(Color.BLACK);
                gc.strokeOval(locationX[i] - 17, locationY[i] - 17, 34, 34);
                gc.setFont(Font.font("Arial", 10));
                gc.strokeText(String.valueOf(i), locationX[i] - 10, locationY[i]);

                if (i % 5 == 0) {
                    gc.strokeOval(locationX[i] - 15, locationY[i] - 15, 30, 30);
                }

                if (i == 0) {
                    gc.setFont(Font.font("맑은 고딕", 12));
                    gc.strokeText("출발", locationX[i] - 12, locationY[i] + 6);
                }

                if (pieces.containsKey(i)) {
                    drawPiecesAt(i);
                }
            }
        }

        gc.setFill(Color.WHITE);
        gc.fillRect(10, 10, 100, 40);  // 왼쪽 위로 이동
        gc.setStroke(Color.BLACK);
        gc.strokeRect(10, 10, 100, 40);  // 왼쪽 위로 이동
        gc.setFont(Font.font("맑은 고딕", 12));
        gc.strokeText("시작 지점", 20, 30);  // 텍스트 위치도 왼쪽 위로 이동


        if (gameController != null) {
            Player currentPlayer = gameController.getCurrentPlayer();
            if (currentPlayer != null && currentPlayer.pieceAtStart > 0) {
                gc.setFill(playerColors[(currentPlayer.getId() - 1) % playerColors.length]);
                gc.fillOval(82, 22, 15, 15);
                gc.setFill(Color.WHITE);
                gc.fillText(String.valueOf(currentPlayer.pieceAtStart), 86, 34);
            }
        }
    }

    private void drawPiecesAt(int position) {
        Map<Integer, Integer> playerPieces = pieces.get(position);
        if (playerPieces == null || playerPieces.isEmpty()) return;

        int angle = 0;
        int playerCount = playerPieces.size();
        for (Map.Entry<Integer, Integer> entry : playerPieces.entrySet()) {
            int playerId = entry.getKey();
            int count = entry.getValue();

            double rad = Math.toRadians(angle);
            int offsetX = playerCount > 1 ? (int) (Math.cos(rad) * 15) : 0;
            int offsetY = playerCount > 1 ? (int) (Math.sin(rad) * 15) : 0;
            angle += 360 / playerCount;

            gc.setFill(playerColors[(playerId - 1) % playerColors.length]);
            gc.fillOval(locationX[position] - 10 + offsetX, locationY[position] - 10 + offsetY, 20, 20);

            if (count > 1) {
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Arial", 10));
                gc.fillText(String.valueOf(count), locationX[position] - 3 + offsetX, locationY[position] + 4 + offsetY);
            }
        }
    }

    private void handleMouseClick(MouseEvent e) {
        if (gameController == null) return;

        Game.GameState state = gameController.getCurrentState();

        if (state == Game.GameState.WAITING_FOR_PIECE_SELECTION && inStartArea(e.getX(), e.getY())) {
            Player currentPlayer = gameController.getCurrentPlayer();
            if (currentPlayer != null && currentPlayer.pieceAtStart > 0) {
                gameController.pieceSelected(999);
                return;
            }
        }

        for (int i = 0; i < point * 100; i++) {
            if (locationX[i] != 0) {
                double dx = locationX[i] - e.getX();
                double dy = locationY[i] - e.getY();
                if (Math.hypot(dx, dy) <= 17) {
                    if (state == Game.GameState.WAITING_FOR_PIECE_SELECTION &&
                            pieces.containsKey(i) &&
                            pieces.get(i).containsKey(currentPlayerId)) {
                        selectedPosition = i;
                        gameController.pieceSelected(i);
                    } else if (state == Game.GameState.WAITING_FOR_MOVE_SELECTION && possibleMoveLocations.contains(i)) {
                        gameController.moveLocationSelected(i);
                        clearPossibleMoves();
                    }
                    drawBoard();
                    break;
                }
            }
        }
    }

    private boolean inStartArea(double x, double y) {
        return x >= 72 && x <= 92 && y >= 12 && y <= 32;
    }

    private double[] toDoubleArray(int[] arr) {
        return Arrays.stream(arr).asDoubleStream().toArray();
    }

    private void setPoints() {
        double angle = 2 * Math.PI / point;
        double offset = (point % 4 == 0) ? Math.PI / 4 : (point % 2 == 0 ? 0 : -Math.PI / 2);

        for (int i = 0; i < point; i++) {
            double theta = -i * angle + offset;
            arrX[i] = (int) (CENTER + RADIUS * Math.cos(theta));
            arrY[i] = (int) (CENTER + RADIUS * Math.sin(theta));
        }
    }

    private void setEdges() {
        for (int i = 0; i < point; i++) {
            int next = (i + 1) % point;
            locationX[5 * i] = arrX[i];
            locationY[5 * i] = arrY[i];

            for (int j = 1; j < 5; j++) {
                locationX[5 * i + j] = (j * arrX[next] + (5 - j) * arrX[i]) / 5;
                locationY[5 * i + j] = (j * arrY[next] + (5 - j) * arrY[i]) / 5;
            }

            if (i != 0) {
                locationX[50 * i + 1] = (2 * arrX[i] + CENTER) / 3;
                locationX[50 * i + 2] = (arrX[i] + 2 * CENTER) / 3;
                locationY[50 * i + 1] = (2 * arrY[i] + CENTER) / 3;
                locationY[50 * i + 2] = (arrY[i] + 2 * CENTER) / 3;
            } else {
                locationX[50 * point] = CENTER;
                locationX[50 * point + 1] = (arrX[i] + 2 * CENTER) / 3;
                locationX[50 * point + 2] = (2 * arrX[i] + CENTER) / 3;
                locationY[50 * point] = CENTER;
                locationY[50 * point + 1] = (arrY[i] + 2 * CENTER) / 3;
                locationY[50 * point + 2] = (2 * arrY[i] + CENTER) / 3;
            }
        }
    }
}