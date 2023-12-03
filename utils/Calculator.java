package utils;

import java.util.List;

public class Calculator {

    public static int sum(List<Integer> intList) {
        return intList.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
