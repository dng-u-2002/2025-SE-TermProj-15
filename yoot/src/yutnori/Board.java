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
        dfsSearch(start, start, targetSteps, 0, resultList, "");

        System.out.println("[" + start.getId() + "]에서 [" + result.getType() + "] 만큼 이동 가능한 경로: " + resultList.size() + "개");
        System.out.println("→ 도착 후보: " + resultList.stream().map(Location::getId).toList());
        return resultList;
    }

    private void dfsSearch(Location origin, Location current, int target, int depth, List<Location> resultList, String branchSource) {
        System.out.println("[DFS] " + current.getId() + " → depth " + depth + " / target " + target);

        if (depth == target) {
            if (!resultList.contains(current)) {
                resultList.add(current);
            }
            return;
        }

        // 🔐 분기 출처가 10인 경우: 언제든 200 → 201 고정
        if (current.getId().equals("200") && (branchSource.equals("10") || depth == 0)) {
            System.out.println("→ 200에서 201만 탐색 (출처: 10 또는 정지)");
            dfsSearch(origin, getLocationById("201"), target, depth + 1, resultList, branchSource);
            return;
        }

        // 🔐 오직 DFS의 시작 위치에서만 분기 가능
        if (depth == 0 && current.equals(origin)) {
            switch (current.getId()) {
                case "5" -> {
                    System.out.println("→ 분기 시도: 5 → 51");
                    dfsSearch(origin, getLocationById("51"), target, depth + 1, resultList, "5");
                }
                case "10" -> {
                    System.out.println("→ 분기 시도: 10 → 101");
                    dfsSearch(origin, getLocationById("101"), target, depth + 1, resultList, "10");
                }
                case "200" -> {
                    System.out.println("→ 분기 시도: 200 → 201");
                    dfsSearch(origin, getLocationById("201"), target, depth + 1, resultList, "");
                }
            }
        }

        for (Path path : paths) {
            if (path.getFrom().equals(current)) {
                String toId = path.getTo().getId();

                // 대각선 시작 노드 이외에는 분기 노드 탐색 금지
                if ((toId.equals("51") && !origin.getId().equals("5")) ||
                        (toId.equals("101") && !origin.getId().equals("10")) ||
                        (toId.equals("201") && !origin.getId().equals("200"))) {
                    continue;
                }

                // 분기 출처 유지 추적
                String nextBranchSource = branchSource;
                if (current.getId().equals("51") || current.getId().equals("52")) {
                    nextBranchSource = "5";
                } else if (current.getId().equals("101") || current.getId().equals("102")) {
                    nextBranchSource = "10";
                }

                dfsSearch(origin, path.getTo(), target, depth + path.getStepCount(), resultList, nextBranchSource);
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
