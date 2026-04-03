import java.util.Scanner;

public class Game {
    private static final char SYMBOL_X = 'X';
    private static final char SYMBOL_O = 'O';
    private static final int MODE_ONE_PLAYER = 1;
    private static final int MODE_TWO_PLAYER = 2;

    private final Board board;
    private final Scanner scanner;
    private final Player[] players;

    public Game() {
        board = new Board();
        scanner = new Scanner(System.in);
        players = new Player[2];
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

    private int promptGameMode() {
        System.out.println("+----------------------------------+");
        System.out.println("|         SELECT GAME MODE         |");
        System.out.println("|  1 - One Player (vs AI)          |");
        System.out.println("|  2 - Two Players (Human vs Human)|");
        System.out.println("+----------------------------------+");
        System.out.print("Enter choice [1 or 2]: ");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("1")) {
                return MODE_ONE_PLAYER;
            }
            if (input.equals("2")) {
                return MODE_TWO_PLAYER;
            }
            System.out.print("Invalid choice. Enter 1 or 2: ");
        }
    }

    private void setupOnePlayerGame() {
        System.out.println("\n--- ONE PLAYER GAME SETUP ---");
        System.out.print("Enter your name: ");
        String humanName = scanner.nextLine().trim();
        if (humanName.isEmpty()) {
            humanName = "Player";
        }

        char humanSymbol = promptSymbolChoice(humanName);
        char aiSymbol = (humanSymbol == SYMBOL_X) ? SYMBOL_O : SYMBOL_X;

        Human human = new Human(humanName, humanSymbol, scanner);
        AI ai = new AI("Computer", aiSymbol);

        if (humanSymbol == SYMBOL_X) {
            players[0] = human;
            players[1] = ai;
        } else {
            players[0] = ai;
            players[1] = human;
        }

        System.out.println("\nX always goes first. Good luck, " + humanName + "!\n");
    }

    private void setupTwoPlayerGame() {
        System.out.println("\n--- TWO PLAYER GAME SETUP ---");
        System.out.print("Player 1 - enter your name: ");
        String name1 = scanner.nextLine().trim();
        if (name1.isEmpty()) {
            name1 = "Player 1";
        }

        char symbol1 = promptSymbolChoice(name1);
        char symbol2 = (symbol1 == SYMBOL_X) ? SYMBOL_O : SYMBOL_X;

        System.out.print("Player 2 - enter your name: ");
        String name2 = scanner.nextLine().trim();
        if (name2.isEmpty()) {
            name2 = "Player 2";
        }

        System.out.println(name2 + " will play as '" + symbol2 + "'.");

        Human firstPlayer = new Human(name1, symbol1, scanner);
        Human secondPlayer = new Human(name2, symbol2, scanner);

        if (symbol1 == SYMBOL_X) {
            players[0] = firstPlayer;
            players[1] = secondPlayer;
        } else {
            players[0] = secondPlayer;
            players[1] = firstPlayer;
        }

        System.out.println("\nX always goes first. Let's play!\n");
    }

    private char promptSymbolChoice(String playerName) {
        System.out.print(playerName + ", choose your symbol (X or O): ");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("X")) {
                return SYMBOL_X;
            }
            if (input.equals("O")) {
                return SYMBOL_O;
            }
            System.out.print("Invalid symbol. Enter X or O: ");
        }
    }

    private void runGameLoop() {
        int currentPlayerIndex = 0;

        board.display();

        while (true) {
            Player currentPlayer = players[currentPlayerIndex];

            System.out.println("\n" + currentPlayer.getName() + "'s turn (" + currentPlayer.getSymbol() + "):");

            int[] move = currentPlayer.getMove(board.getCells());
            int row = move[0];
            int col = move[1];

            board.placeMove(row, col, currentPlayer.getSymbol());
            board.display();

            if (board.hasWon(currentPlayer.getSymbol())) {
                System.out.println("\n" + currentPlayer.getName() + " wins!\n");
                break;
            }

            if (board.isDraw()) {
                System.out.println("\nIt's a draw.\n");
                break;
            }

            currentPlayerIndex = 1 - currentPlayerIndex;
        }
    }

    private void promptPlayAgain() {
        System.out.print("Would you like to play again? (Y/N): ");
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.equals("Y")) {
            board.reset();
            runGameLoop();
            promptPlayAgain();
        } else {
            System.out.println("\nThanks for playing Tic-Tac-Toe. Goodbye.\n");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
