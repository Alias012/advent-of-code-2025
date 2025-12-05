package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
    public static ArrayList<String> getAllLines(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        }
        return lines;
    }

    public static ArrayList<String> getLinesUntilBreak(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while (!(line = reader.readLine()).isEmpty()) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file...");
        }
        return lines;
    }

//    public static Stream<String> streamAllLines(String filename) throws IOException {
//        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new FileIterator(filename), Spliterator.SIZED | Spliterator.NONNULL | Spliterator.IMMUTABLE), false);
//    }
//
//    private static class FileIterator implements Iterator<String>{
//        private String line;
//        private final BufferedReader reader;
//
//        private FileIterator(String filename) throws FileNotFoundException {
//            reader = new BufferedReader(new FileReader(filename));
//        }
//
//        @Override
//        public boolean hasNext() {
//            try {
//                return (line = reader.readLine()) != null;
//            } catch (IOException e) {
//                System.err.println("Error reading input file...");
//            }
//            return false;
//        }
//
//        @Override
//        public String next() {
//            return line;
//        }
//    }
}
