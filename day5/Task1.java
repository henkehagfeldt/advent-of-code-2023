package day5;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Task1 {
    public static void main(String[] args) throws IOException {
        List<String> dayInput = readLinesOfFile("day5/data.input");
        List<String> seeds = List.of(dayInput.get(0).split("seeds: ")[1].split(" +"));
        List<Mapping> mappings = new ArrayList<>();

        for (int l = 2; l < dayInput.size(); l++) {
            print(dayInput.get(l));
            List<String> nextMap = findNextMap(dayInput, l);
            Mapping mapping = new Mapping(nextMap);
            mappings.add(mapping);
            l += nextMap.size() + 1;
        }

        List<Long> locations = new ArrayList<>();
        for (String seed : seeds) {
            Long longSeed = Long.valueOf(seed);
            Long location = propagateSeed(longSeed, mappings);
            locations.add(location);
        }

        print("Result %s", locations.stream().min(Long::compareTo).get());
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
                destStart.add(Long.parseLong(values[0]));
                sourceStart.add(Long.parseLong(values[1]));
                destRange.add(Long.parseLong(values[2]));
            }
            print(destStart.toString());
            print(sourceStart.toString());
            print(destRange.toString());
        }

        public Long getMappedValue(Long source) {
            for (int i = 0; i < sourceStart.size(); i++) {
                Long start = sourceStart.get(i);
                Long range = destRange.get(i);
                if (source >= start && source <= (start+range)) {
                    return destStart.get(i) + (source - start);
                }
            }
            return source;
        }
    }
}
