package fmi.ai;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class AStarTest {

    @Test
    public void shouldReturn0ForSolvedPuzzle() {
        List<Integer> row1 = Arrays.asList(1, 2, 3);
        List<Integer> row2 = Arrays.asList(4, 5, 6);
        List<Integer> row3 = Arrays.asList(7, 8, 0);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertEquals(0, AStar.findShortestPath(boardState));
    }

    @Test
    public void shouldFindShortestPathSimple() {
        List<Integer> row1 = Arrays.asList(1, 2, 3);
        List<Integer> row2 = Arrays.asList(4, 0, 6);
        List<Integer> row3 = Arrays.asList(7, 5, 8);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertEquals(2, AStar.findShortestPath(boardState));
    }

    @Test
    public void shouldFindShortestPathMedium() {
        List<Integer> row1 = Arrays.asList(0, 1, 2);
        List<Integer> row2 = Arrays.asList(5, 6, 3);
        List<Integer> row3 = Arrays.asList(4, 7, 8);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertEquals(8, AStar.findShortestPath(boardState));
    }

    @Test
    public void shouldReturnShortestPathHard() {
        List<Integer> row1 = Arrays.asList(1, 0, 3);
        List<Integer> row2 = Arrays.asList(7, 2, 5);
        List<Integer> row3 = Arrays.asList(8, 4, 6);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertEquals(7, AStar.findShortestPath(boardState));
    }

    @Test
    public void shouldReturnShortestPathExample() {
        List<Integer> row1 = Arrays.asList(6, 5, 3);
        List<Integer> row2 = Arrays.asList(2, 4, 8);
        List<Integer> row3 = Arrays.asList(7, 0, 1);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertEquals(21, AStar.findShortestPath(boardState));
    }

}
