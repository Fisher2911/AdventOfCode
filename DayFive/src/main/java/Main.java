import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Map<Pair, Integer> pairAppearances = new HashMap<>();

    public static void main(String[] args) {
        try (final BufferedReader reader = new BufferedReader(
                new FileReader(Main.class.getResource("input.txt").getFile()))) {
            reader.lines().forEach(
                    line -> {
                        final Line l = Line.fromString(line);
                        for (final Pair pair : l.walk()) {
                            pairAppearances.put(pair, pairAppearances.getOrDefault(pair, 0) + 1);
                        }
                    }
            );

            int total = 0;
            for (final var entry : pairAppearances.entrySet()) {
                final int value = entry.getValue();

                if (value >= 2) {
                    total++;
                }
            }

            System.out.println("Total is: " + total);

        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
}
