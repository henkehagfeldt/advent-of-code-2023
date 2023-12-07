package day7;

import static java.util.stream.Collectors.toList;
import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;
import static utils.Regex.getMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class Task1 {
    public static void main(String[] args) throws IOException {
        List<String> dayInput = readLinesOfFile("day7/data.input");

        List<String> handStrings = dayInput.stream()
            .map(val -> val.split(" +")[0])
            .collect(toList());

        List<String> bidStrings = dayInput.stream()
            .map(val -> val.split(" +")[1])
            .collect(toList());

        List<Hand> hands = new ArrayList<>();

        for(int h = 0; h < handStrings.size(); h++) {
            hands.add(new Hand(handStrings.get(h), Integer.valueOf(bidStrings.get(h))));
        }

        List<Hand> sortedHands = hands.stream().sorted().collect(toList());

        long total = 0;
        for(int rank = 1; rank <= sortedHands.size(); rank++) {
            Hand hand = sortedHands.get(rank - 1);
            hand.getMainRank();
            int bidValue = hand.getBid();
            int handValue = bidValue * rank;
            print("Rank %s, Hand %s, Bid %s, Score: %s", rank, hand, bidValue, handValue);
            total += handValue;
        }

        print("Total sum: %s", total);
    }

    private static class Hand implements Comparable<Hand> {
        Integer bid;
        String hand;
        String sortedHand;


        Hand(String hand, Integer bid) {
            this.hand = hand;
            this.bid = bid;
            sortHand();
        }

        private void sortHand() {
            char[] handArray = this.hand.toCharArray();
            Arrays.sort(handArray);
            this.sortedHand = String.valueOf(handArray);
        }

        public int getMainRank() {
            Matcher fiveOfKind = getMatcher("(A|K|Q|J|T|[2-9])\\1\\1\\1\\1", this.sortedHand);
            Matcher fourOfKind = getMatcher("(A|K|Q|J|T|[2-9])\\1\\1\\1", this.sortedHand);
            Matcher threeOfKind = getMatcher("(A|K|Q|J|T|[2-9])\\1\\1", this.sortedHand);
            Matcher pair = getMatcher("(A|K|Q|J|T|[2-9])\\1", this.sortedHand);

            if (fiveOfKind.find()) {
                return 7;
            }

            if (fourOfKind.find()) {
                return 6;
            }

            if (this.hand.chars().distinct().count() == 2) {
                return 5;
            }

            if (threeOfKind.find()) {
                return 4;
            }

            if (pair.find()) {
                if (pair.find()) {
                    return 3;
                } else {
                    return 2;
                }
            }

            return 1;
        }

        private static int getIntegerValue(char x) {
            if (x == 'A')
                return 14;
            else if (x == 'K')
                return 13;
            else if (x == 'Q')
                return 12;
            else if (x == 'J')
                return 11;
            else if (x == 'T')
                return 10;
            else
                return Integer.parseInt(String.valueOf(x));
        }

        public int getBid() {
            return this.bid;
        }

        @Override
        public int compareTo(Hand o) {
            int mainValue = getMainRank() - o.getMainRank();

            if (mainValue == 0) {
                for (int h = 0; h < 5; h++) {
                    if (this.hand.charAt(h) != o.hand.charAt(h)) {
                        return (getIntegerValue(this.hand.charAt(h)) - getIntegerValue(o.hand.charAt(h)));
                    }
                }

                return 0;
            } else {
                return mainValue;
            }
        }

        @Override
        public String toString() {
            return this.hand;
        }
    }
}
