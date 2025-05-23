import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXStartFrame extends Application {
    private ComboBox<Integer> playerCountComboBox;
    private ComboBox<Integer> piecesPerPlayerComboBox;
    private ComboBox<String> boardTypeComboBox;
    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;

        primaryStage.setTitle("윷놀이 (Yut Nori)");

        // 제목
        Label titleLabel = new Label("윷놀이 게임");
        titleLabel.setFont(Font.font("맑은 고딕", 24));
        titleLabel.setPadding(new Insets(20, 0, 20, 0));

        // 설정 패널
        GridPane settingsGrid = new GridPane();
        settingsGrid.setAlignment(Pos.CENTER);
        settingsGrid.setHgap(10);
        settingsGrid.setVgap(20);
        settingsGrid.setPadding(new Insets(20, 50, 20, 50));

        // 플레이어 수
        Label playerCountLabel = new Label("플레이어 수:");
        playerCountLabel.setFont(Font.font("맑은 고딕", 14));
        playerCountComboBox = new ComboBox<>();
        playerCountComboBox.getItems().addAll(1, 2, 3, 4);
        playerCountComboBox.getSelectionModel().select(1);

        // 말 개수
        Label piecesLabel = new Label("말 개수(플레이어당):");
        piecesLabel.setFont(Font.font("맑은 고딕", 14));
        piecesPerPlayerComboBox = new ComboBox<>();
        piecesPerPlayerComboBox.getItems().addAll(1, 2, 3, 4, 5);
        piecesPerPlayerComboBox.getSelectionModel().select(3);

        // 보드 종류
        Label boardTypeLabel = new Label("보드 종류:");
        boardTypeLabel.setFont(Font.font("맑은 고딕", 14));
        boardTypeComboBox = new ComboBox<>();
        boardTypeComboBox.getItems().addAll("사각형", "오각형", "육각형");
        boardTypeComboBox.getSelectionModel().select(0);

        // Grid에 추가
        settingsGrid.add(playerCountLabel, 0, 0);
        settingsGrid.add(playerCountComboBox, 1, 0);
        settingsGrid.add(piecesLabel, 0, 1);
        settingsGrid.add(piecesPerPlayerComboBox, 1, 1);
        settingsGrid.add(boardTypeLabel, 0, 2);
        settingsGrid.add(boardTypeComboBox, 1, 2);

        // 시작 버튼
        Button startButton = new Button("게임 시작");
        startButton.setFont(Font.font("맑은 고딕", 16));
        startButton.setPrefSize(150, 40);
        startButton.setOnAction(e -> startGame());

        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().addAll(titleLabel, settingsGrid, startButton);
        mainLayout.setSpacing(30);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene(mainLayout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startGame() {
        int numPlayers = playerCountComboBox.getValue();
        int piecesPerPlayer = piecesPerPlayerComboBox.getValue();
        String boardType = boardTypeComboBox.getValue();
        int boardTypeInt = switch (boardType) {
            case "오각형" -> 5;
            case "육각형" -> 6;
            default -> 4;
        };

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("게임 시작");
        alert.setHeaderText(null);
        alert.setContentText("게임 설정이 완료되었습니다.\n"
                + "플레이어 수: " + numPlayers + "명\n"
                + "말 개수: " + piecesPerPlayer + "개\n"
                + "보드 종류: " + boardType);
        alert.showAndWait();

        Game game = new Game(numPlayers, piecesPerPlayer, boardTypeInt, mainStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}