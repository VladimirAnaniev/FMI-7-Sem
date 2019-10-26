package fmi.ai;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intellij.util.containers.HashSetQueue;

public class BeamSearch {

    public static int findShortestPath(BoardState initial, int n) {
        Set<BoardState> visited = new HashSet<>();
        Queue<BoardState> open = new HashSetQueue<>();
        open.add(initial);

        while (!open.isEmpty()) {
            BoardState current = open.poll();

            if (current.getDistance() == 0) {
                return current.getMoves();
            }

            List<BoardState> nextStates = getNextStates(current, n);

            nextStates.stream()
                    .filter(state -> !visited.contains(state))
                    .filter(state -> !open.contains(state))
                    .forEach(open::add);

            visited.add(current);
        }

        return -1;
    }

    private static List<BoardState> getNextStates(BoardState current, int n) {
        return Stream.of(current.moveUp(), current.moveRight(), current.moveDown(), current.moveLeft())
                .filter(Objects::nonNull)
                .sorted()
                .limit(n)
                .collect(Collectors.toList());
    }
}
