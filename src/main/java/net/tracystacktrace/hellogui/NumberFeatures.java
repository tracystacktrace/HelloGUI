package net.tracystacktrace.hellogui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NumberFeatures {

    public static boolean isValidCursorKey(int k) {
        return k == 14 || k == 203 || k == 205; //left, right and backspace
    }

    public static boolean isValidDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isValidHEX(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    public static boolean within256Range(short value, char input) {
        if (isValidDigit(input)) {
            return Short.parseShort(Short.toString(value) + input) < 256;
        }
        return true;
    }

    public static @NotNull String fixStringARGB(final @NotNull String s) {
        if (s.length() == 8) {
            return s;
        }

        if (s.length() > 8) {
            return s.substring(0, 8);
        }

        return s + "0".repeat(8 - s.length());
    }

    public static short parseColorShort(final @Nullable String s) {
        if (s == null || s.isEmpty()) return 0;
        return (short) Math.max(0, Math.min(255, Integer.parseInt(s)));
    }

    public static boolean isValidFormattingChar(final char c) {
        return c == 'k' || c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'r';
    }
}
