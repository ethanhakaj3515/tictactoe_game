import java.util.Scanner;

public class Human extends Player {
    private static final int BOARD_SIZE = 3;
    private final Scanner scanner;

    public Human(String name, char symbol, Scanner scanner) {
        super(name, symbol);
        this.scanner = scanner;
    }

    @Override
    public int[] getMove(char[][] board) {
        while (true) {
            System.out.print("Enter row and column (for example: 1 3): ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+");

            if (parts.length != 2) {
                System.out.println("Please enter exactly two numbers.");
                continue;
            }

            try {
                int row = Integer.parseInt(parts[0]) - 1;
                int col = Integer.parseInt(parts[1]) - 1;

                if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
                    System.out.println("Move out of range. Use values from 1 to 3.");
                    continue;
                }

                if (board[row][col] != ' ') {
                    System.out.println("That cell is already occupied.");
                    continue;
                }

                return new int[] { row, col };
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
    }
}

abstract class Player {
    private final String name;
    private final char symbol;

    protected Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public abstract int[] getMove(char[][] board);
}

class HumanPlayer extends Human {
    public HumanPlayer(String name, char symbol, Scanner scanner) {
        super(name, symbol, scanner);
    }
}
