import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import util.ReadFile;
import util.Range;

public class Day05 {
    public static void main(String[] args) {
        System.out.println(Day05.countAvailableFresh("src/main/resources/Day05.txt")); //p1
        System.out.println(Day05.countAllFresh("src/main/resources/Day05.txt")); //p2
    }

    private static long countAvailableFresh(String filename) {
        ArrayList<String> freshIDs = new ArrayList<>(); //before empty line in file
        ArrayList<String> availableIDs = new ArrayList<>(); //after empty line in file

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while (!(line = reader.readLine()).isEmpty()) {
                freshIDs.add(line);
            }

            while ((line = reader.readLine()) != null) {
                availableIDs.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        }

        ArrayList<Range> freshRanges = loadRanges(freshIDs);
        return availableIDs.stream()
                           .mapToLong(Long::parseLong)
                           .map(id -> freshRanges.stream().anyMatch(range -> range.isInRange(id)) ? 1 : 0)
                           .sum();
    }

    private static long countAllFresh(String filename) {
        return loadRanges(ReadFile.getLinesUntilBreak(filename)).stream()
                                                                .mapToLong(Range::getCoverage)
                                                                .sum();
    }

    private static ArrayList<Range> loadRanges(ArrayList<String> freshIDs) {
        ArrayList<Range> freshRanges = new ArrayList<>();
        for (String freshRange : freshIDs) {
            Range newRange = new Range(freshRange);
            freshRanges.removeIf(newRange::mergeRange);
            freshRanges.add(newRange);
        }
        return freshRanges;
    }
}
