import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // age, amount of fish at age
    private static final Map<Integer, History> map = new HashMap<>();

    public static void main(String args[]) {
        try (final BufferedReader reader = new BufferedReader(new FileReader(
                Main.class.getResource("input.txt").getFile()
        ))) {

            reader.lines().forEach(line -> {
                for (final String s : line.split(",")) {
                    addPreviousToAge(Integer.parseInt(s), 1L);
                }
            });

            for (int i = 0; i < 256; i++) {
                addFish();
            }

            System.out.println(calculateFish());
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void addFish() {
        for (int age = 8; age >= 0; age--) {
            final History history = map.getOrDefault(age, new History());

            if (age == 0) {
                addToAge(6, history.previous);
                addToAge(8, history.previous);
            } else {
                addToAge(age - 1, history.previous);
            }
        }

        for (int age = 0; age <= 8; age++) {
            final History history = map.get(age);

            if (history == null) {
                continue;
            }

            history.previous = history.current;
            history.current = 0;
        }
    }

    private static long calculateFish() {
        long total = 0;
        for (final var entry : map.entrySet()) {
            total += entry.getValue().previous;
        }
        return total;
    }

    private static void addToAge(final int age, final long amount) {
        final History history = map.getOrDefault(age, new History());
        addToAge(history, amount);
        map.put(age, history);
    }

    private static void addToAge(final History history, final long amount) {
        history.addCurrent(amount);
    }

    private static void addPreviousToAge(final int age, final long amount) {
        final History history = map.getOrDefault(age, new History());
        addPreviousToAge(history, amount);
        map.put(age, history);
    }

    private static void addPreviousToAge(final History history, final long amount) {
        history.addPrevious(amount);
    }

    static class History {

        public long previous;
        public long current;

        public History() {
            this(0, 0);
        }

        public History(final long previous, final long current) {
            this.previous = previous;
            this.current = current;
        }

        public void addPrevious(final long previous) {
            this.previous += previous;
        }

        public void addCurrent(final long current) {
            this.current += current;
        }

        @Override
        public String toString() {
            return "History{" +
                    "previous: " + previous + ", " +
                    "current: " + current + "}";
        }

    }
}
