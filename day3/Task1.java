package day3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;
import static utils.Regex.getMatcher;

public class Task1 {
    public static void main(String[] args) throws IOException {
        List<String> gameInput = readLinesOfFile("day3/data.input");

        List<Integer> valuesFound = new ArrayList<>();
        for (int l = 0; l < gameInput.size(); l++) {
            String line = gameInput.get(l);

            Matcher matcher = getMatcher("\\d+", line);

            while (matcher.find()) {
                if (symbolAround(matcher,
                        gameInput.subList(
                                Math.max(l-1, 0),
                                Math.min(l+2, gameInput.size())))) {
                    String stringValue = matcher.group();
                    print("Added value: %s\n", stringValue);
                    valuesFound.add(Integer.parseInt(stringValue));
                }
            }
        }

        Integer result = valuesFound.stream()
                .mapToInt(Integer::intValue)
                .sum();

        print("Result %s", result);
    }

    private static boolean symbolAround(Matcher matchedNumber, List<String> nearbyLines) {
        int startIndex = Math.max(matchedNumber.start() - 1, 0);
        int endIndex = Math.min(matchedNumber.end() + 1, nearbyLines.get(0).length());

        for (String l : nearbyLines) {
            String searchArea = l.substring(startIndex, endIndex);
            Matcher symbolMatcher = getMatcher("[^\\d.]", searchArea);
            if (symbolMatcher.find()) {
                return true;
            }
        }
        return false;
    }
}
