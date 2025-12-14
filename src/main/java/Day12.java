import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day12 {
    public static void main(String[] args) {
        System.out.println(Day12.countFittingRegions("src/main/resources/inputs/Day12.txt")); //p1
    }

    public static long countFittingRegions(String filename) {
        ArrayList<Integer> shapeAreas = new ArrayList<>();
        int index = -1;
        int result = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while (true) {
                line = reader.readLine();
                if (line.matches("\\d:")) {
                    shapeAreas.add(0);
                    index++;
                } else if (line.matches("[#.]+")) {
                    int count = 0;
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '#') {
                            count++;
                        }
                    }
                    shapeAreas.set(index, shapeAreas.get(index) + count);
                } else if (line.matches("\\d+x.*")) {
                    break;
                }
            }

            do {
                String[] split = line.split(":");
                String[] dims = split[0].split("x");
                int area = Integer.parseInt(dims[0]) * Integer.parseInt(dims[1]);
                String[] shapes = split[1].substring(1).split(" ");
                for (int i = 0; i < shapes.length; i++) {
                    area -= shapeAreas.get(i) * Integer.parseInt(shapes[i]);
                }
                if (area >= 0) {
                    result += 1;
                }
            } while ((line = reader.readLine()) != null);
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        }
        return result;
    }
}
