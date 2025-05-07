package yoot;

import java.util.*;

public class Board {
    private Map<Integer, Location> locations;
    private BoardType boardType;

    public Board(BoardType type) {
        this.boardType = type;
        this.locations = new HashMap<>();

        if (type == BoardType.RECTANGLE) {
            initializeRectangleBoard();
        }
        // 추후 PENTAGON, HEXAGON 추가
    }

    private void initializeRectangleBoard() {
        // 0 ~ 19: 외곽
        for (int i = 0; i <= 19; i++) {
            locations.put(i, new Location(String.valueOf(i), false, i == 0));  // 0이 시작/종료점
        }

        // 중앙
        locations.put(200, new Location("200", true, false));

        // 대각선 경로
        locations.put(51, new Location("51", false, false));
        locations.put(52, new Location("52", false, false));

        locations.put(101, new Location("101", false, false));
        locations.put(102, new Location("102", false, false));

        locations.put(151, new Location("151", false, false));
        locations.put(152, new Location("152", false, false));

        locations.put(201, new Location("201", false, false));
        locations.put(202, new Location("202", false, false));
    }

    public Location getStartLocation() {
        return locations.get(0);
    }

    public Location getNextLocation(Location current, YutResult result) {
        int steps = result.getType().getValue();
        if (steps <= 0) return null;

        // 단순 직진형 이동: 실제 게임 규칙에는 다양한 경로 판단이 필요함
        int currentId = Integer.parseInt(current.getId());
        int nextId = (currentId + steps) % 20;
        return locations.getOrDefault(nextId, null);
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public Map<Integer, Location> getLocations() {
        return locations;
    }
}
