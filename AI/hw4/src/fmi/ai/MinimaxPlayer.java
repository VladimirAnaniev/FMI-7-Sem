package fmi.ai;

import static fmi.ai.Winner.*;

import java.util.List;

public class MinimaxPlayer {

    public static Board getBestMove(Board board) {
        Board bestMove = null;
        int bestMoveValue = Integer.MIN_VALUE;

        List<Board> nextMoves = board.getNextMoves(CellState.O);
        for (Board availableMove : nextMoves) {
            int value = minimax(availableMove, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (value > bestMoveValue) {
                bestMoveValue = value;
                bestMove = availableMove;
            }
        }

        return bestMove;
    }

    private static int minimax(Board board, boolean maximizing, int alpha, int beta) {
        if (board.getWinner() == X) {
            return -10;
        }
        if (board.getWinner() == O) {
            return 10;
        }
        if (board.getWinner() == DRAW) {
            return 0;
        }

        int best = 0;

        for (Board nexMove : getNextMoves(board, maximizing)) {
            int value = minimax(nexMove, !maximizing, alpha, beta);

            if (maximizing) {
                best = Integer.max(best, value);
                alpha = Integer.max(alpha, best);
            } else {
                best = Integer.min(best, value);
                beta = Integer.min(beta, best);
            }

            if (alpha > beta) {
                break;
            }
        }

        return best;
    }

    private static List<Board> getNextMoves(Board board, boolean isMaximizing) {
        return board.getNextMoves(isMaximizing ? CellState.O : CellState.X);
    }
}
