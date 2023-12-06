package day6;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

import java.io.IOException;
import java.util.List;

public class Task1 {
    public static void main(String[] args) throws IOException {
        List<String> dayInput = readLinesOfFile("day6/data.input");
        List<String> times = List.of(dayInput.get(0).split(": +")[1].split(" +"));
        List<String> distances = List.of(dayInput.get(1).split(": +")[1].split(" +"));

        print(times.toString());

        int winsMultiplied = 1;
        for (int i = 0; i < times.size(); i++) {
            int time = Integer.parseInt(times.get(i));
            int distanceToBeat = Integer.parseInt(distances.get(i));

            int wins = 0;
            for (int t = 1; t < time; t++) {
                if(calculateDistance(t, time) > distanceToBeat) {
                    wins++;
                }
            }

            winsMultiplied *= wins;
        }

        print(Integer.toString(winsMultiplied));
    }

    private static int calculateDistance(int pushTime, int totalTime) {
        return (totalTime - pushTime) * pushTime;
    }
}
