package day12;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;
import static utils.Regex.getMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class Task1 {

    public static void main(String[] args) throws IOException {
        List<String> springs = readLinesOfFile("day12/data.input");

        long result = 0;
        for (String spring : springs) {
            String springSymbols = spring.split(" ")[0];
            String springNumbers = spring.split(" ")[1];

            List<Integer> springNumberList = getNumberList(springNumbers);

            long springCount = fitSprings(springSymbols, springNumberList);
            print("Springcount: %s", springCount);
            result += springCount;
        }

        print("Total combinations: %s", result);
    }

    private static long fitSprings(String areas, List<Integer> springs) {

        if (springs.isEmpty() && !areas.contains("#")) {
            print("Completed iteration");
            return 1;
        } else if(springs.isEmpty()) {
            print("Finished but did not fill all the #");
            return 0;
        }

        Integer springSize = springs.get(0);
        String matcherString = String.format("(?=([?#]{%s}))", springSize);
        Matcher springMatcher = getMatcher(matcherString, areas);

        print("Checking Area %s to fit %s", areas, springSize);

        long total = 0;
        while (springMatcher.find()) {
            int start = springMatcher.start();
            int end = start + springSize;

            print("Match start %s, end %s", start, end);
            String match = springMatcher.group(1);

            if (match.length() >= springSize) {

                boolean passedSpring = areas.substring(0, start).contains("#");

                if (passedSpring) {
                    continue;
                }

                if (end == areas.length()) {
                    print("Word is finished.");
                    total += fitSprings("", springs.subList(1, springs.size()));
                } else if(areas.charAt(end) != '#') {
                    print("Keep going");
                    int startOfNextArea = Math.min(areas.length(), end + 1);
                    String nextArea = areas.substring(startOfNextArea);
                    total += fitSprings(nextArea, springs.subList(1, springs.size()));
                } else {
                    print("Missed a # - invalid");
                }
            }
        }
        return total;
    }

    private static List<Integer> getNumberList(String springNumbers) {
        return Arrays.stream(springNumbers.split(","))
            .map(Integer::valueOf)
            .collect(Collectors.toList());
    }
}
