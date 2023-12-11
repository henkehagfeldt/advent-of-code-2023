package day11;

import utils.Coord;

import java.io.IOException;
import java.util.*;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

public class Task1 {

    public static void main(String[] args) throws IOException {

        List<String> universe = readLinesOfFile("day11/data.input");
        List<Coord> galaxyList = new ArrayList<>();

        // Expand universe
        List<String> expandedUniverse = expandUniverse(universe);

        // Find galaxies
        for (int x = 0; x < expandedUniverse.get(0).length(); x++) {
            for (int y = 0; y < expandedUniverse.size(); y++) {
                if (expandedUniverse.get(y).charAt(x) == '#') {
                    galaxyList.add(new Coord(x, y));
                }
            }
        }

        // Calculate distances
        long totalDistance = 0;
        for (int g1 = 0; g1 < galaxyList.size(); g1++) {
            for(int g2 = g1 + 1; g2 < galaxyList.size(); g2++) {
                long distance = galaxyList.get(g1).distanceTo(galaxyList.get(g2));
                print("Distance between %s -> %s is %s", galaxyList.get(g1), galaxyList.get(g2), distance);
                totalDistance += distance;
            }
        }

        print("Total distances: %s", totalDistance);
    }

    private static List<String> expandUniverse(List<String> universe) {
        List<String> expandedUniverse = new ArrayList<>();
        List<Boolean> columnShouldExpand = new ArrayList<>();

        for (int x = 0; x < universe.size(); x++) {
            boolean shouldExpand = true;
            for (int y = 0; y < universe.get(0).length(); y++) {
                if (universe.get(y).charAt(x) != '.') {
                    shouldExpand = false;
                }
            }
            columnShouldExpand.add(shouldExpand);
        }


        for (int y = 0; y < universe.size(); y++) {
            String row = universe.get(y);
            int expandedColumns = 0;
            for (int c = 0; c < columnShouldExpand.size(); c++) {
                if (columnShouldExpand.get(c)) {
                    row = new StringBuilder(row).insert(c + expandedColumns, '.').toString();
                    expandedColumns++;
                }
            }

            expandedUniverse.add(row);
            if (universe.get(y).matches("\\.+")) {
                expandedUniverse.add(row);
            }
        }

        return expandedUniverse;
    }
}
