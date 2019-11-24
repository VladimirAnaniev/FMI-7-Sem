package fmi.ai;

import static fmi.ai.CellState.EMPTY;
import static fmi.ai.CellState.O;
import static fmi.ai.CellState.X;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;

import org.junit.Test;

public class MinimaxPlayerTest {
    @Test
    public void getBestMoveShouldReturnNewBoardState() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(X, EMPTY, EMPTY),
                        Arrays.asList(EMPTY, EMPTY, EMPTY),
                        Arrays.asList(EMPTY, EMPTY, EMPTY)
                )
        );

        Board bestResponse = MinimaxPlayer.getBestMove(board);
        assertNotEquals(board, bestResponse);
    }

    @Test
    public void getBestMoveShouldFinishInASingleMove() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(X, O, O),
                        Arrays.asList(X, X, O),
                        Arrays.asList(EMPTY, EMPTY, EMPTY)
                )
        );
        Board expectedResponse = Board.fromState(
                Arrays.asList(
                        Arrays.asList(X, O, O),
                        Arrays.asList(X, X, O),
                        Arrays.asList(EMPTY, EMPTY, O)
                )
        );

        Board bestResponse = MinimaxPlayer.getBestMove(board);
        assertEquals(expectedResponse, bestResponse);
    }

    @Test
    public void getBestMoveShouldDefend() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(O, O, X),
                        Arrays.asList(X, X, EMPTY),
                        Arrays.asList(EMPTY, EMPTY, O)
                )
        );
        Board expectedResponse = Board.fromState(
                Arrays.asList(
                        Arrays.asList(O, O, X),
                        Arrays.asList(X, X, O),
                        Arrays.asList(EMPTY, EMPTY, O)
                )
        );

        Board bestResponse = MinimaxPlayer.getBestMove(board);
        assertEquals(expectedResponse, bestResponse);
    }

    @Test
    public void getBestMoveShouldPreferWinningOverDefending() {
        Board board = Board.fromState(
                Arrays.asList(
                        Arrays.asList(O, O, EMPTY),
                        Arrays.asList(X, X, EMPTY),
                        Arrays.asList(EMPTY, EMPTY, X)
                )
        );
        Board expectedResponse = Board.fromState(
                Arrays.asList(
                        Arrays.asList(O, O, O),
                        Arrays.asList(X, X, EMPTY),
                        Arrays.asList(EMPTY, EMPTY, X)
                )
        );

        Board bestResponse = MinimaxPlayer.getBestMove(board);
        assertEquals(expectedResponse, bestResponse);
    }
}
