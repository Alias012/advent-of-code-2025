import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day01 {
    public static void main(String[] args) {
        System.out.println(Day01.crackLock("src/main/resources/inputs/Day01.txt")); //p1
        System.out.println(Day01.crackSecureLock("src/main/resources/inputs/Day01.txt")); //p2
    }

    private static int crackLock(String filename) {
        int lock = 50;
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                char direction = line.charAt(0);
                int rotations = Integer.parseInt(line.substring(1));
                if (direction == 'L') {
                    rotations = -rotations;
                }
                lock = (lock + rotations) % 100;
                if (lock == 0) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        } catch (NumberFormatException e) {
            System.err.println("Error in input line format...");
        }
        return count;
    }

    private static int crackSecureLock(String filename) {
        int lock = 50;
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                char direction = line.charAt(0);
                int rotations = Integer.parseInt(line.substring(1));

                int overflow = rotations / 100;
                count += overflow;

                int leftover = rotations - (100 * overflow);
                if (direction == 'L') {
                    leftover = -leftover;
                }

                int newLock = lock + leftover;
                if (newLock > 99 || (newLock < 0 && lock != 0)) {
                    count++;
                }
                lock = Math.abs(newLock % 100);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        } catch (NumberFormatException e) {
            System.err.println("Error in input line format...");
        }
        return count;
    }
}