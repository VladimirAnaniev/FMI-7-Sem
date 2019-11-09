package fmi.ai;

import static fmi.ai.Winner.*;

import java.util.List;

public class MinimaxPlayer {
    public static Board getBestMove(Board board) {
        Board bestMove = null;
        int bestMoveValue = Integer.MIN_VALUE;

        List<Board> nextMoves = board.getNextMoves(CellState.O);
        for (Board availableMove : nextMoves) {
            int value = minimax(availableMove, false);
            if (value > bestMoveValue) {
                bestMoveValue = value;
                bestMove = availableMove;
            }
        }

        return bestMove;
    }

    private static int minimax(Board board, boolean maximizing) {
        if (board.getWinner() == X) {
            return -10;
        }
        if (board.getWinner() == O) {
            return 10;
        }
        if (board.getWinner() == DRAW) {
            return 0;
        }

        if (maximizing) {
            return maximize(board);
        } else {
            return minimize(board);
        }
    }

    private static int maximize(Board board) {
        return board.getNextMoves(CellState.O)
                .stream()
                .map(availableMove -> minimax(availableMove, false))
                .max(Integer::compare)
                .orElse(Integer.MIN_VALUE);
    }

    private static int minimize(Board board) {
        return board.getNextMoves(CellState.X)
                .stream()
                .map(availableMove -> minimax(availableMove, true))
                .min(Integer::compare)
                .orElse(Integer.MAX_VALUE);
    }
}
