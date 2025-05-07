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
        dfsSearch(start, start, targetSteps, 0, resultList);

        System.out.println("[" + start.getId() + "]에서 [" + result.getType() + "] 만큼 이동 가능한 경로: " + resultList.size() + "개");
        System.out.println("→ 도착 후보: " + resultList.stream().map(Location::getId).toList());
        return resultList;
    }

    private void dfsSearch(Location origin, Location current, int target, int depth, List<Location> resultList) {
        System.out.println("[DFS] " + current.getId() + " → depth " + depth + " / target " + target);

        if (depth == target) {
            if (!resultList.contains(current)) {
                resultList.add(current);
            }
            return;
        }

        // 🔐 오직 DFS의 시작 위치에서만 분기 가능
        if (depth == 0 && current.equals(origin)) {
            switch (current.getId()) {
                case "5" -> {
                    System.out.println("→ 분기 시도: 5 → 51");
                    dfsSearch(origin, getLocationById("51"), target, depth + 1, resultList);
                }
                case "10" -> {
                    System.out.println("→ 분기 시도: 10 → 101");
                    dfsSearch(origin, getLocationById("101"), target, depth + 1, resultList);
                }
                case "200" -> {
                    System.out.println("→ 분기 시도: 200 → 201");
                    dfsSearch(origin, getLocationById("201"), target, depth + 1, resultList);
                }
            }
        }

        for (Path path : paths) {
            if (path.getFrom().equals(current)) {
                String toId = path.getTo().getId();
                if ((toId.equals("51") && !origin.getId().equals("5")) ||
                        (toId.equals("101") && !origin.getId().equals("10")) ||
                        (toId.equals("201") && !origin.getId().equals("200"))) {
                    continue;
                }
                dfsSearch(origin, path.getTo(), target, depth + path.getStepCount(), resultList);
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
