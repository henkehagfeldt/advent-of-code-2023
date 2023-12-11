package day10;

import utils.Coord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

public class Task2 {

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

        // F -> J (New value)
        // L -> 7 (New value)
        // F -> 7 (Same as before F)
        // L -> J (Same as before L)

        Coord currentPos = findNextPipe(startPosition, pipeMatrix);

        while (!currentPos.equals(startPosition)) {
            currentPos = findNextPipe(currentPos, pipeMatrix);
        }

        boolean inRing;
        char onBend;
        int innerCount = 0;

        for (int y = 0; y < pipeMatrix.size(); y++) {
            inRing = false;
            onBend = NO_VALUE;
            for (int x = 0; x < pipeMatrix.get(0).size(); x++) {

                Character currentValue = pipeMatrix.get(y).get(x);

                if (List.of(BOTTOM_LEFT, TOP_LEFT).contains(currentValue)) {
                    onBend = currentValue;
                } else if ((currentValue == TOP_RIGHT && onBend == BOTTOM_LEFT)
                        || (currentValue == BOTTOM_RIGHT && onBend == TOP_LEFT)) {
                    onBend = NO_VALUE;
                    inRing = !inRing;
                } else if (currentValue == STANDING_WALL) {
                    inRing = !inRing;
                } else if (currentValue != 'X' && inRing) {
                    pipeMatrix.get(y).set(x, 'I');
                    innerCount++;
                }

//                switch (pipeMatrix.get(y).get(x)) {
//                    case 'W':
//                    //case 'S':
//                        inRing = !inRing;
//                        break;
//                    case '8':
//                        onBend = '8';
//                        break;
//                    case BOTTOM_LEFT:
//                        onBend = BOTTOM_LEFT;
//                        break;
//                    case '9':
//                        if (onBend == BOTTOM_LEFT) {
//                            onBend = '5';
//                            inRing = !inRing;
//                        }
//                        break;
//                    case '3':
//                        if (onBend == '8') {
//                            onBend = '5';
//                            inRing = !inRing;
//                        }
//                    case 'X':
//                        break;
//                    default:
//                        if (inRing) {
//                            pipeMatrix.get(y).set(x, 'I');
//                            innerCount++;
//                        }
//                        break;
//                }
            }
        }

        print("Inside Loop: %s", innerCount);
    }

    private static List<Character> validLeftPipes = List.of('S', '-', 'J', '7');
    private static List<Character> validLeftConnections = new ArrayList<>(Arrays.asList('S', '-', 'F', 'L'));

    private static List<Character> validRightPipes = List.of('S', '-', 'F', 'L');
    private static List<Character> validRightConnections = new ArrayList<>(Arrays.asList('S', '-', 'J', '7'));

    private static List<Character> validUpPipes = List.of('S', '|', 'L', 'J');
    private static List<Character> validUpConnections = new ArrayList<>(Arrays.asList('S', '|', 'F', '7'));

    private static List<Character> validDownPipes = List.of('S', '|', 'F', '7');
    private static List<Character> validDownConnections = new ArrayList<>(Arrays.asList('S', '|', 'J', 'L'));

    private static Character STANDING_WALL = 'W';
    private static Character BOTTOM_LEFT = 'B';
    private static Character BOTTOM_RIGHT = 'R';
    private static Character TOP_LEFT = 'T';
    private static Character TOP_RIGHT = 'H';
    private static Character TRAVERSED_PIPE = 'X';
    private static Character NO_VALUE = 'N';


    private static Coord findNextPipe(Coord currentPos, List<List<Character>> pipeMatrix) {

        // Current pipe
        char currentPipe = pipeMatrix.get(currentPos.y).get(currentPos.x);

        switch(currentPipe) {
            case '|':
                pipeMatrix.get(currentPos.y).set(currentPos.x, STANDING_WALL);
                break;
            case 'L':
                pipeMatrix.get(currentPos.y).set(currentPos.x, BOTTOM_LEFT);
                break;
            case '7':
                pipeMatrix.get(currentPos.y).set(currentPos.x, TOP_RIGHT);
                break;
            case 'F':
                pipeMatrix.get(currentPos.y).set(currentPos.x, TOP_LEFT);
                break;
            case 'J':
                pipeMatrix.get(currentPos.y).set(currentPos.x, BOTTOM_RIGHT);
                break;
            case 'S':
                break;
            default:
                pipeMatrix.get(currentPos.y).set(currentPos.x, TRAVERSED_PIPE);

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
