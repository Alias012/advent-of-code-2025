import java.util.ArrayList;
import java.util.Arrays;
import util.ReadFile;

public class Day04 {
    public static void main(String[] args) {
        System.out.println(new Day04().accessibleRolls("src/main/resources/Day04.txt"));
        System.out.println(new Day04().recursivelyAccessibleRolls("src/main/resources/Day04.txt"));
    }

    private int accessibleRolls(String filename) {
        ArrayList<String> lines = ReadFile.getAllLines(filename);
        int count = 0;
        int rowLength = lines.get(0).length();
        int colLength = lines.size();
        byte[] grid = new byte[rowLength * colLength];
        countFreeRolls(lines, grid, rowLength, colLength);
        for (byte adjacents : grid) {
            if (adjacents < 4) {
                count++;
            }
        }

        return count;
    }

    private void countFreeRolls(ArrayList<String> lines, byte[] grid, int rowLength, int colLength) {
        for (int i = 0; i < rowLength; i++) {
            String line = lines.get(i);
            int rowVal = i * rowLength;
            for (int j = 0; j < colLength; j++) {
                if (line.charAt(j) == '@') {
                    boolean notLowerRow = i != 0;
                    boolean notHigherRow = i != rowLength - 1;
                    boolean notLeftCol = j != 0;
                    boolean notRightCol = j != colLength - 1;

                    if (notLowerRow) {
                        grid[(rowVal - rowLength) + j]++;
                        if (notRightCol) {
                            grid[rowVal - rowLength + j + 1]++;
                        }
                        if (notLeftCol) {
                            grid[(rowVal - rowLength) + (j - 1)]++;
                        }
                    }
                    if (notRightCol) {
                        grid[rowVal + j + 1]++;
                    }
                    if (notLeftCol) {
                        grid[rowVal + (j - 1)]++;
                    }
                    if (notHigherRow) {
                        grid[rowVal + rowLength + j]++;
                        if (notRightCol) {
                            grid[rowVal + rowLength + j + 1]++;
                        }
                        if (notLeftCol) {
                            grid[rowVal + rowLength + j - 1]++;
                        }
                    }
                } else {
                    grid[rowVal + j] = 4;
                }
            }
        }
    }

    private long recursivelyAccessibleRolls(String filename) {
        ArrayList<String> lines = ReadFile.getAllLines(filename);
        int rowLength = lines.get(0).length();
        int colLength = lines.size();
        byte[] grid = new byte[rowLength * colLength];
        return recursiveCountFreeRolls(lines, grid, rowLength, colLength);
    }

    private int recursiveCountFreeRolls(ArrayList<String> lines, byte[] grid, int rowLength, int colLength) {
        int oldCount = 0;
        int newCount = 0;

        while (true) {
            countFreeRolls(lines, grid, rowLength, colLength);
            for (int i = 0; i < grid.length; i++) {
                if (grid[i] < 4) {
                    newCount++;
                    int row = i / rowLength;
                    int col = i % rowLength;
                    String line = lines.get(row);
                    lines.set(row, line.substring(0, col) + '.' + line.substring(col + 1));
                }
            }
            if (newCount == oldCount) {
                break;
            }
            oldCount = newCount;
            Arrays.fill(grid, (byte) 0);
        }

        return newCount;
    }
}
