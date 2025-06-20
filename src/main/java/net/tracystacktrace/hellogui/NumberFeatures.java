package net.tracystacktrace.hellogui;

public final class NumberFeatures {

    public static boolean isValidTextManipulationKey(int eventKey) {
        return eventKey == 14 || eventKey == 203 || eventKey == 205; //left, right and backspace
    }

    public static boolean isValidInputDigit(char eventChar, int eventKey) {
        return Character.isDigit(eventChar) || isValidTextManipulationKey(eventKey);
    }

    public static boolean isValidInputHEX(char c, int eventKey) {
        return (c >= '0' && c <= '9') ||
                (c >= 'a' && c <= 'f') ||
                (c >= 'A' && c <= 'F') ||
                isValidTextManipulationKey(eventKey); //left, right and backspace
    }

    public static boolean isUnsignedByteRange(short value, char input) {
        if (Character.isDigit(input))
            return Short.parseShort(String.valueOf(value) + input) < 256;
        return true;
    }

    public static String fixStringARGB(String damaged) {
        if (damaged.length() == 8) {
            return damaged;
        }

        if (damaged.length() > 8) {
            return damaged.substring(0, 8);
        }

        return damaged + "0".repeat(8 - damaged.length());
    }

    public static short parseColorShort(final String s) {
        if (s == null || s.isEmpty()) return 0;
        return (short) Math.max(0, Math.min(255, Integer.parseInt(s)));
    }
}
