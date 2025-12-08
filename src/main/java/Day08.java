import java.util.*;
import java.util.stream.Collectors;
import util.ReadFile;

public class Day08 {
    public static void main(String[] args) {
        System.out.println(Day08.threeBiggestGroups("src/main/resources/Day08.txt")); //p1
        System.out.println(Day08.distanceOfLastConnection("src/main/resources/Day08.txt")); //p2
    }

    private static long threeBiggestGroups(String filename) {
        ArrayList<Point> points = ReadFile.getAllLines(filename)
                                        .stream()
                                        .map(Point::new)
                                        .collect(Collectors.toCollection(ArrayList::new));
        int numPoints = points.size();
        HashMap<Integer, HashSet<Point>> groupToPoints = new HashMap<>();
        HashSet<Pair<Point, Point>> matches = new HashSet<>();

        for (int i = 0; i < numPoints; i++) {
            Point point = points.get(i);
            point.setGroup(i);
            groupToPoints.put(i, new HashSet<>(Collections.singletonList(point)));
        }

        int count = 0;
        do {
            HashMap<Point, Pair<Point, Double>> pointToNearest = new HashMap<>();
            for (int i = 0; i < numPoints; i++) {
                Point point = points.get(i);
                Point nearestPoint = null;
                double lowestDistance = Double.MAX_VALUE;
                for (int j = i + 1; j < numPoints; j++) {
                    Point other = points.get(j);
                    if (!matches.contains(new Pair<>(point, other))) {
                        double distance = point.getEuclideanDistance(other);
                        if (distance < lowestDistance) {
                            lowestDistance = distance;
                            nearestPoint = other;
                        }
                    }
                }
                pointToNearest.put(point, new Pair<>(nearestPoint, lowestDistance));
            }

            Pair<Point, Point> closestPoints = null;
            double lowestDistance = Double.MAX_VALUE;
            for (Map.Entry<Point, Pair<Point, Double>> entry : pointToNearest.entrySet()) {
                if (entry.getValue().y() < lowestDistance) {
                    lowestDistance = entry.getValue().y();
                    closestPoints = new Pair<>(entry.getKey(), entry.getValue().x());
                }
            }

            int a = closestPoints.x().getGroup();
            int b = closestPoints.y().getGroup();
            if (a != b) {
                HashSet<Point> groupA = groupToPoints.get(a);
                HashSet<Point> groupB = groupToPoints.get(b);
                if (groupA.size() > groupB.size()) {
                    groupA.addAll(groupB);
                    groupToPoints.remove(b);
                    groupB.forEach(point -> point.setGroup(a));
                } else {
                    groupB.addAll(groupA);
                    groupToPoints.remove(a);
                    groupA.forEach(point -> point.setGroup(b));
                }
            }
            matches.add(new Pair<>(closestPoints.x(), closestPoints.y()));
        } while (++count != 1000);

        List<Integer> groupSizes = groupToPoints.values()
                            .stream()
                            .mapToInt(HashSet::size)
                            .boxed()
                            .sorted()
                            .collect(Collectors.collectingAndThen(Collectors.toList(), list -> { Collections.reverse(list); return list; }));
        return (long) groupSizes.get(0) * groupSizes.get(1) * groupSizes.get(2);
    }

    private static long distanceOfLastConnection(String filename) {
        ArrayList<String> lines = ReadFile.getAllLines(filename);
        int numPoints = lines.size();
        Point[] points = new Point[numPoints];
        HashMap<Integer, HashSet<Point>> groupToPoints = new HashMap<>();
        for (int i = 0; i < numPoints;) {
            Point point = new Point(lines.get(i));
            point.setGroup(i);
            points[i] = point;
            HashSet<Point> newSet = new HashSet<>();
            newSet.add(point);
            groupToPoints.put(i++, newSet);
        }

        int numGroups = numPoints;
        Pair<Point, Point> closestPoints = null;
        do {
            double lowestDistance = Double.MAX_VALUE;
            for (int i = 0; i < numPoints; i++) {
                Point point = points[i];
                HashSet<Point> group = groupToPoints.get(point.getGroup());
                for (int j = i + 1; j < numPoints; j++) {
                    Point other = points[j];
                    if (!group.contains(other)) {
                        double distance = point.getEuclideanDistance(other);
                        if (distance < lowestDistance) {
                            lowestDistance = distance;
                            closestPoints = new Pair<>(point, other);
                        }
                    }
                }
            }

            int a = closestPoints.x().getGroup();
            int b = closestPoints.y().getGroup();
            HashSet<Point> groupA = groupToPoints.get(a);
            HashSet<Point> groupB = groupToPoints.get(b);
            if (groupA.size() > groupB.size()) {
                groupA.addAll(groupB);
                groupToPoints.remove(b);
                groupB.forEach(point -> point.setGroup(a));
            } else {
                groupB.addAll(groupA);
                groupToPoints.remove(a);
                groupA.forEach(point -> point.setGroup(b));
            }
        } while (--numGroups != 1);

        return (long) closestPoints.x().getX() * closestPoints.y().getX();
    }

    private record Pair<T, U>(T x, U y) {

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Point {
        private final int x;
        private final int y;
        private final int z;
        private int group;

        public Point(String line) {
            String[] coords = line.split(",");
            this.x = Integer.parseInt(coords[0]);
            this.y = Integer.parseInt(coords[1]);
            this.z = Integer.parseInt(coords[2]);;
        }

        public double getEuclideanDistance(Point other) {
            return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2) + Math.pow(other.z - z, 2));
        }

        public int getX() {
            return x;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
