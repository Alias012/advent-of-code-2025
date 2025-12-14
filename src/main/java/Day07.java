import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import util.ReadFile;

public class Day07 {
    public static void main(String[] args) {
        System.out.println(Day07.countSplits("src/main/resources/inputs/Day07.txt")); //p1
        System.out.println(Day07.countTimelines("src/main/resources/inputs/Day07.txt")); //p2
    }

    private static int countSplits(String filename) {
        ArrayList<String> lines = ReadFile.getAllLines(filename);
        int start = lines.removeFirst().indexOf('S');
        int numLines = lines.size();

        HashSet<Integer> activeBeams = new HashSet<>();
        HashSet<Integer> toRemove = new HashSet<>();
        HashSet<Integer> toAdd = new HashSet<>();
        activeBeams.add(start);

        int count = 0;
        for (int i = 0; i < numLines; i++) {
            for (Integer x : activeBeams) {
                if (lines.get(i).charAt(x) == '^') {
                    toRemove.add(x);
                    toAdd.add(x - 1);
                    toAdd.add(x + 1);
                    count++;
                }
            }
            activeBeams.removeAll(toRemove);
            toRemove.clear();
            activeBeams.addAll(toAdd);
            toAdd.clear();
        }
        return count;
    }

    private static long countTimelines(String filename) {
        ArrayList<String> lines = ReadFile.getAllLines(filename);
        int start = lines.removeFirst().indexOf('S');
        int numLines = lines.size();

        HashSet<Integer> activeBeams = new HashSet<>();
        HashSet<Integer> toRemove = new HashSet<>();
        HashSet<Integer> toAdd = new HashSet<>();
        HashMap<Integer, Long> timelineCounter = new HashMap<>();
        activeBeams.add(start);
        timelineCounter.put(start, 1L);

        for (int i = 0; i < numLines; i++) {
            for (Integer x : activeBeams) {
                if (lines.get(i).charAt(x) == '^') {
                    toRemove.add(x);
                    toAdd.add(x - 1);
                    timelineCounter.put(x - 1, timelineCounter.getOrDefault(x - 1, 0L) + timelineCounter.get(x));
                    toAdd.add(x + 1);
                    timelineCounter.put(x + 1, timelineCounter.getOrDefault(x + 1, 0L) + timelineCounter.get(x));
                    timelineCounter.remove(x);
                }
            }
            activeBeams.removeAll(toRemove);
            toRemove.clear();
            activeBeams.addAll(toAdd);
            toAdd.clear();
        }

        return timelineCounter.values().stream().reduce(0L, Long::sum);
    }
}
