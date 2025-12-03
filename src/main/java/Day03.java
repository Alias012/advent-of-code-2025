import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day03 {
    public static void main(String[] args) {
        System.out.println(new Day03().maxJoltage("src/main/resources/Day03.txt", 2));
        System.out.println(new Day03().maxJoltage("src/main/resources/Day03.txt", 12));
    }

    private long maxJoltage(String filename, int numJolts) {
        long count = 0;
        char[] jolts = new char[numJolts];
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                maxBattery(line, jolts);
                count += Long.parseLong(new String(jolts));
                jolts = new char[numJolts];
            }
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        }
        return count;
    }

    private void maxBattery(String line, char[] jolts) {
        char[] chars = line.toCharArray();
        int numJolts = jolts.length;
        int start = chars.length - numJolts;
        int lastFound = -1;
        int found = 0;
        for (int i = 0; i < numJolts; i++) {
            for (int j = start + i; j > lastFound; j--) {
                char c = chars[j];
                if (c >= jolts[i]) {
                    jolts[i] = c;
                    found = j;
                }
            }
            lastFound = found;
        }
    }
}
