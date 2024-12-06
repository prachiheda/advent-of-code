import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Solution4 {
    private static final String WORD = "XMAS";
    private static final int[][] DIRECTIONS = {
        {0, 1},   // Right
        {0, -1},  // Left
        {1, 0},   // Down
        {-1, 0},  // Up
        {1, 1},   // Diagonal down-right
        {1, -1},  // Diagonal down-left
        {-1, 1},  // Diagonal up-right
        {-1, -1}  // Diagonal up-left
    };

    public static int[] getRowsCols(String filename){
        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(Path.of(filename));

            // The number of rows is the number of lines
            int rows = lines.size();

            // The number of columns is determined by the first non-empty line
            int cols = 0;
            for (String line : lines) {
                cols = line.length(); // Get the length of the first non-empty line
                break;
            }

            // Return rows and cols as an array
            return new int[]{rows, cols};
        } catch (IOException e) {
            e.printStackTrace();
            return new int[]{-1, -1}; // Return an error indicator in case of failure
        }
    }

    public static char[][] createMatrix(String filename, int rows, int cols){
        char[][] matrix = new char[rows][cols]; 
        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(Path.of(filename));

            for (int i=0; i<rows; i++) {
                char[] lineArray = lines.get(i).toCharArray(); 
                matrix[i] = lineArray; 
            }

            return matrix;
        } catch (IOException e) {
            e.printStackTrace();
            return new char[][]{}; // Return an error indicator in case of failure
        }
    }

    public static int countOccurrences(char[][] matrix, int rows, int cols){
        String WORD = "XMAS";
        int[][] DIRECTIONS = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        int totalCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 'X') {
                    // Check all directions
                    for (int[] dir : DIRECTIONS) {
                        totalCount += checkDirection(matrix, i, j, dir[0], dir[1], WORD, rows, cols);
                    }
                }
            }
        }

        return totalCount;
    }

    private static int checkDirection(char[][] matrix, int startX, int startY, int dx, int dy, String WORD, int rows, int cols) {
        for (int k = 0; k < WORD.length(); k++) {
            int x = startX + k * dx;
            int y = startY + k * dy;
    
            // Check boundaries
            if (x < 0 || x >= rows || y < 0 || y >= cols || matrix[x][y] != WORD.charAt(k)) {
                return 0;
            }
        }
        return 1; // Found the word
    }

    public static int countXMAS(char[][] matrix, int rows, int cols) {
        int count = 0;
        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {
                if (matrix[r][c] == 'A') {
                    // Check the diagonals
                    char topLeft = matrix[r-1][c-1];
                    char bottomRight = matrix[r+1][c+1];
                    char topRight = matrix[r-1][c+1];
                    char bottomLeft = matrix[r+1][c-1];
    
                    boolean primaryValid = isMAS(topLeft, 'A', bottomRight);
                    boolean secondaryValid = isMAS(topRight, 'A', bottomLeft);
    
                    if (primaryValid && secondaryValid) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
    private static boolean isMAS(char first, char mid, char last) {
        // The mid should always be 'A', as per our checks
        // Valid sequences are M-A-S or S-A-M
        return (first == 'M' && mid == 'A' && last == 'S') ||
               (first == 'S' && mid == 'A' && last == 'M');
    }

    public static void main(String[] args){
        int[] res = getRowsCols("/Users/prachiheda/Desktop/advent of code/advent-of-code/day 4/input.txt"); 
        int rows = res[0]; 
        int cols = res[1]; 
        char[][] matrix = createMatrix("/Users/prachiheda/Desktop/advent of code/advent-of-code/day 4/input.txt", rows, cols); 
        int ans = countOccurrences(matrix, rows, cols);
        System.out.println("Total occurrences of 'XMAS': " + ans);
        int part2 = countXMAS(matrix, rows, cols); 
        System.out.println("Total occurrences of 'XMAS' Part 2: " + part2);


    }
}
