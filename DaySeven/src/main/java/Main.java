import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// This is the best solution you will ever see to day 7
public class Main {

    private static final List<Integer> input = new ArrayList<>();

    public static void main(String args[]) {


        try (final BufferedReader reader = new BufferedReader(
                new FileReader(Main.class.getResource("input.txt").getFile()))) {

            final String[] strings = reader.readLine().split(",");

            for (final String s : strings) {
                input.add(Integer.parseInt(s));
            }

            int highestInput = 0;

            for (final int i : input) {
                highestInput = Math.max(i, highestInput);
            }

            System.out.println(highestInput);

            int highest = 0;
            int best = 0;

            for (int mode = 1; mode < highestInput; mode++) {
                int value = 0;

                int index = 0;
                for (final int x : input) {

                    final int abs = Math.abs(mode - x);

                    int k = 0;
                    for (int n = 0; n <= abs; n++) {
                        k += n;
                    }

                    value += k;
                }

                if (highest == 0) {
                    highest = value;
                }

                highest = Math.min(highest, value);

                if (highest == value) best = mode;
            }

            System.out.println(highest);
            System.out.println(best);

        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    private static int getMode(final List<Integer> list) {
        final Map<Integer, Integer> map = new HashMap<>();

        for (final int i : list) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        int highest = 0;
        int value = 0;

        for (final var entry : map.entrySet()) {
            if (entry.getValue() > value) {
                value = entry.getValue();
                highest = entry.getKey();
            }
        }

        return highest;
    }
}
