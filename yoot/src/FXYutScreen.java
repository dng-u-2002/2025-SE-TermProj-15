import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXYutScreen {
    private FXBoardPanel board;
    private Button throwButton;
    private Label statusLabel;
    private Label yutResultLabel;
    private Game gameController;
    private VBox root;
    private Stage stage;

    public FXYutScreen(int playerCount, int piecesPerPlayer, int boardType, Game gameController, FXGameUI ui, Stage stage) {
        this.gameController = gameController;
        this.stage = stage;

        // 보드 생성
        board = new FXBoardPanel(boardType, playerCount, piecesPerPlayer);

        // 상태 레이블
        statusLabel = new Label("게임을 시작합니다.");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // 윷 결과 레이블
        yutResultLabel = new Label("윷 결과: ");
        yutResultLabel.setStyle("-fx-font-size: 14px;");

        // 윷 던지기 버튼
        throwButton = new Button("윷 던지기");
        throwButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        throwButton.setOnAction(e -> gameController.throwYutButtonClicked());

        // 정보 패널
        GridPane infoPanel = new GridPane();
        infoPanel.setVgap(10);
        infoPanel.setPadding(new Insets(10));
        infoPanel.add(statusLabel, 0, 0);
        infoPanel.add(yutResultLabel, 0, 1);
        infoPanel.add(throwButton, 0, 2);

        // 게임 방법
        TextArea instructionText = new TextArea();
        instructionText.setText(
                "1. '윷 던지기' 버튼을 클릭하여 윷을 던집니다.\n" +
                        "2. 이동할 말을 직접 클릭하여 선택합니다.\n" +
                        "   - 왼쪽 상단 시작점에 있는 말을 클릭하여 새 말을 꺼낼 수 있습니다.\n" +
                        "   - 보드 위에 있는 말을 클릭하여 이동할 수 있습니다.\n" +
                        "3. 이동 가능한 위치가 노란색으로 표시됩니다. 클릭하여 이동합니다.\n" +
                        "4. 윷이나 모가 나오면 한 번 더 던질 수 있습니다.\n" +
                        "5. 상대방 말을 잡으면 추가로 윷을 던질 수 있습니다.\n" +
                        "6. 모든 말을 먼저 골인시킨 플레이어가 승리합니다."
        );
        instructionText.setWrapText(true);
        instructionText.setEditable(false);
        instructionText.setStyle("-fx-font-size: 12px;");

        ScrollPane scrollPane = new ScrollPane(instructionText);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));

        // 전체 레이아웃
        root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(infoPanel, board.getPane(), scrollPane);

        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("윷놀이 게임 - " + boardType + "각형 판");
        stage.setScene(scene);
        stage.show();
    }

    public void updateStatusMessage(String message) {
        statusLabel.setText(message);
    }

    public void displayYutResult(String result) {
        yutResultLabel.setText("윷 결과: " + result);
    }

    public FXBoardPanel getBoard() {
        return board;
    }

    public VBox getPane() {
        return root;
    }

    public void close() {
        stage.close();
    }
}
