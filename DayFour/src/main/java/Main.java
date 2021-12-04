import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final List<Integer> guesses = new ArrayList<>();
    private static final List<int[][]> boards = new ArrayList<>();
    private static final List<Integer> boardsWon = new ArrayList<>();

    private static int lastWonBoard = -1;
    private static int lastGuessWonIndex = -1;

    public static void main(String[] args) {
        try (final BufferedReader reader = new BufferedReader(
                new FileReader(Main.class.getResource("input.txt").getFile()))) {
            guesses.addAll(Arrays.stream(reader.readLine().split(",")).
                    map(Integer::parseInt).collect(Collectors.toList()));

            while (true) {

                final int[][] board = new int[5][5];

                String line = reader.readLine();
                boolean add = true;
                for (int i = 0; i < 5; i++) {

                    if (line.isBlank()) {
                        add = false;
                        break;
                    }

                    final String[] strings = line.split(" ");

                    int k = 0;
                    for (String s : strings) {
                        try {
                            board[i][k] = Integer.parseInt(s);
                            k++;
                        } catch (final Exception ignored) {
                        }
                    }

                    line = reader.readLine();
                }

                if (!add) {
                    continue;
                }

                boards.add(board);
            }

        } catch (final Exception exception) {
            for (int i = 0; i < guesses.size(); i++) {
                int index = 0;
                for (final var board : boards) {

                    if (boardsWon.contains(index)) {
                        index++;
                        continue;
                    }

                    final List<Integer> currentGuesses = new ArrayList<>();
                    for (int j = i; j >= 0; j--) {
                        currentGuesses.add(guesses.get(j));
                    }

                    Collections.reverse(currentGuesses);

                    final int check = check(board, currentGuesses);

                    if (check != -1) {
                        boardsWon.add(index);
                        lastWonBoard = index;
                        lastGuessWonIndex = i;
                    }

                    index++;
                }
            }
        }

        final var board = boards.get(lastWonBoard);
        final List<Integer> newGuesses = new ArrayList<>();

        for (int i = 0; i <= lastGuessWonIndex; i++) {
            newGuesses.add(guesses.get(i));
        }

        System.out.println("Last won board score = " + check(
                board, newGuesses
        ) * guesses.get(lastGuessWonIndex));
    }

    private static int check(final int[][] board, final List<Integer> guesses) {
        final char[][] checks = new char[5][5];
        boolean isBingo = false;
        outer:
        for (final int guess : guesses) {
            for (int i = 0; i < board.length; i++) {
                for (int k = 0; k < board[i].length; k++) {
                    if (board[i][k] == guess) {
                        checks[i][k] = 'x';
                        isBingo = isBingo(checks);
                        if (isBingo) {
                            break outer;
                        }
                    }
                }
            }
        }


        if (isBingo) {
            return getUnmarkedSum(board, checks);
        }

        return -1;
    }

    private static boolean isBingo(final char[][] board) {

        for (int i = 0; i < board.length; i++) {
            boolean previousRowChecked = true;
            boolean previousColumnChecked = true;
            for (int j = 0; j < board[i].length; j++) {
                if (!previousRowChecked || board[i][j] != 'x') {
                    previousRowChecked = false;
                }

                if (!previousColumnChecked || board[j][i] != 'x') {
                    previousColumnChecked = false;
                }

                if (previousRowChecked && j == board.length - 1) {
                    return true;
                }

                if (previousColumnChecked && j == board.length - 1) {
                    return true;
                }
            }
        }

        return false;
    }

    private static int getUnmarkedSum(final int[][] board, final char[][] checked) {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (checked[i][j] == 'x') continue;
                sum += board[i][j];
            }
        }

        return sum;
    }
}








