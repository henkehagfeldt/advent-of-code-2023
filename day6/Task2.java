package day6;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

import java.io.IOException;
import java.util.List;

public class Task2 {
    public static void main(String[] args) throws IOException {
        List<String> dayInput = readLinesOfFile("day6/data.input");

        List<String> times = List.of(dayInput.get(0).split(": +")[1].split(" +"));
        String totalTime = times.stream().reduce((a, b) -> String.format("%s%s", a, b)).get();
        List<String> distances = List.of(dayInput.get(1).split(": +")[1].split(" +"));
        String totalDistance = distances.stream().reduce((a, b) -> String.format("%s%s", a, b)).get();

        print(times.toString());
        long wins = 0;

        long time = Long.parseLong(totalTime);
        long distanceToBeat = Long.parseLong(totalDistance);

        for (int t = 1; t < time; t++) {
            if(calculateDistance(t, time) > distanceToBeat) {
                wins++;
            }
        }

        print(Long.toString(wins));
    }

    private static long calculateDistance(long pushTime, long totalTime) {
        return (totalTime - pushTime) * pushTime;
    }
}
