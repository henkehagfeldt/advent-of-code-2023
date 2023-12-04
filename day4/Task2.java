package day4;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task2 {
    public static void main(String[] args) throws IOException {
        List<String> cardInput = readLinesOfFile("day4/data.input");
        Map<Integer, Integer> multipliers = new HashMap<>();

        long cardCount = 0;

        for (String card : cardInput) {
            List<String> identifierAndNumbers = List.of(card.split(":"));

            String identifierString = identifierAndNumbers.get(0);
            String cardNumberString = identifierString.substring(5).trim();
            int cardNumber = Integer.parseInt(cardNumberString);

            List<String> winningAndActualNumbers = List.of(identifierAndNumbers.get(1).split(" +\\| +"));
            List<String> winningNumbers = List.of(winningAndActualNumbers.get(0).split(" +"));
            List<String> actualNumbers = List.of(winningAndActualNumbers.get(1).split(" +"));

            long wins = findWins(winningNumbers, actualNumbers);
            print("Wins: %s", wins);

            for (int cardToCopy = cardNumber + 1; cardToCopy <= cardNumber + wins; cardToCopy++) {
                int currentMultiplier = multipliers.getOrDefault(cardNumber, 1);
                int currentCardCopies = multipliers.getOrDefault(cardToCopy, 1);
                multipliers.put(cardToCopy, currentCardCopies + currentMultiplier);
            }

            int cardsToAdd = multipliers.getOrDefault(cardNumber, 1);
            cardCount += cardsToAdd;
            print("CardCount: %s, Added: %s", Long.toString(cardCount), cardsToAdd);
        }

        print("Result %s", cardCount);
    }

    private static long findWins(List<String> winningNumbers, List<String> actualNumbers) {
        return actualNumbers.stream()
            .filter(winningNumbers::contains)
            .count();
    }
}
