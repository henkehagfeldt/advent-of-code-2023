package day13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

public class Task1 {

    public static void main(String[] args) throws IOException {

        List<String> allReflections = readLinesOfFile("day13/data.input");
        List<List<String>> reflectionGroups = new ArrayList<>();

        // Group reflections
        List<String> newReflection = new ArrayList<>();
        for (String line : allReflections) {
            if (line.isBlank()) {
                reflectionGroups.add(newReflection);
                newReflection = new ArrayList<>();
            } else {
                newReflection.add(line);
            }
        }
        reflectionGroups.add(newReflection);

        long totalHMirrors = 0;
        long totalVMirrors = 0;

        for (List<String> reflection : reflectionGroups) {
            long hMirrors = findMirrors(reflection, reflection.size(), Task1::getHLine);
            long vMirrors = findMirrors(reflection, reflection.get(0).length(), Task1::getVLine);

            print("H: %s", hMirrors);
            print("V: %s", vMirrors);

            if (hMirrors > vMirrors) {
                totalHMirrors += 100 * hMirrors;
            } else {
                totalVMirrors += vMirrors;
            }
        }

        print("Verticals: %s", totalVMirrors);
        print("Horizontal: %s", totalHMirrors);
        print("Result %s", totalHMirrors + totalVMirrors);
    }

    private static long findMirrors(List<String> reflection, int size, BiFunction<List, Integer, String> getLine) {
        long mirroredLines = 0;

        for (int lineIndex = 0; lineIndex < size - 1; lineIndex++) {
            String line = getLine.apply(reflection, lineIndex);
            String nextLine = getLine.apply(reflection, lineIndex + 1);

            if (line.equals(nextLine)) {
                int offset = 1;

                while ((lineIndex - offset) >= 0) {
                    boolean lineOutOfBounds = (lineIndex + 1 + offset) >= size;
                    if (lineOutOfBounds) {
                        offset++;
                        continue;
                    }

                    String checkLine = getLine.apply(reflection, lineIndex - offset);
                    String mirrorLine = getLine.apply(reflection, lineIndex + 1 + offset);

                    if (checkLine.equals(mirrorLine)) {
                        offset++;
                    } else {
                        offset = 0;
                        break;
                    }
                }
                mirroredLines = offset > mirroredLines ? offset : mirroredLines;
            }
        }

        return mirroredLines;
    }

    private static String getVLine(List<String> reflection, int xIndex) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < reflection.size(); y++) {
            sb.append(reflection.get(y).charAt(xIndex));
        }
        return sb.toString();
    }

    private static String getHLine(List<String> reflection, int yIndex) {
        return reflection.get(yIndex);
    }
}
