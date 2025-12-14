import java.util.ArrayList;
import java.util.List;
import util.ReadFile;
import util.Combinations;

public class Day10 {
    public static void main(String[] args) {
        System.out.println(Day10.fewestButtonsLights("src/main/resources/inputs/Day10.txt")); //p1
    }

    public static int fewestButtonsLights(String filename) {
        ArrayList<String> lines = ReadFile.getAllLines(filename);

        int fewestButtons = 0;
        for (String line : lines) {
            int lightEnd = line.indexOf("]");
            int lights = 0;
            for (int i = 1; i < lightEnd; i++) {
                if (line.charAt(i) == '#') {
                    lights |= (1 << (i - 1));
                }
            }

            ArrayList<Integer> buttons = new ArrayList<>();
            for (String button : line.substring(lightEnd + 3, line.indexOf('{')).split("\\(")) {
                int newButton = 0;
                for (int i = 0; i < button.length(); i++) {
                    char c = button.charAt(i);
                    if (c == ')') {
                        break;
                    }
                    if (c != ',') {
                        newButton |= (1 << Character.getNumericValue(c));
                    }
                }
                buttons.add(newButton);
            }

            int smallestCombination = Integer.MAX_VALUE;
            List<List<Integer>> powerSet = Combinations.powerSet(buttons);
            for (List<Integer> set : powerSet) {
                int lightsTest = 0;
                for (int button : set) {
                    lightsTest ^= button;
                }
                if (lights == lightsTest) {
                    smallestCombination = Math.min(smallestCombination, set.size());
                }
            }
            fewestButtons += smallestCombination;
        }
        return fewestButtons;
    }
}
