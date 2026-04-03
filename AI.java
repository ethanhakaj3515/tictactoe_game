public class AI extends Player {
    private static final int BOARD_SIZE = 3;
    private static final char EMPTY = ' ';
    private static final int WIN_SCORE = 10;
    private static final int DRAW_SCORE = 0;

    private final char aiSymbol;
    private final char humanSymbol;

    public AI(String name, char symbol) {
        super(name, symbol);
        this.aiSymbol = symbol;
        this.humanSymbol = symbol == 'X' ? 'O' : 'X';
    }

    @Override
    public int[] getMove(char[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[] { -1, -1 };

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] != EMPTY) {
                    continue;
                }

                board[row][col] = aiSymbol;
                int score = minimax(board, 0, false);
                board[row][col] = EMPTY;

                if (score > bestScore || (score == bestScore && isPreferredMove(row, col, bestMove))) {
                    bestScore = score;
                    bestMove[0] = row;
                    bestMove[1] = col;
                }
            }
        }

        if (bestMove[0] == -1) {
            return findFirstOpenCell(board);
        }

        return bestMove;
    }

    private int minimax(char[][] board, int depth, boolean isMaximizing) {
        int score = evaluate(board, depth);
        if (score != Integer.MIN_VALUE) {
            return score;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;

            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (board[row][col] != EMPTY) {
                        continue;
                    }

                    board[row][col] = aiSymbol;
                    bestScore = Math.max(bestScore, minimax(board, depth + 1, false));
                    board[row][col] = EMPTY;
                }
            }

            return bestScore;
        }

        int bestScore = Integer.MAX_VALUE;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] != EMPTY) {
                    continue;
                }

                board[row][col] = humanSymbol;
                bestScore = Math.min(bestScore, minimax(board, depth + 1, true));
                board[row][col] = EMPTY;
            }
        }

        return bestScore;
    }

    private int evaluate(char[][] board, int depth) {
        if (hasWon(board, aiSymbol)) {
            return WIN_SCORE - depth;
        }
        if (hasWon(board, humanSymbol)) {
            return depth - WIN_SCORE;
        }
        if (isFull(board)) {
            return DRAW_SCORE;
        }
        return Integer.MIN_VALUE;
    }

    private boolean hasWon(char[][] board, char symbol) {
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

    private boolean isFull(char[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPreferredMove(int row, int col, int[] currentBestMove) {
        return movePriority(row, col) > movePriority(currentBestMove[0], currentBestMove[1]);
    }

    private int movePriority(int row, int col) {
        if (row == -1 || col == -1) {
            return Integer.MIN_VALUE;
        }

        int centerIndex = 1;
        if (row == centerIndex && col == centerIndex) {
            return 3;
        }
        if ((row == 0 || row == 2) && (col == 0 || col == 2)) {
            return 2;
        }
        return 1;
    }

    private int[] findFirstOpenCell(char[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    return new int[] { row, col };
                }
            }
        }
        return new int[] { -1, -1 };
    }
}

class AIPlayer extends AI {
    public AIPlayer(String name, char symbol, char[][] board) {
        super(name, symbol);
    }
}
