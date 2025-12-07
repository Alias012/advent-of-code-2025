import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import util.ReadFile;

public class Day06 {
    public static void main(String[] args) {
        System.out.println(Day06.verticalMath("src/main/resources/Day06.txt")); //p1
        System.out.println(Day06.cephalopodMath("src/main/resources/Day06.txt")); //p2
    }

    private static long verticalMath(String filename) {
        ArrayList<String[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim().split("\\s+"));
            }
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        }

        int colLength = lines.size();
        String[] operatorRow = lines.remove(colLength - 1);

        int rowLength = lines.getFirst().length;
        long count = 0;

        for (int i = 0; i < rowLength; i++) {
            long[] row = new long[colLength - 1];
            for (int j = 0; j < colLength - 1; j++) {
                row[j] = Long.parseLong(lines.get(j)[i]);
            }

            count += switch (operatorRow[i]) {
                case "*" -> Arrays.stream(row).reduce(1, (x, y) -> x * y);
                case "+" -> Arrays.stream(row).sum();
                default -> -1;
            };
        }

        return count;
    }

    private static long cephalopodMath(String filename) {
        ArrayList<String> lines = ReadFile.getAllLines(filename);
        int colLength = lines.size();
        String operatorRow = lines.remove(colLength - 1);

        int colWidth = 1;
        int totalWidth = 0;
        ArrayList<String[]> transpose = new ArrayList<>();
        ArrayList<String> operators = new ArrayList<>();
        operators.add(String.valueOf(operatorRow.charAt(0)));

        //uses the distance between operators to find width of each column
        //essentially splits on the location of operator and saves spaces
        for (int i = 1; i < operatorRow.length(); i++) {
            char c = operatorRow.charAt(i);
            if (c != ' ') {
                operators.add(String.valueOf(c));
                String[] row = new String[colLength - 1];
                for (int j = 0; j < colLength - 1; j++) {
                    row[j] = lines.get(j).substring(totalWidth, totalWidth + colWidth - 1);
                }
                totalWidth += colWidth;
                colWidth = 0;
                transpose.add(row);
            }
            colWidth++;
        }

        //the last column
        String[] row = new String[colLength - 1];
        for (int j = 0; j < colLength - 1; j++) {
            row[j] = lines.get(j).substring(totalWidth);
        }
        transpose.add(row);

        return transpose.stream()
                        .map(oldRow -> { //find the column-wise values
                                    int length = oldRow[0].length();
                                    ArrayList<Long> newRow = new ArrayList<>();
                                    while (length-- != 0) {
                                        long newNum = 0;
                                        for (String num : oldRow) {
                                            char c = num.charAt(length);
                                            if (c != ' ') {
                                                newNum = newNum * 10 + Character.getNumericValue(c);
                                            }
                                        }
                                        if (newNum != 0) {
                                            newRow.add(newNum);
                                        }
                                    }
                                    return newRow;})
                        .mapToLong(newRow -> {
                                    switch (operators.removeFirst()) {
                                        case "*" -> { return newRow.stream().reduce(1L, (x, y) -> x * y); }
                                        case "+" -> { return newRow.stream().reduce(0L, Long::sum); }
                                        default -> { return -1; }
                                    }})
                        .sum();
    }
}
