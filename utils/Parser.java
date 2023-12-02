package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<String> readLinesOfFile(String fileName) throws IOException {
        List<String> result = Files.readAllLines(Path.of(fileName));
        return result;
    }

}
