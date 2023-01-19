package net.lucypoulton.pronouns.api.util;

import java.util.Locale;

public class StringUtils {
    public static String capitalize(String input) {
        return switch (input.length()) {
            case 0 -> input;
            case 1 -> input.toUpperCase(Locale.ROOT);
            default -> input.substring(0, 1).toUpperCase(Locale.ROOT) + input.substring(1).toLowerCase(Locale.ROOT);
        };
    }
}
