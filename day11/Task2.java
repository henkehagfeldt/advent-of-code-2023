package day11;

import utils.Coord;

import java.io.IOException;
import java.util.*;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

public class Task2 {
    private static final long EXPAND_SIZE = 1000000L;
    public static void main(String[] args) throws IOException {

        List<String> universe = readLinesOfFile("day11/data.input");
        List<Coord> galaxyList = new ArrayList<>();

        // Expand universe
        List<Integer> expandedRows = new ArrayList<>();
        for (int y = 0; y < universe.size(); y++) {
            if (universe.get(y).matches("\\.+")) {
                expandedRows.add(y);
            }
        }

        List<Integer> expandedCols = new ArrayList<>();
        for (int x = 0; x < universe.get(0).length(); x++) {
            boolean shouldExpand = true;
            for (int y = 0; y < universe.size(); y++) {
                if (universe.get(y).charAt(x) != '.') {
                    shouldExpand = false;
                }
            }
            if (shouldExpand) {
                expandedCols.add(x);
            }
        }

        // Find galaxies
        for (int x = 0; x < universe.get(0).length(); x++) {
            for (int y = 0; y < universe.size(); y++) {
                if (universe.get(y).charAt(x) == '#') {
                    galaxyList.add(new Coord(x, y));
                }
            }
        }

        // Calculate distances
        long totalDistance = 0;
        for (int g1 = 0; g1 < galaxyList.size(); g1++) {
            for(int g2 = g1 + 1; g2 < galaxyList.size(); g2++) {
                long distance = calculateDistance(galaxyList.get(g1), galaxyList.get(g2), expandedRows, expandedCols);
                print("Distance between %s -> %s is %s", galaxyList.get(g1), galaxyList.get(g2), distance);
                totalDistance += distance;
            }
        }

        print("Total distances: %s", totalDistance);
    }

    private static long calculateDistance(Coord g1, Coord g2, List<Integer> expandedRows, List<Integer> expandedCols) {
        long unExpandedDistance = g1.distanceTo(g2);

        long extraRows = 0;
        for (int expandedRow : expandedRows) {
            if (expandedRow > Math.min(g1.y, g2.y) && expandedRow < Math.max(g1.y, g2.y)) {
                extraRows++;
            }
        }

        long extraCols = 0;
        for (int expandedCol : expandedCols) {
            if (expandedCol > Math.min(g1.x, g2.x) && expandedCol < Math.max(g1.x, g2.x)) {
                extraCols++;
            }
        }

        long extrasExpanded = (extraRows + extraCols) * (EXPAND_SIZE - 1L);
        return unExpandedDistance + extrasExpanded;
    }
}
