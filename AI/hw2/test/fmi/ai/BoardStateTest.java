package fmi.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;


import org.junit.Test;

public class BoardStateTest {
    @Test
    public void getDistanceShouldReturn0IfTarget() {
        List<Integer> row1 = Arrays.asList(1, 2, 3);
        List<Integer> row2 = Arrays.asList(4, 5, 6);
        List<Integer> row3 = Arrays.asList(7, 8, 0);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertEquals(0, boardState.getDistance());
    }

    @Test
    public void getDistanceShouldBeCorrect() {
        List<Integer> row1 = Arrays.asList(6, 5, 3);
        List<Integer> row2 = Arrays.asList(2, 4, 8);
        List<Integer> row3 = Arrays.asList(7, 0 ,1);

        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertEquals(14, boardState.getDistance());
    }

    @Test
    public void moveUpShouldSwapElements() {
        List<Integer> row1 = Arrays.asList(6, 5, 3);
        List<Integer> row2 = Arrays.asList(2, 4, 8);
        List<Integer> row3 = Arrays.asList(7, 0 ,1);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        List<Integer> row1After = Arrays.asList(6, 5, 3);
        List<Integer> row2After = Arrays.asList(2, 0, 8);
        List<Integer> row3After = Arrays.asList(7, 4 ,1);
        BoardState boardStateAfter = BoardState.initial(Arrays.asList(row1After, row2After, row3After));

        assertEquals(boardStateAfter, boardState.moveUp());
    }

    @Test
    public void moveUpShouldReturnNullOnFirstRow() {
        List<Integer> row1 = Arrays.asList(6, 0, 3);
        List<Integer> row2 = Arrays.asList(2, 4, 8);
        List<Integer> row3 = Arrays.asList(7, 5 ,1);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertNull(boardState.moveUp());
    }

    @Test
    public void moveRightShouldSwapElements() {
        List<Integer> row1 = Arrays.asList(6, 5, 3);
        List<Integer> row2 = Arrays.asList(2, 4, 8);
        List<Integer> row3 = Arrays.asList(7, 0, 1);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        List<Integer> row1After = Arrays.asList(6, 5, 3);
        List<Integer> row2After = Arrays.asList(2, 4, 8);
        List<Integer> row3After = Arrays.asList(7, 1, 0);
        BoardState boardStateAfter = BoardState.initial(Arrays.asList(row1After, row2After, row3After));

        assertEquals(boardStateAfter, boardState.moveRight());
    }

    @Test
    public void moveRightShouldReturnNullOnLastCol() {
        List<Integer> row1 = Arrays.asList(6, 3, 0);
        List<Integer> row2 = Arrays.asList(2, 4, 8);
        List<Integer> row3 = Arrays.asList(7, 5 ,1);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertNull(boardState.moveRight());
    }

    @Test
    public void moveDownShouldSwapElements() {
        List<Integer> row1 = Arrays.asList(6, 5, 3);
        List<Integer> row2 = Arrays.asList(2, 0, 8);
        List<Integer> row3 = Arrays.asList(7, 4, 1);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        List<Integer> row1After = Arrays.asList(6, 5, 3);
        List<Integer> row2After = Arrays.asList(2, 4, 8);
        List<Integer> row3After = Arrays.asList(7, 0, 1);
        BoardState boardStateAfter = BoardState.initial(Arrays.asList(row1After, row2After, row3After));

        assertEquals(boardStateAfter, boardState.moveDown());
    }

    @Test
    public void moveDownShouldReturnNullOnLastRow() {
        List<Integer> row1 = Arrays.asList(6, 5, 3);
        List<Integer> row2 = Arrays.asList(2, 4, 8);
        List<Integer> row3 = Arrays.asList(7, 0 ,1);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertNull(boardState.moveDown());
    }

    @Test
    public void moveLeftShouldSwapElements() {
        List<Integer> row1 = Arrays.asList(6, 5, 3);
        List<Integer> row2 = Arrays.asList(2, 4, 8);
        List<Integer> row3 = Arrays.asList(7, 0, 1);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        List<Integer> row1After = Arrays.asList(6, 5, 3);
        List<Integer> row2After = Arrays.asList(2, 4, 8);
        List<Integer> row3After = Arrays.asList(0, 7, 1);
        BoardState boardStateAfter = BoardState.initial(Arrays.asList(row1After, row2After, row3After));

        assertEquals(boardStateAfter, boardState.moveLeft());
    }

    @Test
    public void moveLeftShouldReturnNullOnFirstCol() {
        List<Integer> row1 = Arrays.asList(0, 6, 3);
        List<Integer> row2 = Arrays.asList(2, 4, 8);
        List<Integer> row3 = Arrays.asList(7, 5 ,1);
        BoardState boardState = BoardState.initial(Arrays.asList(row1, row2, row3));

        assertNull(boardState.moveLeft());
    }
}
