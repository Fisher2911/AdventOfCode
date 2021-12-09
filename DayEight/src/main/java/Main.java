import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class Main {

    private static final Map<Integer, Integer> UNIQUE_DIGITS = Map.of(2, 1, 4, 4, 3, 7, 7, 8);

    private static final Map<String, String> input = new HashMap<>();

    public static void main(String[] args) {
        try (final BufferedReader reader = new BufferedReader(
                new FileReader(Main.class.getResource("input.txt").getFile())
        )) {

            reader.lines().forEach(line -> {
                final String[] parts = line.split(" [|] ");

                input.put(parts[0], parts[1]);
            });

            partOne();
            partTwo();

        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void partOne() {

        int total = 0;

        for (final var entry : input.entrySet()) {
            final String value = entry.getValue();

            final String[] parts = value.split(" ");

            for (final String part : parts) {
                if (UNIQUE_DIGITS.containsKey(part.length())) {
                    total++;
                }
            }
        }

        System.out.println("Unique: " + total);
    }

    private static void partTwo() {

        int total = 0;

        for (final var entry : input.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();

            final Map<String, Integer> values = new HashMap<>();

            final String[] parts = key.split(" ");
            final Map<Integer, Set<Character>> numberChars = new HashMap<>();

            for (final String part : parts) {
                final Integer digit = UNIQUE_DIGITS.get(part.length());

                if (digit == null) {
                    continue;
                }

                values.put(part, digit);

                for (final char c : part.toCharArray()) {
                    final Set<Character> possible = numberChars.getOrDefault(
                            digit, new HashSet<>()
                    );

                    possible.add(c);

                    numberChars.put(digit, possible);
                }
            }

            for (int i = 0; i <= 9; i++) {
                for (final String part : parts) {

                    if (checkNumber(
                            part,
                            3,
                            numberChars,
                            () -> isThree(numberChars.get(1), part)
                    )) continue;

                    if (checkNumber(
                            part,
                            9,
                            numberChars,
                            () -> isNine(numberChars.get(3), part)
                    )) continue;

                    if (checkNumber(
                            part,
                            0,
                            numberChars,
                            () -> isZero(numberChars.get(7), part)
                    )) continue;

                    if (part.length() == 6) {
                        final Set<Character> characters = new HashSet<>();
                        for (final char c : part.toCharArray()) {
                            characters.add(c);
                        }
                        numberChars.put(6, new HashSet<>(characters));
//                        System.out.println("Is six: " + part);
                        continue;
                    }

                    if (checkNumber(
                            part,
                            5,
                            numberChars,
                            () -> isFive(numberChars.get(9), part)
                    )) continue;

                    if (part.length() == 5) {
                        final Set<Character> characters = new HashSet<>();
                        for (final char c : part.toCharArray()) {
                            characters.add(c);
                        }
                        numberChars.put(2, new HashSet<>(characters));
//                        System.out.println("Is two: " + part);
                    }
                }
            }

            final StringBuilder builder = new StringBuilder("");

            for (final String string : value.split(" ")) {
                for (final var e : numberChars.entrySet()) {
                    final Set<Character> chars = new HashSet<>();
                    for (final char c : string.toCharArray()) {
                        chars.add(c);
                    }
                    if (e.getValue().equals(chars)) {
                        builder.append(e.getKey());
                    }
                }
            }

            total += Integer.parseInt(builder.toString());
            System.out.println("Added: " + builder);
        }

        System.out.println("Total is: " + total);
    }

    private static boolean checkNumber(
            final String part,
            final int digit,
            final @Nullable Map<Integer, Set<Character>> numberChars,
            final Supplier<Boolean> supplier) {

        if (numberChars == null) {
            return false;
        }

        if (supplier.get()) {
            final Set<Character> characters = new HashSet<>();
            for (final char c : part.toCharArray()) {
                characters.add(c);
            }
            numberChars.put(digit, new HashSet<>(characters));
            return true;
        }

        return false;
    }

    private static boolean isThree(@Nullable final Set<Character> oneChars, final String input) {
        if (input.length() != 5 || oneChars == null) {
            return false;
        }

        final boolean hasChars = stringHasChars(input, oneChars);

        if (!hasChars) return false;

//        System.out.println("Is three: " + input);
        return hasChars;
    }

    private static boolean isNine(@Nullable final Set<Character> threeChars, final String input) {
        if (input.length() != 6 || threeChars == null) {
            return false;
        }

        final boolean hasChars = stringHasChars(input, threeChars);

        if (!hasChars) return false;

//        System.out.println("Is nine: " + input);
        return hasChars;
    }

    private static boolean isZero(@Nullable final Set<Character> sevenChars, final String input) {
        if (input.length() != 6 || sevenChars == null) {
            return false;
        }

        final boolean hasChars = stringHasChars(input, sevenChars);

        if (!hasChars) return false;

//        System.out.println("Is zero: " + input);
        return hasChars;
    }

    private static boolean isFive(@Nullable final Set<Character> nineChars, final String input) {
        if (input.length() != 5 || nineChars == null) {
            return false;
        }

        for (final char c : input.toCharArray()) {
            if (!nineChars.contains(c)) {
                return false;
            }
        }

//        System.out.println("Is five: " + input);
        return true;
    }

    private static boolean stringHasChars(final String input, final Set<Character> chars) {
        for (final Character c : chars) {
            if (!input.contains(c.toString())) {
                return false;
            }
        }

        return true;
    }

}
