package day12;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;
import static utils.Regex.getMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class Task2 {

    public static void main(String[] args) throws IOException {
        List<String> springs = readLinesOfFile("day12/data.input");

        long totalArrangements = 0;
        for (String spring : springs) {

            String originalSymbols = spring.split(" ")[0].replaceAll("\\.+", ".");
            String fiveExtendedSymbols = originalSymbols + "?" + originalSymbols + "?" + originalSymbols
                + "?" + originalSymbols + "?" + originalSymbols;

            String originalNumbers = spring.split(" ")[1];
            String fiveExtendedNumbers = originalNumbers + "," + originalNumbers + "," + originalNumbers
                + "," + originalNumbers + "," + originalNumbers;

            List<Integer> fiveExtendedNumberList = getNumberList(fiveExtendedNumbers);
            totalArrangements += findArrangements(fiveExtendedSymbols, fiveExtendedNumberList, new HashMap<>());
        }

        print("Total combinations: %s", totalArrangements);
    }

    private static long findArrangements(String areas, List<Integer> springs, HashMap<String, Long> cache) {
        long result = 0;

        if (cache.containsKey(areas + springs)) {
            return cache.get(areas + springs);
        } else if (springs.isEmpty() && !areas.contains("#")) {
            result = 1;
        } else if(!springs.isEmpty()) {
            Integer springSize = springs.get(0);
            String matcherString = String.format("(?=([?#]{%s}))", springSize);
            Matcher springMatcher = getMatcher(matcherString, areas);
            while (springMatcher.find()) {
                int start = springMatcher.start();
                int end = start + springSize;

                String match = springMatcher.group(1);

                if (match.length() >= springSize) {
                    boolean passedSpring = areas.substring(0, start).contains("#");

                    if (passedSpring) {
                        result += 0;
                        break;
                    } else if (end == areas.length()) {
                        result += findArrangements("", springs.subList(1, springs.size()), cache);
                    } else if(areas.charAt(end) != '#') {
                        int startOfNextArea = Math.min(areas.length(), end + 1);
                        String nextArea = areas.substring(startOfNextArea);
                        result += findArrangements(nextArea, springs.subList(1, springs.size()), cache);
                    }
                }
            }
        }
        
        cache.put(areas + springs, result);
        return result;
    }

    private static List<Integer> getNumberList(String springNumbers) {
        return Arrays.stream(springNumbers.split(","))
            .map(Integer::valueOf)
            .collect(Collectors.toList());
    }
}
