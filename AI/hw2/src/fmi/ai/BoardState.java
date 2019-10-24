package fmi.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class BoardState {

    private final Map<Integer, Pair<Integer, Integer>> board;
    private final int distance;

    private BoardState(Map<Integer, Pair<Integer, Integer>> board) {
        this.board = board;
        this.distance = calculateDistance();
    }

    public int getDistance() {
        return distance;
    }

    public static BoardState initial(List<List<Integer>> board) {
        assert board.size() == 3 : "Board should have 3 rows";
        assert board.stream().allMatch(row -> row.size() == 3) : "Each bord row should have 3 columns";
        // TODO: assert contains 0..8
        return new BoardState(toMap(board));
    }

    public BoardState moveUp() {
        Pair<Integer, Integer> zeroPosition = board.get(0);
        if (zeroPosition.getKey() == 0) {
            return null;
        }

        Map<Integer, Pair<Integer, Integer>> newBoard =
                board.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                        e -> {
                            if (Objects.equals(e.getValue().getKey(), zeroPosition.getKey() - 1) &&
                                    Objects.equals(e.getValue().getValue(), zeroPosition.getValue())) {
                                return zeroPosition;
                            }
                            if (Objects.equals(e.getValue().getKey(), zeroPosition.getKey()) &&
                                    Objects.equals(e.getValue().getValue(), zeroPosition.getValue())) {
                                return new Pair<>(zeroPosition.getKey() - 1, zeroPosition.getValue());
                            }
                            return e.getValue();
                        }
                ));

        return new BoardState(newBoard);
    }

    public BoardState moveRight() {
        Pair<Integer, Integer> zeroPosition = board.get(0);
        if (zeroPosition.getValue() == 2) {
            return null;
        }

        Map<Integer, Pair<Integer, Integer>> newBoard =
                board.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                        e -> {
                            if (Objects.equals(e.getValue().getKey(), zeroPosition.getKey()) &&
                                    Objects.equals(e.getValue().getValue(), zeroPosition.getValue() + 1)) {
                                return zeroPosition;
                            }
                            if (Objects.equals(e.getValue().getKey(), zeroPosition.getKey()) &&
                                    Objects.equals(e.getValue().getValue(), zeroPosition.getValue())) {
                                return new Pair<>(zeroPosition.getKey(), zeroPosition.getValue() + 1);
                            }
                            return e.getValue();
                        }
                ));

        return new BoardState(newBoard);
    }

    public BoardState moveDown() {
        Pair<Integer, Integer> zeroPosition = board.get(0);
        if (zeroPosition.getKey() == 2) {
            return null;
        }

        Map<Integer, Pair<Integer, Integer>> newBoard =
                board.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                        e -> {
                            if (Objects.equals(e.getValue().getKey(), zeroPosition.getKey() + 1) &&
                                    Objects.equals(e.getValue().getValue(), zeroPosition.getValue())) {
                                return zeroPosition;
                            }
                            if (Objects.equals(e.getValue().getKey(), zeroPosition.getKey()) &&
                                    Objects.equals(e.getValue().getValue(), zeroPosition.getValue())) {
                                return new Pair<>(zeroPosition.getKey() + 1, zeroPosition.getValue());
                            }
                            return e.getValue();
                        }
                ));

        return new BoardState(newBoard);
    }

    public BoardState moveLeft() {
        Pair<Integer, Integer> zeroPosition = board.get(0);
        if (zeroPosition.getValue() == 0) {
            return null;
        }

        Map<Integer, Pair<Integer, Integer>> newBoard =
                board.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                        e -> {
                            if (Objects.equals(e.getValue().getKey(), zeroPosition.getKey()) &&
                                    Objects.equals(e.getValue().getValue(), zeroPosition.getValue() - 1)) {
                                return zeroPosition;
                            }
                            if (Objects.equals(e.getValue().getKey(), zeroPosition.getKey()) &&
                                    Objects.equals(e.getValue().getValue(), zeroPosition.getValue())) {
                                return new Pair<>(zeroPosition.getKey(), zeroPosition.getValue() - 1);
                            }
                            return e.getValue();
                        }
                ));

        return new BoardState(newBoard);
    }

    private static Map<Integer, Pair<Integer, Integer>> toMap(List<List<Integer>> board) {
        Map<Integer, Pair<Integer, Integer>> map = new HashMap<>();

        int r = 0;
        for (List<Integer> row : board) {
            int c = 0;
            for (Integer col : row) {
                map.put(col, new Pair<>(r, c++));
            }
            r++;
        }

        return map;
    }

    private int calculateDistance() {
        return board.entrySet().stream()
                .map(entry -> {
                    int value = entry.getKey();
                    int row = entry.getValue().getKey();
                    int col = entry.getValue().getValue();
                    if (value == 0) {
                        return Math.abs(2 - row) + Math.abs(2 - col);
                    } else {
                        int targetRow = (value - 1) / 3;
                        int targetCol = (value - 1) % 3;

                        return Math.abs(row - targetRow) + Math.abs(col - targetCol);
                    }
                })
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoardState that = (BoardState) o;
        return distance == that.distance &&
                Objects.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, distance);
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
