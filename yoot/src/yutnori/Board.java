package yutnori;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Location> locations;
    private List<Path> paths;
    private BoardType type;

    public Board(BoardType type) {
        this.type = type;
        this.locations = new ArrayList<>();
        this.paths = new ArrayList<>();
        initializeBoard();
    }

    private void initializeBoard() {
        System.out.println("[" + type + "] 형태의 윷놀이 판을 초기화합니다.");
        switch (type) {
            case RECTANGLE:
                BoardFactory.buildRectangleBoard(this);
                break;
            case PENTAGON:
                BoardFactory.buildPentagonBoard(this);
                break;
            case HEXAGON:
                BoardFactory.buildHexagonBoard(this);
                break;
        }
    }

    public Location getStartLocation() {
        for (Location loc : locations) {
            if (loc.isStart()) {
                return loc;
            }
        }
        return null;
    }

    public List<Location> getNextLocations(Location start, YutResult result) {
        List<Location> resultList = new ArrayList<>();
        int targetSteps = result.getValue();
        dfsSearch(start, targetSteps, 0, resultList);
        System.out.println("[" + start.getId() + "]에서 [" + result.getType() + "] 만큼 이동 가능한 경로: " + resultList.size() + "개");
        return resultList;
    }

    private void dfsSearch(Location current, int target, int depth, List<Location> resultList) {
        if (depth == target) {
            resultList.add(current);
            return;
        }

        for (Path path : paths) {
            if (path.getFrom().equals(current)) {
                dfsSearch(path.getTo(), target, depth + path.getStepCount(), resultList);
            }
        }

        // 대각선 분기 처리: 5, 10, 200에서 출발할 때만
        if (depth == 0) {
            switch (current.getId()) {
                case "5" -> dfsSearch(getLocationById("51"), target, depth + 1, resultList);
                case "10" -> dfsSearch(getLocationById("101"), target, depth + 1, resultList);
                case "200" -> dfsSearch(getLocationById("201"), target, depth + 1, resultList);
            }
        }
    }


    public void addLocation(Location location) {
        locations.add(location);
    }

    public void addPath(Path path) {
        paths.add(path);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public BoardType getType() {
        return type;
    }

    public Location getLocationById(String id) {
        for (Location loc : locations) {
            if (loc.getId().equals(id)) return loc;
        }
        return null;
    }
}
