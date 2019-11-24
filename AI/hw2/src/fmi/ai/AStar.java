package fmi.ai;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class AStar {

    public static int findShortestPath(BoardState initialState) {
        Set<BoardState> closed = new HashSet<>();
        Queue<BoardState> open = new PriorityQueue<>();
        open.add(initialState);

        while (!open.isEmpty()) {
            BoardState current = open.poll();

            if (current.getDistance() == 0) {
                return current.getMoves();
            }

            List<BoardState> nextStates = getNextStates(current);

            nextStates.stream()
                    .filter(Objects::nonNull)
                    .filter(state -> !wasBetterStateClosed(closed, state))
                    .filter(state -> !isBetterStateOpen(open, state))
                    .forEach(state -> addState(open, state));

            closed.add(current);
        }

        return -1;
    }

    private static List<BoardState> getNextStates(BoardState current) {
        return Arrays.asList(current.moveUp(), current.moveRight(), current.moveDown(), current.moveLeft());
    }

    private static boolean wasBetterStateClosed(Set<BoardState> closed, BoardState current) {
        return closed.stream().anyMatch(
                closedState -> closedState.equals(current) && closedState.getHeuristic() <= current.getHeuristic());
    }

    private static boolean isBetterStateOpen(Queue<BoardState> open, BoardState state) {
        return open.stream().anyMatch(openState -> openState.equals(state) && openState.getMoves() <= state.getMoves());
    }

    private static void addState(Queue<BoardState> open, BoardState state) {
        // Remove old sub-optimal state if exists
        open.remove(state);
        open.add(state);
    }

}
