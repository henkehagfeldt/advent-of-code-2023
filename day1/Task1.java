package day1;

import static utils.Parser.readLinesOfFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task1 {
    public static void main(String[] args) throws IOException {
        List<String> calibrationValues = readLinesOfFile("day1/data.input");

        Integer result = calibrationValues.stream()
            .map(Task1::extractFirstAndLast)
            .mapToInt(Integer::parseInt)
            .sum();

        System.out.println(String.format("Result %s", result));
    }

    private static String extractFirstAndLast(String value) {
        System.out.println(value);

        String digitPatternString = "\\d";
        Pattern findDigitPattern = Pattern.compile(digitPatternString);
        Matcher digitMatcher = findDigitPattern.matcher(value);

        String firstDigit = null;
        String lastDigit = null;

        while(digitMatcher.find()) {
            firstDigit = firstDigit == null ? digitMatcher.group() : firstDigit;
            lastDigit = digitMatcher.group();
        }

        System.out.println(firstDigit + lastDigit);

        return String.format("%s%s",
            firstDigit,
            lastDigit);
    }
}
