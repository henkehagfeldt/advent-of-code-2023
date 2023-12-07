package day7;

import static java.util.stream.Collectors.toList;
import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task2 {
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
            int jokers = (int) this.hand.chars().filter(c -> c == 'J').count();

            Map<Integer, Integer> countMap = new HashMap<>();

            for (int c : this.hand.chars().filter(c -> c != 'J').toArray()) {
                countMap.putIfAbsent(c, 0);
                countMap.put(c, countMap.get(c) + 1);
            }

            if (countMap.containsValue(5 - jokers) || jokers == 5) {
                print("Five of a kind %s", this.sortedHand);
                return 7;
            }

            if (countMap.containsValue(4 - jokers)) {
                print("Four of a kind %s", this.sortedHand);
                return 6;
            }

            if (countMap.size() == 2) {
                print("Full house %s", this.sortedHand);
                return 5;
            }

            if (countMap.containsValue(3 - jokers)) {
                print("Three of a kind %s", this.sortedHand);
                return 4;
            }

            if (countMap.size() < 5) {
                if (countMap.size() < 4) {
                    print("2 pairs %s", this.sortedHand);
                    return 3;
                } else {
                    print("1 pair %s", this.sortedHand);
                    return 2;
                }
            }

            print("High Card %s", this.sortedHand);
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
                return 1;
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
