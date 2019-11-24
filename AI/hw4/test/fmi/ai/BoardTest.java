package fmi.ai;

import static org.junit.Assert.assertEquals;
import static fmi.ai.CellState.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class BoardTest {

    @Test
    public void emptyBoardShouldHaveNoWinner() {
        Board empty = Board.empty();
        assertEquals(Winner.UNKNOWN, empty.getWinner());
    }

    @Test
    public void emptyBoardShouldHaveOnlyEmptyCells() {
        Board empty = Board.empty();
        assertEmpty(empty);
    }

    @Test
    public void emptyBoardShouldHave9AvailableMoves() {
        Board empty = Board.empty();
        List<Board> nextMoves = empty.getNextMoves(X);
        assertEquals(9, nextMoves.size());
    }

    @Test
    public void nextMovesShouldHave1AddedCell() {
        Board empty = Board.empty();
        List<Board> nextMoves = empty.getNextMoves(X);
        nextMoves.forEach(board -> {
            assertCellsCount(X, 1, board);
            assertCellsCount(O, 0, board);
            assertCellsCount(EMPTY, 8, board);
        });
    }

    @Test
    public void boardShouldNotBeModifiedAfterGettingNextMoves() {
        Board empty = Board.empty();
        empty.getNextMoves(X);
        assertEmpty(empty);
    }

    @Test
    public void shouldFindWinnerCol() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(O, X, X),
                        Arrays.asList(O, EMPTY, EMPTY),
                        Arrays.asList(O, EMPTY, X)
                )
        );

        assertEquals(Winner.O, board.getWinner());
    }

    @Test
    public void shouldFindWinnerRow() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(X, X, X),
                        Arrays.asList(O, O, EMPTY),
                        Arrays.asList(EMPTY, EMPTY, O)
                )
        );

        assertEquals(Winner.X, board.getWinner());
    }

    @Test
    public void shouldFindWinnerDiagonal() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(O, X, X),
                        Arrays.asList(O, O, EMPTY),
                        Arrays.asList(X, EMPTY, O)
                )
        );

        assertEquals(Winner.O, board.getWinner());
    }

    @Test
    public void shouldFindWinnerReverseDiagonal() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(O, O, X),
                        Arrays.asList(O, X, EMPTY),
                        Arrays.asList(X, EMPTY, X)
                )
        );

        assertEquals(Winner.X, board.getWinner());
    }

    @Test
    public void shouldFindDraw() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(O, X, X),
                        Arrays.asList(X, O, O),
                        Arrays.asList(X, O, X)
                )
        );

        assertEquals(Winner.DRAW, board.getWinner());
    }

    @Test
    public void noMovesShouldBeAvailableAfterGameIsWon() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(X, X, X),
                        Arrays.asList(O, O, EMPTY),
                        Arrays.asList(EMPTY, EMPTY, O)
                )
        );

        List<Board> nextMovesX = board.getNextMoves(X);
        List<Board> nextMovesO = board.getNextMoves(O);

        assertEquals(0, nextMovesX.size());
        assertEquals(0, nextMovesO.size());
    }

    private void assertEmpty(Board board) {
        board.getBoard().forEach(row -> {
            row.forEach(cellState -> {
                assertEquals(CellState.EMPTY, cellState);
            });
        });
    }

    private void assertCellsCount(CellState cellState, int expectedCount, Board board) {
        long count = board.getBoard()
                .stream()
                .flatMap(Collection::stream)
                .filter(cell -> cell == cellState)
                .count();
        assertEquals(expectedCount, count);
    }

    @Test
    public void toStringShouldVisualizeCorrectly() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(O, X, X),
                        Arrays.asList(X, O, O),
                        Arrays.asList(X, O, X)
                )
        );

        String expected = "-------------\n" +
                          "| O | X | X |\n" +
                          "-------------\n" +
                          "| X | O | O |\n" +
                          "-------------\n" +
                          "| X | O | X |\n" +
                          "-------------\n";

        assertEquals(expected, board.toString());
    }

}
