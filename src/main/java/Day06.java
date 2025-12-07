import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
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

        int rowLength = lines.getFirst().length;
        int colLength = lines.size() - 1;
        String[] operatorRow = lines.remove(colLength);

        // transpose the list then reduce each row with relevant operator
        return IntStream.iterate(0, i -> i < rowLength, i -> i + 1)
                        .mapToLong(i -> {
                            LongStream row = IntStream.iterate(0, j -> j < colLength, j -> j + 1)
                                                      .mapToLong(j -> Long.parseLong(lines.get(j)[i]));

                            switch (operatorRow[i]) {
                                case "*" -> { return row.reduce(1, (x, y) -> x * y); }
                                case "+" -> { return row.sum(); }
                                default -> { return -1; }
                            }
                        })
                        .sum();
    }

    private static long cephalopodMath(String filename) {
        ArrayList<String> lines = ReadFile.getAllLines(filename);
        int colLength = lines.size() - 1;
        String operatorRow = lines.remove(colLength);
        ArrayList<String> operators = new ArrayList<>(List.of(operatorRow.split("\\s+")));

        //use the distance between operators to find width of each column
        //essentially split on the location of operator and save spaces
        return Arrays.stream(operatorRow.split("\\S"))
                     .mapToInt(whitespace -> whitespace.length() + 1)
                     .skip(1)
                     .mapToLong(width -> {
                         //transpose and save spaces
                         String[] row = new String[colLength];
                         for (int j = 0; j < colLength; j++) {
                             String line = lines.get(j);
                             row[j] = line.substring(0, width);
                             lines.set(j, line.substring(width));
                         }

                         //find column-wise values (~ transpose again)
                         LongStream cols = IntStream.iterate(width - 1, i -> i != -1, i -> i - 1)
                                                    .mapToLong(length -> {
                                                        long newNum = 0;
                                                        for (String num : row) {
                                                            char c = num.charAt(length);
                                                            if (c != ' ') {
                                                                newNum = newNum * 10 + Character.getNumericValue(c);
                                                            }
                                                        }
                                                        return newNum;
                                                    })
                                                   .filter(num -> num != 0);

                         //reduce the values depending on relevant operator
                         switch (operators.removeFirst()) {
                             case "*" -> { return cols.reduce(1L, (x, y) -> x * y); }
                             case "+" -> { return cols.reduce(0L, Long::sum); }
                             default -> { return -1; }
                         }
                     })
                     .sum();
    }
}
