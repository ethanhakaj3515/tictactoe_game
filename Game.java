// Martins Madubuchi 101496696
import java.util.Scanner;

public class Game {

    private static final int BOARD_SIZE = 3;
    private static final char EMPTY = ' ';
    private static final char SYMBOL_X = 'X';
    private static final char SYMBOL_O = 'O';
    private static final int MODE_ONE_PLAYER = 1;
    private static final int MODE_TWO_PLAYER = 2;

    private final char[][] board;
    private final Scanner scanner;
    private Player[] players;

    public Game() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        scanner = new Scanner(System.in);
        players = new Player[2];
        initBoard();
    }

    public void start() {
        int mode = promptGameMode();

        if (mode == MODE_ONE_PLAYER) {
            setupOnePlayerGame();
        } else {
            setupTwoPlayerGame();
        }

        runGameLoop();
        promptPlayAgain();
    }

    private void initBoard() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                board[r][c] = EMPTY;
            }
        }
    }

    private int promptGameMode() {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│         SELECT GAME MODE         │");
        System.out.println("│  1 - One Player (vs AI)          │");
        System.out.println("│  2 - Two Players (Human vs Human)│");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Enter choice [1 or 2]: ");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("1")) return MODE_ONE_PLAYER;
            if (input.equals("2")) return MODE_TWO_PLAYER;
            System.out.print("Invalid choice. Enter 1 or 2: ");
        }
    }

    private void setupOnePlayerGame() {
        System.out.println("\n--- ONE PLAYER GAME SETUP ---");
        System.out.print("Enter your name: ");
        String humanName = scanner.nextLine().trim();
        if (humanName.isEmpty()) humanName = "Player";

        char humanSymbol = promptSymbolChoice(humanName);
        char aiSymbol = (humanSymbol == SYMBOL_X) ? SYMBOL_O : SYMBOL_X;

        HumanPlayer human = new HumanPlayer(humanName, humanSymbol, scanner);
        AIPlayer ai = new AIPlayer("Computer", aiSymbol, board);

        if (humanSymbol == SYMBOL_X) {
            players[0] = human;
            players[1] = ai;
        } else {
            players[0] = ai;
            players[1] = human;
        }

        System.out.println("\nX always goes first.  Good luck, " + humanName + "!\n");
    }

    private void setupTwoPlayerGame() {
        System.out.println("\n--- TWO PLAYER GAME SETUP ---");
        System.out.print("Player 1 – enter your name: ");
        String name1 = scanner.nextLine().trim();
        if (name1.isEmpty()) name1 = "Player 1";
        char symbol1 = promptSymbolChoice(name1);

        char symbol2 = (symbol1 == SYMBOL_X) ? SYMBOL_O : SYMBOL_X;
        System.out.print("Player 2 – enter your name: ");
        String name2 = scanner.nextLine().trim();
        if (name2.isEmpty()) name2 = "Player 2";
        System.out.println(name2 + " will play as '" + symbol2 + "'.");

        HumanPlayer p1 = new HumanPlayer(name1, symbol1, scanner);
        HumanPlayer p2 = new HumanPlayer(name2, symbol2, scanner);

        if (symbol1 == SYMBOL_X) {
            players[0] = p1;
            players[1] = p2;
        } else {
            players[0] = p2;
            players[1] = p1;
        }

        System.out.println("\nX always goes first.  Let's play!\n");
    }

    private char promptSymbolChoice(String playerName) {
        System.out.print(playerName + ", choose your symbol (X or O): ");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("X")) return SYMBOL_X;
            if (input.equals("O")) return SYMBOL_O;
            System.out.print("Invalid symbol. Enter X or O: ");
        }
    }

    private void runGameLoop() {
        int currentPlayerIndex = 0;

        displayBoard();

        while (true) {
            Player currentPlayer = players[currentPlayerIndex];

            System.out.println("\n" + currentPlayer.getName()
                    + "'s turn (" + currentPlayer.getSymbol() + "):");

            int[] move = currentPlayer.getMove(board);
            int row = move[0];
            int col = move[1];

            board[row][col] = currentPlayer.getSymbol();
            displayBoard();

            if (checkWin(currentPlayer.getSymbol())) {
                System.out.println(currentPlayer.getName() + " wins!\n");
                break;
            }

            if (checkDraw()) {
                System.out.println("\nIt's a draw (tie)!\n");
                break;
            }

            currentPlayerIndex = 1 - currentPlayerIndex;
        }
    }

    boolean checkWin(char symbol) {
        for (int r = 0; r < BOARD_SIZE; r++) {
            if (board[r][0] == symbol && board[r][1] == symbol && board[r][2] == symbol)
                return true;
        }

        for (int c = 0; c < BOARD_SIZE; c++) {
            if (board[0][c] == symbol && board[1][c] == symbol && board[2][c] == symbol)
                return true;
        }

        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)
            return true;

        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)
            return true;

        return false;
    }

    boolean checkDraw() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (board[r][c] == EMPTY) return false;
            }
        }
        return true;
    }

    void displayBoard() {
        System.out.println();
        System.out.println("     Col: 1   2   3");
        for (int r = 0; r < BOARD_SIZE; r++) {
            System.out.print("  Row " + (r + 1) + ":  ");
            for (int c = 0; c < BOARD_SIZE; c++) {
                System.out.print(" " + board[r][c] + " ");
                if (c < BOARD_SIZE - 1) System.out.print("|");
            }
            System.out.println();
            if (r < BOARD_SIZE - 1) {
                System.out.println("           -----------");
            }
        }
        System.out.println();
    }

    private void promptPlayAgain() {
        System.out.print("Would you like to play again? (Y/N): ");
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.equals("Y")) {
            initBoard();
            runGameLoop();
            promptPlayAgain();
        } else {
            System.out.println("\nThanks for playing TicTacToe!  Goodbye.\n");
        }
    }

    public static void main(String[] args) {
        Game controller = new Game();
        controller.start();
    }
}