import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YutScreen extends JFrame {
    private BoardPanel board;
    private JButton throwButton;
    private JLabel statusLabel;
    private JLabel yutResultLabel;
    private Game gameController;
    private JPanel infoPanel;

    YutScreen() {
        board = new BoardPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    YutScreen(int point) {
        board = new BoardPanel(point);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    YutScreen(int playerCount, int piecesPerPlayer, int point, Game gameController) {
        this.gameController = gameController;

        // 타이틀 설정
        setTitle("윷놀이 게임 - " + point + "각형 판");

        // 메인 레이아웃 설정
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 보드 패널 생성
        board = new BoardPanel(point);
        board.setPreferredSize(new Dimension(600, 600));

        // 상태 및 정보 패널 생성
        createInfoPanel();

        // 게임 설명 패널 생성
        createInstructionPanel();

        // 컴포넌트 배치
        add(board, BorderLayout.CENTER);

        // 프레임 사이즈 및 위치 설정
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 정보 패널 생성 (상태 메시지, 윷 결과 표시)
    private void createInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 게임 상태 레이블
        statusLabel = new JLabel("게임을 시작합니다.");
        statusLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        // 윷 결과 레이블
        yutResultLabel = new JLabel("윷 결과: ");
        yutResultLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        yutResultLabel.setHorizontalAlignment(JLabel.CENTER);

        // 윷 던지기 버튼
        throwButton = new JButton("윷 던지기");
        throwButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        throwButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameController != null) {
                    gameController.throwYutButtonClicked();
                }
            }
        });

        // 패널에 컴포넌트 추가
        infoPanel.add(statusLabel);
        infoPanel.add(yutResultLabel);
        infoPanel.add(throwButton);

        add(infoPanel, BorderLayout.NORTH);
    }

    // 게임 설명 패널 생성
    private void createInstructionPanel() {
        JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new BorderLayout());
        instructionPanel.setBorder(BorderFactory.createTitledBorder("게임 방법"));

        JTextArea instructionText = new JTextArea();
        instructionText.setEditable(false);
        instructionText.setLineWrap(true);
        instructionText.setWrapStyleWord(true);
        instructionText.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        instructionText.setText(
                "1. '윷 던지기' 버튼을 클릭하여 윷을 던집니다.\n" +
                        "2. 이동할 말을 직접 클릭하여 선택합니다.\n" +
                        "   - 왼쪽 상단 시작점에 있는 말을 클릭하여 새 말을 꺼낼 수 있습니다.\n" +
                        "   - 보드 위에 있는 말을 클릭하여 이동할 수 있습니다.\n" +
                        "3. 이동 가능한 위치가 노란색으로 표시됩니다. 클릭하여 이동합니다.\n" +
                        "4. 윷이나 모가 나오면 한 번 더 던길 수 있습니다.\n" +
                        "5. 상대방 말을 잡으면 추가로 윷을 던질 수 있습니다.\n" +
                        "6. 모든 말을 먼저 골인시킨 플레이어가 승리합니다."
        );

        JScrollPane scrollPane = new JScrollPane(instructionText);
        instructionPanel.add(scrollPane, BorderLayout.CENTER);

        add(instructionPanel, BorderLayout.SOUTH);
    }

    // 상태 메시지 업데이트
    public void updateStatusMessage(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }

    // 윷 결과 표시
    public void displayYutResult(String result) {
        if (yutResultLabel != null) {
            yutResultLabel.setText("윷 결과: " + result);
        }
    }

    // 보드 패널 반환
    public BoardPanel getBoard() {
        return board;
    }
}