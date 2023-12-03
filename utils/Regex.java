package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static Matcher getMatcher(String pattern, String matching) {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(matching);
        return matcher;
    }
}
