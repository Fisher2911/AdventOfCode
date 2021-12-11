import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    private static int[][] octopi;

    public static void main(String[] args) {
        try (final BufferedReader reader = new BufferedReader(
                new FileReader(Main.class.getResource("input.txt").getFile())
        )) {
            final List<String> lines = reader.lines().toList();

            octopi = new int[lines.size()][];

            int index = 0;
            for (final String line : lines) {
                octopi[index] = new int[line.length()];
                int i = 0;
                for (final char c : line.toCharArray()) {
                    octopi[index][i] = Integer.parseInt(String.valueOf(c));
                    i++;
                }
                index++;
            }

            final Set<Pair> flashed = new HashSet<>();

            int totalFlashes = 0;

            int loop = 0;
            while (sumOctopi() != 0) {
                for (int i = 0; i < octopi.length; i++) {
                    for (int j = 0; j < octopi[i].length; j++) {

                        final int currentLevel = octopi[i][j] + 1;
                        octopi[i][j] = currentLevel;
                    }
                }

                for (int i = 0; i < octopi.length; i++) {
                    for (int j = 0; j < octopi[i].length; j++) {

                        final int currentLevel = octopi[i][j];

                        if (currentLevel > 9) {
                            flash(i, j, flashed);
                            octopi[i][j] = 0;
                            flashed.add(Pair.of(i, j));
                        }
                    }
                }

                totalFlashes += flashed.size();
                flashed.clear();

                loop++;
            }

            System.out.println("Flashes = " + totalFlashes);
            System.out.println("Loop: " + loop);

        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    private static int sumOctopi() {
        int total = 0;
        for (final int[] octopus : octopi) {
            for (final int i : octopus) {
                total += i;
            }
        }

        return total;
    }

    private static Set<Pair> flash(
            final int row,
            final int column,
            final Set<Pair> flashed) {

        if (row < 0 || row >= octopi.length) return flashed;
        if (column < 0 || column >= octopi[row].length) return flashed;

        if (flashed.contains(Pair.of(row, column))) return flashed;

        int level = Math.min(octopi[row][column] + 1, 10);

        octopi[row][column] = level;

        if (level <= 9) {
            return flashed;
        }

        flashed.add(Pair.of(row, column));

        octopi[row][column] = 0;

        flash(row, column + 1, flashed);
        flash(row, column - 1, flashed);
        flash(row + 1, column + 1, flashed);
        flash(row + 1, column - 1, flashed);
        flash(row + 1, column, flashed);
        flash(row - 1, column, flashed);
        flash(row - 1, column - 1, flashed);
        flash(row - 1, column + 1, flashed);

        return flashed;
    }

    private static record Pair(int x, int y) {

        public static Pair of(final int x, final int y) {
            return new Pair(x, y);
        }
    }
}
