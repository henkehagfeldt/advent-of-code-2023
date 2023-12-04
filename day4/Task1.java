package day4;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

import java.io.IOException;
import java.util.List;

public class Task1 {
    public static void main(String[] args) throws IOException {
        List<String> cardInput = readLinesOfFile("day4/data.input");

        long totalWins = 0;

        for (String card : cardInput) {
            print(card);
            List<String> cardParts = List.of(card.split(" +\\| +"));
            List<String> winningNumbers = List.of(cardParts.get(0).split(":")[1].split(" +"));
            List<String> actualNumbers = List.of(cardParts.get(1).split(" +"));

            long wins = actualNumbers.stream()
                .filter(winningNumbers::contains)
                .count();

            print(Long.toString(wins));
            double adds = wins > 0 ? Math.pow(2, Math.max(wins - 1, 0)) : 0;
            totalWins += adds;
        }

        print("Result %s", totalWins);
    }
}
