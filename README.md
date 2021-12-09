# AdventOfCode
Please ignore day 3

import java.util.*;

public class MyClass {
    
    private static int[][] heightMap = null;
    private static char[][] checked = null;
    private static int lowest = 0;
    
    public static void main(String args[]) {
        // try (final BufferedReader reader = new BufferedReader(
                // new FileReader("input.txt");
            // ));
            
            final Scanner scanner = new Scanner(System.in);
            
            // final Stream<String> lines = reader.lines();
            
            final List<String> lines = new ArrayList<>();
            
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
            
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
            
            // for (int i = 0; i < heightMap.length; i++) {
                // for (int j = 0; j < heightMap[i].length; j++) {
                    // System.out.print(heightMap[i][j] + " ");
                // }
                // System.out.print("\n");
            // }
            
            calculatePoints(0, 0);
            
            // for (int i = 0; i < checked.length; i++) {
                // for (int j = 0; j < checked[i].length; j++) {
                    // System.out.print(checked[i][j] + " ");
                // }
                // System.out.print("\n");
            // }            
            
            System.out.println("lowest: " + lowest);
    // } catch (final IOException e) {
        // e.printStackTrac
    // }
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

        // if (current < up && left < current && right < current && down < current) {
        //     lowest += current;
        // }
        
        if (current < right && current < up && current < down && current < left) {
            lowest += current + 1;
            checked[row][column] = 'y';
        } else {        
            checked[row][column] = 'n';
        }
        return current; 
        
    }
}
