package utils;

import java.util.List;

public class Printer {

    public static void print(String text, Object... args) {
        System.out.println(String.format(text, args));
    }

    public static void print(List<String> stringList) {
        System.out.println(stringList);
    }
}
