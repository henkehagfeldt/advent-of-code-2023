package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Parser {

    public static List<String> readLinesOfFile(String fileName) throws IOException {
        return Files.readAllLines(Path.of(fileName));
    }

}
