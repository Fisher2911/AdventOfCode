import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {

    private static int[][] heightMap = null;
    private static char[][] checked = null;
    private static int lowest = 0;
    private static List<Integer> basins = new ArrayList<>();

    public static void main(String args[]) {
        try (final BufferedReader reader = new BufferedReader(
                new FileReader(Main.class.getResource("input.txt").getFile())
        )) {

            final List<String> lines = reader.lines().collect(Collectors.toList());

            heightMap = new int[lines.size()][];
            checked = new char[lines.size()][];

            int index = 0;

            for (final String line : lines) {
                heightMap[index] = new int[line.length()];
                checked[index] = new char[line.length()];
                int charIndex = 0;
                for (final char c : line.toCharArray()) {
                    heightMap[index][charIndex] = Integer.parseInt(String.valueOf(c));
                    charIndex++;
                }
                index++;
            }

            calculatePoints(0, 0);

            System.out.println("lowest: " + lowest);

            calculateBasins();

            int total = 1;

            Collections.sort(basins);

            for (int i = basins.size() -1; i >= basins.size() - 3; i--) {
                total *= basins.get(i);
            }

            System.out.println("Total = " + total);

        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    private static int calculatePoints(final int row, final int column) {

        if (row >= heightMap.length || row < 0) return Integer.MAX_VALUE;
        if (column >= heightMap[row].length || column < 0) return Integer.MAX_VALUE;

        if (checked[row][column] != '\u0000') return heightMap[row][column];

        final int right = calculatePoints(row, column + 1);
        final int up = calculatePoints(row + 1, column);

        final int left;
        final int down;

        if (column == 0) {
            left = Integer.MAX_VALUE;
        } else {
            left = heightMap[row][column - 1];
        }

        if (row == 0) {
            down = Integer.MAX_VALUE;
        } else {
            down = heightMap[row - 1][column];
        }


        final int current = heightMap[row][column];

        if (current < right && current < up && current < down && current < left) {
            lowest += current + 1;
            checked[row][column] = 'y';
        } else {
            checked[row][column] = 'n';
        }
        return current;
    }

    private static void calculateBasins() {
        for (int i = 0; i < checked.length; i++) {
            final char[] map = checked[i];
            for (int j = 0; j < map.length; j++) {
                final char c = map[j];

                if (c == 'n') continue;

                final AtomicInteger integer = new AtomicInteger(0);

                recursiveBasin(
                        i,
                        j,
                        new HashSet<>(),
                        integer
                );

                basins.add(integer.get());
            }
        }
    }

    private static void recursiveBasin(final int row,
                                       final int column,
                                       final Set<Pair> checked,
                                       final AtomicInteger total) {
        if (row >= heightMap.length || row < 0) return;
        final int[] map = heightMap[row];
        if (column >= map.length || column < 0) return;

        if (checked.contains(Pair.of(row, column))) return;

        checked.add(Pair.of(row, column));

        final int current = map[column];

        if (current == 9) return;

        recursiveBasin(row, column + 1, checked, total);
        recursiveBasin(row + 1, column, checked, total);

        if (column != 0) {
            recursiveBasin(row, column -1, checked, total);
            checked.add(Pair.of(row, column));
        }

        if (row != 0) {
            recursiveBasin(row - 1, column, checked, total);
            checked.add(Pair.of(row, column));
        }

        total.addAndGet(1);
    }

    private static record Pair(int x, int y) {

        public static Pair of(final int x, final int y) {
            return new Pair(x, y);
        }

    }
}
