package day10;

import utils.Coord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

public class Task1 {

    public static void main(String[] args) throws IOException {

        List<String> pipeMatrixStrings = readLinesOfFile("day10/data.input");
        List<List<Character>> pipeMatrix = new ArrayList<>();

        for (String line : pipeMatrixStrings) {
            pipeMatrix.add(line.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.toList()));
        }

        Coord startPosition = null;
        for (int y = 0; y < pipeMatrix.size(); y++) {
            int startX = pipeMatrix.get(y).indexOf('S');
            if (startX >= 0) {
                startPosition = new Coord(startX, y);
                break;
            }
        }

        Coord currentPos = findNextPipe(startPosition, pipeMatrix);
        //pipeMatrix.get(startPosition.y).set(startPosition.x, 'X');

        int steps = 1;
        while (!currentPos.equals(startPosition)) {
            currentPos = findNextPipe(currentPos, pipeMatrix);

            //int steps = traversePipe(startPosition, firstStep, pipeMatrix);
            steps++;
        }

        print("Total steps: %s", steps / 2);
    }

    private static int total_steps = 0;
    private static Integer traversePipe(Coord startingPos, Coord currentPos, List<List<Character>> pipeMatrix) {

        print("Another step %s", total_steps++);

        if (currentPos.equals(startingPos)) {
            return 0;
        }

        Coord nextPos = findNextPipe(currentPos, pipeMatrix);
        return traversePipe(startingPos, nextPos, pipeMatrix) + 1;
    }

    private static List<Character> validLeftPipes = List.of('S', '-', 'J', '7');
    private static List<Character> validLeftConnections = new ArrayList<>(Arrays.asList('S', '-', 'F', 'L'));

    private static List<Character> validRightPipes = List.of('S', '-', 'F', 'L');
    private static List<Character> validRightConnections = new ArrayList<>(Arrays.asList('S', '-', 'J', '7'));

    private static List<Character> validUpPipes = List.of('S', '|', 'L', 'J');
    private static List<Character> validUpConnections = new ArrayList<>(Arrays.asList('S', '|', 'F', '7'));

    private static List<Character> validDownPipes = List.of('S', '|', 'F', '7');
    private static List<Character> validDownConnections = new ArrayList<>(Arrays.asList('S', '|', 'J', 'L'));

    private static Coord findNextPipe(Coord currentPos, List<List<Character>> pipeMatrix) {

        // Current pipe
        char currentPipe = pipeMatrix.get(currentPos.y).get(currentPos.x);
        if (currentPipe != 'S') {
            pipeMatrix.get(currentPos.y).set(currentPos.x, 'X');
        }

        if (currentPos.x > 0) {
            // Check left
            char leftPipe = pipeMatrix.get(currentPos.y).get(currentPos.x-1);
            if (validLeftPipes.contains(currentPipe) && validLeftConnections.contains(leftPipe)) {
                if (currentPipe == 'S') {
                    validRightConnections.remove(0);
                }
                return new Coord(currentPos.x - 1, currentPos.y);
            }
        }


        if (currentPos.x < (pipeMatrix.get(0).size() - 1)) {
            // Check right
            char rightPipe = pipeMatrix.get(currentPos.y).get(currentPos.x + 1);
            if (validRightPipes.contains(currentPipe) && validRightConnections.contains(rightPipe)) {
                if (currentPipe == 'S') {
                    validLeftConnections.remove(0);
                }
                return new Coord(currentPos.x + 1, currentPos.y);
            }
        }

        if (currentPos.y > 0) {
            // Check up
            char abovePipe = pipeMatrix.get(currentPos.y - 1).get(currentPos.x);
            if (validUpPipes.contains(currentPipe) && validUpConnections.contains(abovePipe)) {
                if (currentPipe == 'S') {
                    validDownConnections.remove(0);
                }
                return new Coord(currentPos.x, currentPos.y - 1);
            }
        }

        if (currentPos.y < (pipeMatrix.get(0).size() - 1)) {
            // Check down
            char belowPipe = pipeMatrix.get(currentPos.y + 1).get(currentPos.x);
            if (validDownPipes.contains(currentPipe) && validDownConnections.contains(belowPipe)) {
                if (currentPipe == 'S') {
                    validUpConnections.remove(0);
                }
                return new Coord(currentPos.x, currentPos.y + 1);
            }
        }
        return null;
    }
}
