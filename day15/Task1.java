package day15;

import java.io.IOException;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

public class Task1 {

    public static void main(String[] args) throws IOException {

        String letters = readLinesOfFile("day15/data.input").get(0);

        String[] groups = letters.split(",");

        long result = 0;
        for (int g = 0; g < groups.length; g++) {
            String stringGroup = groups[g];
            long currentValue = 0;
            for (char c : stringGroup.toCharArray()) {
                currentValue += (int) c;
                currentValue *= 17;
                currentValue = currentValue % 256;
            }
            result += currentValue;
        }

        print("Result %s", result);
    }
}
