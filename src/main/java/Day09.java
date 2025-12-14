import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import util.Pair;
import util.ReadFile;

public class Day09 {
    public static void main(String[] args) {
        System.out.println(Day09.biggestRectangle("src/main/resources/inputs/Day09.txt")); //p1
        Day09.biggestInnerRectangle("src/main/resources/inputs/Day09.txt"); //p2
    }

    public static long biggestRectangle(String filename) {
        ArrayList<Point> points = ReadFile.getAllLines(filename)
                                          .stream()
                                          .map(Point::new)
                                          .collect(Collectors.toCollection(ArrayList::new));

        int numPoints = points.size();
        long largestArea = -1;
        for (int i = 0; i < numPoints; i++) {
            Point point = points.get(i);
            for (int j = i + 1; j < numPoints; j++) {
                long area = point.getRectangleArea(points.get(j));
                if (area > largestArea) {
                    largestArea = area;
                }
            }
        }

        return largestArea;
    }

    public static long biggestInnerRectangle(String filename) {
        ArrayList<Point> points = ReadFile.getAllLines(filename)
                                          .stream()
                                          .map(Point::new)
                                          .collect(Collectors.toCollection(ArrayList::new));

        int numPoints = points.size();
        Pair<Point, Point>[] edges = new Pair[numPoints];
        for (int i = 0; i < numPoints - 1; i++) {
            edges[i] = new Pair<>(points.get(i), points.get(i + 1));
        }
        edges[numPoints - 1] = new Pair<>(points.get(numPoints - 1), points.getFirst());

        long largestArea = 0;
        for (int i = 0; i < numPoints; i++) {
            Point point = points.get(i);
            for (int j = i + 1; j < numPoints; j++) {
                Point other = points.get(j);
                int recLowX = Math.min(point.getX(), other.getX());
                int recHighX = Math.max(point.getX(), other.getX());
                int recLowY = Math.min(point.getY(), other.getY());
                int recHighY = Math.max(point.getY(), other.getY());

                boolean conflict = false;
                for (Pair<Point, Point> edge : edges) {
                    int edgeLowX = Math.min(edge.getX().getX(), edge.getY().getX());
                    int edgeHighX = Math.max(edge.getX().getX(), edge.getY().getX());
                    int edgeLowY = Math.min(edge.getX().getY(), edge.getY().getY());
                    int edgeHighY = Math.max(edge.getX().getY(), edge.getY().getY());

                    if (!(recHighX <= edgeLowX || recLowX >= edgeHighX || recHighY <= edgeLowY || recLowY >= edgeHighY)) {
                        conflict = true;
                        break;
                    }
                }
                if (!conflict) {
                    largestArea = Math.max(largestArea, (recHighX - recLowX + 1) * (recHighY - recLowY + 1));
                }
            }
        }
        return largestArea;
    }

    private static class Point {
        private final int x;
        private final int y;

        public Point(String line) {
            String[] coords = line.split(",");
            this.x = Integer.parseInt(coords[0]);
            this.y = Integer.parseInt(coords[1]);
        }

        public long getRectangleArea(Point other) {
            return (Math.abs(x - other.x) + 1) * (Math.abs(y - other.y) + 1);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Point p) {
                return x == p.getX() && y == p.getY();
            }
            return false;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}
