package fmi.ai;

import java.util.Scanner;

public class Main {
    private static Board board = Board.empty();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printBoard();

        while (board.getWinner() == Winner.UNKNOWN) {
            playerTurn();

            if(board.getWinner() == Winner.UNKNOWN) {
                aiTurn();
            }
        }

        System.out.println(board.getWinner() + " wins!");
    }

    private static void playerTurn() {
        System.out.println("What's your next move?");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        board = board.getNextState(row, col, CellState.X);
        printBoard();
    }

    private static void aiTurn() {
        board = MinimaxPlayer.getBestMove(board);
        printBoard();
    }

    private static void printBoard() {
        clearScreen();
        System.out.println(board);
    }

    private static void clearScreen() {
        // Dirty hack
        for(int i = 0; i < 1000; i++)
        {
            System.out.println("\b");
        }
    }
}
