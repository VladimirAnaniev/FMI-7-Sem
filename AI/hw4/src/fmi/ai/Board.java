package fmi.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class Board {

    private final List<List<CellState>> board;
    private final Winner winner;

    private Board(List<List<CellState>> board) {
        this.board = board;
        this.winner = calculateWinner();
    }

    public static Board empty() {
        List<List<CellState>> board = Arrays.asList(
                Arrays.asList(CellState.EMPTY, CellState.EMPTY, CellState.EMPTY),
                Arrays.asList(CellState.EMPTY, CellState.EMPTY, CellState.EMPTY),
                Arrays.asList(CellState.EMPTY, CellState.EMPTY, CellState.EMPTY)
        );
        return new Board(board);
    }

    static Board fromState(List<List<CellState>> board) {
        return new Board(
                board.stream()
                        .map(ArrayList::new)
                        .collect(Collectors.toList()));
    }

    public Winner getWinner() {
        return winner;
    }

    List<List<CellState>> getBoard() {
        return board.stream()
                .map(ArrayList::new)
                .collect(Collectors.toList());
    }

    public List<Board> getNextMoves(CellState currentPlayer) {
        if (winner != Winner.UNKNOWN) {
            return Collections.emptyList();
        }

        return getAvailableMoves()
                .stream()
                .map(move -> getNextState(move.getKey(), move.getValue(), currentPlayer))
                .collect(Collectors.toList());
    }

    private List<Pair<Integer, Integer>> getAvailableMoves() {
        List<Pair<Integer, Integer>> availableMoves = new ArrayList<>();

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board.get(r).get(c) == CellState.EMPTY) {
                    availableMoves.add(new Pair<>(r, c));
                }
            }
        }

        return availableMoves;
    }

    public Board getNextState(int row, int col, CellState newState) {
        List<List<CellState>> newBoard = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i == row) {
                List<CellState> modifiedRow = new ArrayList<>(board.get(row));
                modifiedRow.set(col, newState);
                newBoard.add(modifiedRow);
            } else {
                newBoard.add(board.get(i));
            }
        }

        return new Board(newBoard);
    }

    private Winner calculateWinner() {
        for (int i = 0; i < 3; i++) {
            if (isWinningRow(i)) {
                return Winner.fromCellState(board.get(i).get(0));
            }
            if (isWinningCol(i)) {
                return Winner.fromCellState(board.get(0).get(i));
            }
        }

        if(isWinningDiagonal()) {
            return Winner.fromCellState(board.get(0).get(0));
        }
        if(isWinningReverseDiagonal()) {
            return Winner.fromCellState(board.get(2).get(0));
        }

        if (isFull()) {
            return Winner.DRAW;
        }

        return Winner.UNKNOWN;
    }

    private boolean isWinningRow(int r) {
        List<CellState> cells = board.get(r);
        return areEqual(cells);
    }

    private boolean isWinningCol(int c) {
        List<CellState> cells = board.stream().map(row -> row.get(c)).collect(Collectors.toList());
        return areEqual(cells);
    }


    private boolean isWinningDiagonal() {
        List<CellState> cells = Arrays.asList(board.get(0).get(0), board.get(1).get(1), board.get(2).get(2));
        return areEqual(cells);
    }

    private boolean isWinningReverseDiagonal() {
        List<CellState> cells = Arrays.asList(board.get(2).get(0), board.get(1).get(1), board.get(0).get(2));
        return areEqual(cells);
    }

    private boolean isFull() {
        return board.stream().flatMap(Collection::stream).noneMatch(cellState -> cellState == CellState.EMPTY);
    }

    private boolean areEqual(List<CellState> cells) {
        return new HashSet<>(cells).size() == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board1 = (Board) o;
        return board.equals(board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    @Override
    public String toString() {
        return  "-------------\n" +
                toStringRow(board.get(0), 0) +
                "-------------\n" +
                toStringRow(board.get(1), 1) +
                "-------------\n" +
                toStringRow(board.get(2), 2) +
                "-------------\n";
    }

    private String toStringRow(List<CellState> row, int r) {
        return "| " + row.get(0) + " | " + row.get(1) + " | " + row.get(2) + " |\n";
    }
}
