package day8;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Task1 {
    public static void main(String[] args) throws IOException {
        List<String> dayInput = readLinesOfFile("day8/data.input");

        String walkingDirections = dayInput.get(0);
        HashMap<String, List<String>> nodes = new HashMap<>();
        for (int n = 2; n < dayInput.size(); n++) {
            nodes.put(dayInput.get(n).split(" = ")[0],
                List.of(dayInput.get(n).split(" = ")[1].substring(1, 9).split(", ")));
        }

        long totalSteps = 0;
        int currentWalkingDirection = 0;
        String currentNode = "AAA";

        while(!currentNode.equals("ZZZ")) {

            char direction = walkingDirections.charAt(currentWalkingDirection);
            currentNode = direction == 'L'
                ? nodes.get(currentNode).get(0)
                : nodes.get(currentNode).get(1);

            currentWalkingDirection = (currentWalkingDirection + 1) % walkingDirections.length();
            totalSteps++;
        }

        print("Total steps: %s", totalSteps);
    }
}
