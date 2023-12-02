package day2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static utils.Parser.readLinesOfFile;

public class Task2{
    public static void main(String[] args) throws IOException {
        List<String> gameInput = readLinesOfFile("day2/data.input");

        List<List<Round>> games = gameInput.stream()
                .map(Task2::convertToGameList)
                .map(gameEntries -> gameEntries.stream()
                        .map(Round::new)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        List<Integer> validGames = new ArrayList<>();
        for (int g = 0; g < games.size(); g++ ) {
            List<Round> gameRounds = games.get(g);
            Game game = new Game(0, 0, 0);

            boolean invalidGame = false;
            for (Round round : gameRounds) {
                System.out.println(round);
                game.red = Math.max(round.red, game.red);
                game.green = Math.max(round.green, game.green);
                game.blue = Math.max(round.blue, game.blue);
            }

            validGames.add(game.red * game.green * game.blue);

        }
        System.out.println(validGames);

        int sum = validGames.stream().mapToInt(Integer::intValue).sum();
        System.out.println(String.format("Result %s", sum));
    }

    private static List<String> convertToGameList(String value) {

        System.out.println(value);

        String cubes = value.split(":")[1];
        List<String> cubeList = List.of(cubes.split(";"));
        return cubeList.stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private static class Game {

        int red = 0;
        int green = 0;
        int blue = 0;

        Game(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        boolean gameValid() {
            return red >= 0 && green >= 0 && blue >= 0;
        }
    }

    private static class Round {
        int red = 0;
        int green = 0;
        int blue = 0;

        Round(String roundString) {
            red = roundString.contains("red")
                    ? parseCount(roundString, "red")
                    : 0;
            green = roundString.contains("green")
                    ? parseCount(roundString, "green")
                    : 0;
            blue = roundString.contains("blue")
                    ? parseCount(roundString, "blue")
                    : 0;
        }

        private int parseCount(String roundString, String color) {
            String patternString = String.format("\\d+ %s", color);
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(roundString);

            if (matcher.find()) {
                String matched = matcher.group();
                return Integer.parseInt(matched.split(" ")[0]);
            }

            return 0;
        }

        public String toString() {
            return String.format("Red %s, Green %s, Blue %s", red, green, blue);
        }
    }
}
