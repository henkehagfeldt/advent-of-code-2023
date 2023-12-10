package day9;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

public class Task1 {
    public static void main(String[] args) throws IOException {
        List<String> histories = readLinesOfFile("day9/data.input");

        long totalSum = 0;
        for(String history : histories) {
            String[] historyArray = history.split(" ");
            List<Integer> historyList = new ArrayList<>();
            for(String s : historyArray) {
                historyList.add(Integer.valueOf(s));
            }

            int value = digDeeper(historyList);
            print("Value is: %s", value);
            totalSum += value;
        }

        print("Total steps: %s", totalSum);
    }

    private static Integer digDeeper(List<Integer> currentLevel) {

        if (currentLevel.stream().allMatch(x -> x == 0)) {
            return 0;
        }

        List<Integer> differences = new ArrayList<>();
        for (int i = 0; i < currentLevel.size() - 1; i++) {
            int value = currentLevel.get(i);
            int nextValue = currentLevel.get(i + 1);
            int difference = nextValue - value;
            differences.add(difference);
        }

        print("Differences %s", differences);

        return  currentLevel.get(currentLevel.size() - 1) + digDeeper(differences);

    }
}
