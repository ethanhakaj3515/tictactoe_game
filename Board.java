public class Board {
    private static final int BOARD_SIZE = 3;
    private static final char EMPTY = ' ';

    private final char[][] board;

    public Board() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        reset();
    }

    public char[][] getCells() {
        return board;
    }

    public void reset() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = EMPTY;
            }
        }
    }

    public void display() {
        System.out.println();
        System.out.println("       1   2   3");
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print("Row " + (row + 1) + ": ");
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(" " + board[row][col] + " ");
                if (col < BOARD_SIZE - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (row < BOARD_SIZE - 1) {
                System.out.println("       ---+---+---");
            }
        }
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < BOARD_SIZE
                && col >= 0 && col < BOARD_SIZE
                && board[row][col] == EMPTY;
    }

    public void placeMove(int row, int col, char symbol) {
        if (isValidMove(row, col)) {
            board[row][col] = symbol;
        }
    }

    public boolean hasWon(char symbol) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][0] == symbol && board[row][1] == symbol && board[row][2] == symbol) {
                return true;
            }
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[0][col] == symbol && board[1][col] == symbol && board[2][col] == symbol) {
                return true;
            }
        }

        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)
                || (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    public boolean isDraw() {
        if (hasWon('X') || hasWon('O')) {
            return false;
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }
}
