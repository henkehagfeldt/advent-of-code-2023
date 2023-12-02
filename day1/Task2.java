package day1;

import static utils.Parser.readLinesOfFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task2 {
    public static void main(String[] args) throws IOException {
        List<String> calibrationValues = readLinesOfFile("day1/data.input");

        Integer result = calibrationValues.stream()
            .map(Task2::extractFirstAndLast)
            .mapToInt(Integer::parseInt)
            .sum();

        System.out.println(String.format("Result %s", result));
    }

    private static String extractFirstAndLast(String value) {

        System.out.println(value);

        String digitPatternString = "(?=(\\d))|"
            + "(?=(one))|"
            + "(?=(two))|"
            + "(?=(three))|"
            + "(?=(four))|"
            + "(?=(five))|"
            + "(?=(six))|"
            + "(?=(seven))|"
            + "(?=(eight))|"
            + "(?=(nine))";

        Pattern findDigitPattern = Pattern.compile(digitPatternString);
        Matcher digitMatcher = findDigitPattern.matcher(value);

        String firstDigit = null;
        String lastDigit = null;

        while(digitMatcher.find()) {
            for (int g = 0; g <= digitMatcher.groupCount(); g++) {
                String match = digitMatcher.group(g);
                if (match != null && !match.isEmpty()) {
                    firstDigit = firstDigit == null ? match : firstDigit;
                    lastDigit = match;
                }
            }
        }

        List<String> wordDigits = List.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        firstDigit = wordDigits.contains(firstDigit) ? String.valueOf(wordDigits.indexOf(firstDigit)) : firstDigit;
        lastDigit = wordDigits.contains(lastDigit) ? String.valueOf(wordDigits.indexOf(lastDigit)) : lastDigit;

        String result = firstDigit + lastDigit;
        System.out.println(result);

        return result;
    }

}
