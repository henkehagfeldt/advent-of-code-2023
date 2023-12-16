package day13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

public class Task2 {

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

        long totalMirrors = 0;

        int r = 0;
        for (List<String> reflection : reflectionGroups) {

            long res = searchReflection(reflection);

            print("Reflection %s %s", r++, res);

            totalMirrors += res;
        }

        print("Result %s", totalMirrors);
    }

    private static long searchReflection(List<String> reflection) {

        List<String> originalReflection = reflection;

        int sx = 0;
        int sy = 0;

        Result result = new Result(0, 0);

        while (true) {

            long hMirrors = findMirrors(reflection, reflection.size(), Task2::getHLine, result.h);
            long vMirrors = findMirrors(reflection, reflection.get(0).length(), Task2::getVLine, result.v);

            Result currentResult = new Result(hMirrors, vMirrors);

            // Store result
            if (currentResult.hasResult()) {
                if (result.hasResult() && !currentResult.equals(result)) {
                    return currentResult.getNotIn(result);
                } else {
                    result = currentResult;
                }
            }

            // Smudge next reflection
            reflection = smudgeReflection(originalReflection, sx, sy);

            // Move the smudge position forward
            sx++;
            if (sx == reflection.get(0).length()) {
                sx = 0;
                sy++;
                if (sy == reflection.size()) {
                    return 0;
                }
            }
        }
    }

    private static List<String> smudgeReflection(List<String> reflection, int sx, int sy) {
        List<String> newReflection = new ArrayList<>(reflection);

        String line = newReflection.get(sy);
        char c = newReflection.get(sy).charAt(sx);

        String newLine;
        if (c == '.') {
            newLine = new StringBuilder(line)
                    .replace(sx, sx + 1, "#").toString();
        } else {
            newLine = new StringBuilder(line)
                    .replace(sx, sx + 1, ".").toString();
        }
        newReflection.set(sy, newLine);
        return newReflection;
    }

    private static long findMirrors(List<String> reflection, int size, BiFunction<List, Integer, String> getLine, long previousValue) {

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

                if (offset > 0 && offset != previousValue) {
                    return offset;
                }

            }
        }

        return previousValue;
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

    private static class Result {
        public long h;
        public long v;

        Result(long h, long v) {
            this.h = h;
            this.v = v;
        }

        boolean hasResult() {
            return h > 0 || v > 0;
        }

        long getNotIn(Result other) {
            if (this.h != other.h) {
                return this.h*100;
            } else if (this.v != other.v) {
                return this.v;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            Result other = (Result) o;
            return this.h == other.h && this.v == other.v;
        }
    }
}
