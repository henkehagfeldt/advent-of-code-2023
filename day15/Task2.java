package day15;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static utils.Parser.readLinesOfFile;
import static utils.Printer.print;

public class Task2 {

    public static void main(String[] args) throws IOException {

        String letters = readLinesOfFile("day15/data.input").get(0);
        String[] groups = letters.split(",");
        HashMap<Integer, List<Entry>> lenses = new HashMap<>();
        for (String group : groups) {
            String label = group.split("[=-]")[0];
            String operation = group.substring(label.length(), label.length() + 1);

            int hash = getHash(label);
            if (operation.equals("=")) {

                Integer value = Integer.parseInt(String.valueOf(group.split("=")[1]));
                Entry entry = new Entry(label, value);

                if(lenses.containsKey(hash)) {
                    List<Entry> entries = lenses.get(hash);

                    if (entries.contains(entry)) {
                        entries.set(entries.indexOf(entry), entry);
                    } else {
                        entries.add(new Entry(label, value));
                    }
                } else {
                    List<Entry> entryList = new ArrayList<>();
                    entryList.add(new Entry(label, value));
                    lenses.put(hash, entryList);
                }

            } else if(operation.equals("-")) {
                if (lenses.containsKey(hash)) {
                    List<Entry> entries = lenses.get(hash);
                    deleteLabel(entries, label);
                }
            }
        }

        long focusingPower = 0;
        for (int b = 0; b < 256; b++) {
            List<Entry> entries = lenses.getOrDefault(b, List.of());
            for (int e = 0; e < entries.size(); e++) {
                int value = entries.get(e).lensValue;
                focusingPower += (long) (b + 1) * (e + 1) * value;
            }
        }

        print("Result %s", focusingPower);
    }

    private static void deleteLabel(List<Entry> entries, String label) {
        for (int e = 0; e < entries.size(); e++) {
            Entry entry = entries.get(e);
            if (entry.label.equals(label)) {
                entries.remove(e);
                return;
            }
        }
    }

    private static int getHash(String string) {
        int hashValue = 0;
        for (char c : string.toCharArray()) {
            hashValue += (int) c;
            hashValue *= 17;
            hashValue = hashValue % 256;
        }
        return hashValue;
    }

    private static class Entry {

        String label;
        Integer lensValue;

        Entry(String label, Integer lensValue) {
            this.label = label;
            this.lensValue = lensValue;
        }

        @Override
        public boolean equals(Object o) {
            Entry other = (Entry) o;
            return other.label.equals(this.label);
        }
    }
}
