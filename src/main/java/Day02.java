import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day02 {
    public static void main(String[] args) {
        System.out.println(new Day02().repeatedIDs("src/main/resources/Day02.txt"));
        System.out.println(new Day02().manyRepeatedIDs("src/main/resources/Day02.txt"));
    }

    private long repeatedIDs(String filename) {
        long count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] ranges = reader.readLine().split(",");
            for (String range : ranges) {
                int indexHyphen = range.indexOf('-');
                long lower = Long.parseLong(range.substring(0, indexHyphen));
                long higher = Long.parseLong(range.substring(indexHyphen + 1)) + 1;
                for (long i = lower; i < higher; i++) {
                    String val = String.valueOf(i);
                    int halfLength = val.length() / 2;
                    if (val.substring(0, halfLength).equals(val.substring(halfLength))) {
                        count += i;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        }
        return count;
    }

    private long manyRepeatedIDs(String filename) {
        long count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] ranges = reader.readLine().split(",");
            for (String range : ranges) {
                int indexHyphen = range.indexOf('-');
                long lower = Long.parseLong(range.substring(0, indexHyphen));
                long higher = Long.parseLong(range.substring(indexHyphen + 1)) + 1;
                for (long i = lower; i < higher; i++) {
                    String val = String.valueOf(i);
                    int length = val.length();
                    for (int j = 1; j <= length / 2; j++) {
                        if (length % j != 0) {
                            continue;
                        }
                        String match = val.substring(0, j);
                        boolean conflict = false;
                        for (int k = j; k < length - j + 1; k += j) {
                            String part = val.substring(k, k + j);
                            if (!match.equals(part)) {
                                conflict = true;
                                break;
                            }
                        }
                        if (!conflict) {
                            count += i;
                            break;
                        }
                    }
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        }
        return count;
    }
}
