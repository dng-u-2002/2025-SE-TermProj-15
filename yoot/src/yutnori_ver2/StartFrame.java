package yutnori_ver2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class StartFrame extends JFrame {
    private JComboBox<Integer> playerCountComboBox;
    private JComboBox<Integer> piecesPerPlayerComboBox;
    private JComboBox<String> boardTypeComboBox;
    private JButton startButton;

    public StartFrame() {
        // 프레임 기본 설정
        setTitle("윷놀이 (Yut Nori)");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // 제목 라벨 생성
        JLabel titleLabel = new JLabel("윷놀이 게임", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 설정 패널 생성
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(3, 2, 10, 20));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // 플레이어 수 선택
        JLabel playerCountLabel = new JLabel("플레이어 수:");
        playerCountLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        Integer[] playerCounts = {1, 2, 3, 4};
        playerCountComboBox = new JComboBox<>(playerCounts);
        playerCountComboBox.setSelectedIndex(1); // 기본값 2명으로 설정

        // 말 개수 선택
        JLabel piecesLabel = new JLabel("말 개수(플레이어당):");
        piecesLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        Integer[] piecesCounts = {1, 2, 3, 4, 5};
        piecesPerPlayerComboBox = new JComboBox<>(piecesCounts);
        piecesPerPlayerComboBox.setSelectedIndex(3); // 기본값 4개로 설정

        // 보드 종류 선택
        JLabel boardTypeLabel = new JLabel("보드 종류:");
        boardTypeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        String[] boardTypes = {"사각형", "오각형", "육각형"};
        boardTypeComboBox = new JComboBox<>(boardTypes);

        // 설정 패널에 컴포넌트 추가
        settingsPanel.add(playerCountLabel);
        settingsPanel.add(playerCountComboBox);
        settingsPanel.add(piecesLabel);
        settingsPanel.add(piecesPerPlayerComboBox);
        settingsPanel.add(boardTypeLabel);
        settingsPanel.add(boardTypeComboBox);

        mainPanel.add(settingsPanel, BorderLayout.CENTER);

        // 시작 버튼 패널
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("게임 시작");
        startButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        startButton.setPreferredSize(new Dimension(150, 40));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startGame() {
        int numPlayers = (Integer) playerCountComboBox.getSelectedItem();
        int piecesPerPlayer = (Integer) piecesPerPlayerComboBox.getSelectedItem();
        String boardType = (String) boardTypeComboBox.getSelectedItem();
        int boardTypeInt = 0;

        if(Objects.equals(boardType, "사각형"))
            boardTypeInt = 4;
        else if(Objects.equals(boardType, "오각형"))
            boardTypeInt = 5;
        else if(Objects.equals(boardType, "육각형"))
            boardTypeInt = 6;

        // 선택된 값 확인 (실제로는 게임 화면으로 넘어가는 코드 필요)
        JOptionPane.showMessageDialog(this,
                "게임 설정이 완료되었습니다.\n" +
                        "플레이어 수: " + numPlayers + "명\n" +
                        "말 개수: " + piecesPerPlayer + "개\n" +
                        "보드 종류: " + boardType,
                "게임 시작", JOptionPane.INFORMATION_MESSAGE);

        Game gameScreen = new Game(numPlayers, piecesPerPlayer, boardTypeInt);
    }
}