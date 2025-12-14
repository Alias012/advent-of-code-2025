import java.util.ArrayList;
import java.util.HashMap;
import util.Pair;
import util.ReadFile;

public class Day11 {
    public static void main(String[] args) {
        System.out.println(Day11.countPathsOut("src/main/resources/Day11.txt")); //p1
        System.out.println(Day11.countSpecialPathsOut("src/main/resources/Day11.txt")); //p2
    }

    public static long countPathsOut(String filename) {
        HashMap<String, String[]> graph = parseGraph(filename);
        HashMap<String, Long> cache = new HashMap<>();
        cache.put("out", 1L);
        return countPathsToTarget("you", graph, cache);
    }

    public static long countSpecialPathsOut(String filename) {
        HashMap<String, String[]> graph = parseGraph(filename);
        ArrayList<Pair<String, String>> paths = new ArrayList<>();

        paths.add(new Pair<>("svr", "dac"));
        paths.add(new Pair<>("dac", "fft"));
        paths.add(new Pair<>("fft", "out"));
        long pathA = getPathResult(graph, paths);

        paths.add(new Pair<>("svr", "fft"));
        paths.add(new Pair<>("fft", "dac"));
        paths.add(new Pair<>("dac", "out"));
        long pathB = getPathResult(graph, paths);

        return pathA + pathB;
    }

    private static long getPathResult(HashMap<String, String[]> devicePaths, ArrayList<Pair<String, String>> paths) {
        long count = 1L;
        while (!paths.isEmpty()) {
            Pair<String, String> path =  paths.removeFirst();
            HashMap<String, Long> cache = new HashMap<>();
            cache.put(path.getY(), 1L);
            count *= countPathsToTarget(path.getX(), devicePaths, cache);
        }
        return count;
    }

    private static long countPathsToTarget(String node, HashMap<String, String[]> devicePaths, HashMap<String, Long> cache) {
        if (cache.containsKey(node)) {
            return cache.get(node);
        }

        long count = 0;
        for (String child : devicePaths.getOrDefault(node, new String[0])) {
            count += countPathsToTarget(child, devicePaths, cache);
        }
        cache.put(node, count);
        return count;
    }

    private static HashMap<String, String[]> parseGraph(String filename) {
        ArrayList<String> lines = ReadFile.getAllLines(filename);
        HashMap<String, String[]> graph = new HashMap<>();
        for (String line : lines) {
            String node = line.substring(0, 3);
            String[] children = line.substring(5).split("\\s");
            graph.put(node, children);
        }
        return graph;
    }
}
