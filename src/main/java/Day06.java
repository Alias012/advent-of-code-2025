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
        long count = 0;
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

        int rowLength = lines.get(0).length;
        long[][] transpose = new long[rowLength][colLength - 1];

        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < colLength - 1; j++) {
                transpose[i][j] = Long.parseLong(lines.get(j)[i]);
            }
        }

        for (int i = 0; i < rowLength; i++) {
            count += switch (operatorRow[i]) {
                case "*" -> Arrays.stream(transpose[i]).reduce(1, Day06::multiply);
                case "+" -> Arrays.stream(transpose[i]).sum();
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

        String[] row = new String[colLength - 1];
        for (int j = 0; j < colLength - 1; j++) {
            row[j] = lines.get(j).substring(totalWidth);
        }
        transpose.add(row);

        ArrayList<ArrayList<Long>> cephalopodTranspose = new ArrayList<>(transpose.size());
        for(String[] oldRow : transpose) {
            int length = oldRow[0].length();
            int count = 0;
            ArrayList<Long> newRow = new ArrayList<>();
            while (count != length) {
                long newNum = 0;
                for (String num : oldRow) {
                    char c = num.charAt(length - count - 1);
                    if (c != ' ') {
                        newNum = newNum * 10 + Byte.parseByte(String.valueOf(c));
                    }
                }
                if (newNum != 0) {
                    newRow.add(newNum);
                }
                count++;
            }
            cephalopodTranspose.add(newRow);
        }

        long count = 0;
        for (int i = 0; i < cephalopodTranspose.size(); i++) {
            count += switch (operators.get(i)) {
                case "*" -> cephalopodTranspose.get(i).stream().reduce(1L, Day06::multiply);
                case "+" -> cephalopodTranspose.get(i).stream().reduce(0L, Day06::add);
                default -> -1;
            };
        }

        return count;
    }

    private static long multiply(long l1, long l2) {
        return l1 * l2;
    }

    private static long add(long l1, long l2) {
        return l1 + l2;
    }
}
