// Isaac Natera 101554429

public class Board {
    private char[][] board;

    public Board() {
        board = new char[4][4];
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void printBoard() {
        System.out.println("\nBoard:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j]);
                if (j < 3) System.out.print(" | ");
            }
            System.out.println();
            if (i < 3) System.out.println("--------------");
        }
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < 4 &&
               col >= 0 && col < 4 &&
               board[row][col] == ' ';
    }

    public void makeMove(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    public boolean isFull() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWin(char symbol) {
        // Rows
        for (int i = 0; i < 4; i++) {
            if (board[i][0] == symbol &&
                board[i][1] == symbol &&
                board[i][2] == symbol &&
                board[i][3] == symbol) return true;
        }

        // Columns
        for (int j = 0; j < 4; j++) {
            if (board[0][j] == symbol &&
                board[1][j] == symbol &&
                board[2][j] == symbol &&
                board[3][j] == symbol) return true;
        }

        // Diagonals
        if (board[0][0] == symbol &&
            board[1][1] == symbol &&
            board[2][2] == symbol &&
            board[3][3] == symbol) return true;

        if (board[0][3] == symbol &&
            board[1][2] == symbol &&
            board[2][1] == symbol &&
            board[3][0] == symbol) return true;

        return false;
    }
}
// char[][] board = new char[4][4];

// initializeBoard()
// printBoard()
// isValidMove(int row, int col)
// makeMove(int row, int col, char symbol)
// checkWin(char symbol)
// isFull()

