package day5;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Task2 {
    public static void main(String[] args) throws IOException {
        List<String> dayInput = readLinesOfFile("day5/data.input");
        List<Mapping> mappings = new ArrayList<>();

        for (int l = 2; l < dayInput.size(); l++) {
            List<String> nextMap = findNextMap(dayInput, l);
            mappings.add(new Mapping(nextMap));
            l += nextMap.size() + 1;
        }

        List<String> seedRanges = List.of(dayInput.get(0).split("seeds: ")[1].split(" +"));

        long minLocation = Long.MAX_VALUE;
        for (int i = 0; i < seedRanges.size(); i += 2) {
            print("Processing seed range %s out of %s", (i / 2) + 1, seedRanges.size()/2);
            long startSeed = Long.parseUnsignedLong(seedRanges.get(i));
            long seedRange = Long.parseUnsignedLong(seedRanges.get(i + 1));

            for (long s = 0; s < seedRange; s += 1L) {
                Long seedToCheck = startSeed + s;
                long location = propagateSeed(seedToCheck, mappings);

                if (location < minLocation) {
                    print("New minimum found: %s, seed %s", location, seedToCheck);
                    minLocation = location;
                }

                if (s % 50000000 == 0) {
                    print("Progress %s / 100", (s * 100 / seedRange));
                }
            }
        }

        print("Result %s", minLocation);
        print(String.valueOf(minLocation));
    }

    private static List<String> findNextMap(List<String> map, int index) {
        List<String> nextMap = new ArrayList<>();

        for (int i = index + 1; i < map.size() && !map.get(i).isEmpty(); i++) {
            nextMap.add(map.get(i));
        }
        return nextMap;
    }

    private static Long propagateSeed(Long seed, List<Mapping> mappings) {
        Long currentValue = seed;

        for(Mapping map : mappings) {
            currentValue = map.getMappedValue(currentValue);
        }

        return currentValue;
    }

    private static class Mapping {
        List<Long> sourceStart = new ArrayList<>();
        List<Long> destStart = new ArrayList<>();
        List<Long> destRange = new ArrayList<>();

        Mapping(List<String> map) {
            for (String mapLine : map) {
                String[] values = mapLine.split(" +");
                destStart.add(Long.parseUnsignedLong(values[0]));
                sourceStart.add(Long.parseUnsignedLong(values[1]));
                destRange.add(Long.parseUnsignedLong(values[2]));
            }
        }

        public Long getMappedValue(Long source) {
            for (int i = 0; i < sourceStart.size(); i++) {
                Long start = sourceStart.get(i);
                Long range = destRange.get(i);

                if (source < start) {
                    continue;
                }

                long relativeSource = source - start;
                if (relativeSource < range) {
                    long destinationStart = destStart.get(i);
                    return destinationStart + relativeSource;
                }
            }

            return source;
        }
    }
}
