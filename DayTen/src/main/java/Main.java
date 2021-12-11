import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static final Map<Character, Character> GROUPS = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
    private static final Set<Character> START_CHARS = Collections.unmodifiableSet(GROUPS.keySet());
    private static final Set<Character> END_CHARS = Set.copyOf(GROUPS.values());
    private static final Map<Character, Integer> POINTS = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);
    private static final Map<Character, Integer> COMPLETION_POINTS = Map.of(')', 1, ']', 2, '}', 3, '>', 4);

    private static final List<String> input = new ArrayList<>();

    public static void main(String[] args) {
        try (final BufferedReader reader = new BufferedReader(
                new FileReader(Main.class.getResource("input.txt").getFile())
        )) {

            input.addAll(reader.lines().collect(Collectors.toList()));

            int total = 0;
            final List<Long> completions = new ArrayList<>();

            for (final String line : input) {
                total += checkSyntax(line);
                final long score = completeLine(line);

                if (score != 0) {
                    completions.add(score);
                }
            }

            System.out.println("Total = " + total);

            Collections.sort(completions);

            System.out.println("Middle: " + completions.get(completions.size() / 2));

        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    private static int checkSyntax(final String line) {
        char[] chars = line.toCharArray();

        int distance = 1;

        int length = chars.length;

        while (distance < length) {
            for (int i = 0; i < chars.length - distance; i++) {
                if (i + distance > length) break;

                final char start = chars[i];
                final char end = chars[i + distance];

                if (START_CHARS.contains(start) && matches(start, end)) {
                    chars[i] = ' ';
                    chars[i + distance] = ' ';
                    continue;
                }

                if (START_CHARS.contains(start) && END_CHARS.contains(end)) {
                    return POINTS.get(end);
                }
            }

            distance++;
        }

        return 0;
    }

    private static long completeLine(final String line) {
        final char[] chars = line.toCharArray();

        int distance = 1;

        int length = chars.length;

        while (distance < length) {
            for (int i = 0; i < chars.length - distance; i++) {
                if (i + distance > length) break;

                final char start = chars[i];
                final char end = chars[i + distance];

                if (START_CHARS.contains(start) && matches(start, end)) {
                    chars[i] = ' ';
                    chars[i + distance] = ' ';
                    continue;
                }

                if (START_CHARS.contains(start) && END_CHARS.contains(end)) {
                    return 0;
                }
            }

            distance++;
        }

        final StringBuilder builder = new StringBuilder();

        for (int i = chars.length - 1; i >= 0; i--) {
            if (!START_CHARS.contains(chars[i])) continue;

            builder.append(GROUPS.get(chars[i]));
        }

        final String string = builder.toString();

        long total = 0;

        for (final char c : string.toCharArray()) {
            total *= 5;
            total += COMPLETION_POINTS.get(c);
        }

        return total;
    }

    private static boolean matches(final char start, final char end) {
        return GROUPS.get(start) == end;
    }

}
