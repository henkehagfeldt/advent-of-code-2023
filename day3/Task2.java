package day3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static utils.Calculator.sum;
import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;
import static utils.Regex.getMatcher;

public class Task2 {
    public static void main(String[] args) throws IOException {
        List<String> gameInput = readLinesOfFile("day3/data.input");
        List<Integer> valuesFound = new ArrayList<>();

        for (int l = 0; l < gameInput.size(); l++) {
            String line = gameInput.get(l);
            Matcher gearMatcher = getMatcher("\\*", line);

            while (gearMatcher.find()) {
                int aboveLineIndex = Math.max(l-1, 0);
                int belowLineIndex = Math.min(l+2, gameInput.size());
                List<String> searchArea = gameInput.subList(aboveLineIndex, belowLineIndex);

                int gearValue = calculateGearValue(gearMatcher, searchArea);

                if (gearValue != 0) {
                    print("Found gear: %s\n", gearValue);
                    valuesFound.add(gearValue);
                }
            }
        }

        print("Result %s", sum(valuesFound));
    }

    private static int calculateGearValue(Matcher matchedGear, List<String> nearbyLines) {
        int startAreaIndex = Math.max(matchedGear.start() - 1, 0);
        int endAreaIndex = Math.min(matchedGear.end() + 1, nearbyLines.get(0).length());
        int firstNumberMatch = 0;

        for (String line : nearbyLines) {
            Matcher numberMatcher = getMatcher("\\d+", line);

            while (numberMatcher.find()) {
                if (isNearbyNumber(numberMatcher, startAreaIndex, endAreaIndex)) {
                    String matchedNumber = numberMatcher.group();
                    print("Found match %s", matchedNumber);

                    if (firstNumberMatch == 0) {
                        firstNumberMatch = Integer.parseInt(matchedNumber);
                    } else {
                        int secondNumberMatch = Integer.parseInt(matchedNumber);
                        return firstNumberMatch * secondNumberMatch;
                    }
                }
            }
        }
        return 0;
    }

    private static boolean isNearbyNumber(Matcher matcher, int startIndex, int endIndex) {
        boolean startCondition = matcher.start() < endIndex;
        boolean endCondition = matcher.end() > startIndex;
        return startCondition && endCondition;
    }
}
