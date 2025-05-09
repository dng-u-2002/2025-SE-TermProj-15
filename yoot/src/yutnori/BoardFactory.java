package yutnori;

public class BoardFactory {

    public static void buildRectangleBoard(Board board) {
        System.out.println("[사각형 판]을 구성합니다.");

        // 1. 외곽 위치 (0~19)
        for (int i = 0; i < 20; i++) {
            boolean isStart = (i == 0);
            boolean isEnd = (i == 0);
            board.addLocation(new Location(String.valueOf(i), false, isEnd, isStart));
        }

        // 2. 대각선 및 특수 노드 추가
        board.addLocation(new Location("51", false, false, false));
        board.addLocation(new Location("52", false, false, false));
        board.addLocation(new Location("101", false, false, false));
        board.addLocation(new Location("102", false, false, false));
        board.addLocation(new Location("151", false, false, false));
        board.addLocation(new Location("152", false, false, false));
        board.addLocation(new Location("200", true, false, false));
        board.addLocation(new Location("201", false, false, false));
        board.addLocation(new Location("202", false, false, false));

        // 3. 외곽 기본 루프 연결 (0~19 시계 방향)
        for (int i = 0; i < 20; i++) {
            int next = (i + 1) % 20;
            board.addPath(new Path(
                    board.getLocationById(String.valueOf(i)),
                    board.getLocationById(String.valueOf(next)),
                    1
            ));
        }

        // 4. 대각선 진입 경로 (정지 시 분기)
        board.addPath(new Path(board.getLocationById("5"), board.getLocationById("51"), 1));
        board.addPath(new Path(board.getLocationById("51"), board.getLocationById("52"), 1));
        board.addPath(new Path(board.getLocationById("52"), board.getLocationById("200"), 1));

        board.addPath(new Path(board.getLocationById("10"), board.getLocationById("101"), 1));
        board.addPath(new Path(board.getLocationById("101"), board.getLocationById("102"), 1));
        board.addPath(new Path(board.getLocationById("102"), board.getLocationById("200"), 1));

        // 5. 중앙 이후 경로: 200 → 152 → 151 → 15 → 16
        board.addPath(new Path(board.getLocationById("200"), board.getLocationById("152"), 1));
        board.addPath(new Path(board.getLocationById("152"), board.getLocationById("151"), 1));
        board.addPath(new Path(board.getLocationById("151"), board.getLocationById("15"), 1));

        // 6. 중앙 이후 일반 경로 (정지 시): 200 → 201 → 202 → 0
        board.addPath(new Path(board.getLocationById("200"), board.getLocationById("201"), 1));
        board.addPath(new Path(board.getLocationById("201"), board.getLocationById("202"), 1));
        board.addPath(new Path(board.getLocationById("202"), board.getLocationById("0"), 1));
    }

    public static void buildPentagonBoard(Board board) {
        System.out.println("[오각형 판]은 아직 구현되지 않았습니다.");
    }

    public static void buildHexagonBoard(Board board) {
        System.out.println("[육각형 판]은 아직 구현되지 않았습니다.");
    }
}
