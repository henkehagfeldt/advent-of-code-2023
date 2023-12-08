package day8;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Task2 {
    public static void main(String[] args) throws IOException {
        List<String> dayInput = readLinesOfFile("day8/data.input");

        String walkingDirections = dayInput.get(0);
        HashMap<String, List<String>> nodes = new HashMap<>();
        List<String> startNodes = new ArrayList<>();
        for (int n = 2; n < dayInput.size(); n++) {
            String node = dayInput.get(n).split(" = ")[0];
            if (node.charAt(2) == 'A') {
                startNodes.add(node);
            }

            nodes.put(node,
                List.of(dayInput.get(n).split(" = ")[1].substring(1, 9).split(", ")));
        }

        int currentSteps = 0;
        int currentWalkingDirection = 0;
        List<Integer> stepsToComplete = new ArrayList<>();

        while(stepsToComplete.size() != startNodes.size()) {
            print("%s / %s", stepsToComplete.size(), startNodes.size());

            char direction = walkingDirections.charAt(currentWalkingDirection);
            currentWalkingDirection = (currentWalkingDirection + 1) % walkingDirections.length();

            for (int s = 0; s < startNodes.size(); s++) {

                String currentNode = startNodes.get(s);
                if (currentNode.charAt(2) == 'Z') {
                    stepsToComplete.add(currentSteps);
                }

                currentNode = direction == 'L'
                    ? nodes.get(currentNode).get(0)
                    : nodes.get(currentNode).get(1);

                startNodes.set(s, currentNode);
            }
            currentSteps++;
        }

        stepsToComplete.stream().sorted();

        long smallestMultiplierSteps = stepsToComplete.get(0);
        for (int s = 1; s < stepsToComplete.size(); s++) {
            int steps = stepsToComplete.get(s);
            smallestMultiplierSteps = lcm(smallestMultiplierSteps, steps);
        }

        print("Total steps: %s", smallestMultiplierSteps);
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
