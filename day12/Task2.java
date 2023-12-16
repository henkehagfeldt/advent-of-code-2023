package day12;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;
import static utils.Regex.getMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class Task2 {

    public static void main(String[] args) throws IOException {
        List<String> springs = readLinesOfFile("day12/data.input");

        long result = 0;
        int index = 0;
        int springAmount = springs.size();
        for (String spring : springs) {

            String originalSymbols = spring.split(" ")[0].replaceAll("\\.+", ".");
            //String extendedSymbols = originalSymbols + "?" + originalSymbols;
            String fiveExtendedSymbols = originalSymbols + "?" + originalSymbols + "?" + originalSymbols
                + "?" + originalSymbols + "?" + originalSymbols;

            String originalNumbers = spring.split(" ")[1];
            //String extendedNumbers = originalNumbers + "," + originalNumbers;
            String fiveExtendedNumbers = originalNumbers + "," + originalNumbers + "," + originalNumbers
                + "," + originalNumbers + "," + originalNumbers;

            //print(springNumbers);

            //List<Integer> originalNumberList = getNumberList(originalNumbers);
            //List<Integer> extendedNumberList = getNumberList(extendedNumbers);
            List<Integer> fiveExtendedNumberList = getNumberList(fiveExtendedNumbers);

            //print("%s -> %s", springSymbols, springNumbers);
            print("Processing %s / %s", index++, springAmount);
            //long springCount1 = fitSprings(originalSymbols, originalNumberList);
            //long springCount2 = fitSprings(extendedSymbols, extendedNumberList);
            long springCount3 = fitSprings(fiveExtendedSymbols, fiveExtendedNumberList);

            //long multiplier = (springCount2 / springCount1);
            //long value = (long) (Math.pow(springCount2, 4) / Math.pow(springCount1, 3));
            //long value2 = (springCount2 * springCount2 *springCount2 *springCount2) / ( springCount1 * springCount1* springCount1);


                //print("Springcount1: %s", springCount1);
                //print("Springcount2: %s", springCount2);
                print("Springcount3: %s", springCount3);
                //print("%s -> %s / M: %s -> %s", springCount1, springCount2, multiplier, value);
                //print("Value2: %s", value2);

            //if (multiplier > 300) {

            //}
            result += springCount3;
        }

        print("Total combinations: %s", result);
    }

    private static long fitSprings(String areas, List<Integer> springs) {

        if (springs.isEmpty() && !areas.contains("#")) {
            //print("Completed iteration");
            return 1;
        } else if(springs.isEmpty()) {
            //print("Finished but did not fill all the #");
            return 0;
        }

        Integer springSize = springs.get(0);
        String matcherString = String.format("(?=([?#]{%s}))", springSize);
        Matcher springMatcher = getMatcher(matcherString, areas);

        //print("Checking Area %s to fit %s", areas, springSize);

        long total = 0;
        while (springMatcher.find()) {
            int start = springMatcher.start();
            int end = start + springSize;

            //print("Match start %s, end %s", start, end);
            String match = springMatcher.group(1);

            if (match.length() >= springSize) {

                boolean passedSpring = areas.substring(0, start).contains("#");

                if (passedSpring) {
                    continue;
                }

                if (end == areas.length()) {
                    //print("Word is finished.");
                    total += fitSprings("", springs.subList(1, springs.size()));
                } else if(areas.charAt(end) != '#') {
                    //print("Keep going");
                    int startOfNextArea = Math.min(areas.length(), end + 1);
                    String nextArea = areas.substring(startOfNextArea);
                    total += fitSprings(nextArea, springs.subList(1, springs.size()));
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
