import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
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

        board = new FXBoardPanel(boardType, playerCount, piecesPerPlayer);

        statusLabel = new Label("게임을 시작합니다.");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        statusLabel.setWrapText(true);
        statusLabel.setMaxWidth(Double.MAX_VALUE);

        yutResultLabel = new Label("윷 결과: ");
        yutResultLabel.setStyle("-fx-font-size: 14px;");
        yutResultLabel.setWrapText(true);
        yutResultLabel.setMaxWidth(Double.MAX_VALUE);

        throwButton = new Button("윷 던지기");
        throwButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        throwButton.setPrefSize(120, 50);
        throwButton.setOnAction(e -> gameController.throwYutButtonClicked());

        VBox leftBox = new VBox(statusLabel);
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.setPadding(new Insets(10));
        leftBox.setPrefWidth(600);
        leftBox.setFillWidth(true);


        VBox rightBox = new VBox(10, throwButton, yutResultLabel);
        rightBox.setAlignment(Pos.TOP_RIGHT);
        rightBox.setPrefWidth(150);
        rightBox.setMaxHeight(Region.USE_PREF_SIZE);
        rightBox.setPadding(new Insets(10));

        HBox topPanel = new HBox(20, leftBox, rightBox);
        topPanel.setAlignment(Pos.TOP_LEFT);
        topPanel.setPadding(new Insets(10));

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

        root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(topPanel, board.getPane(), scrollPane);

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
