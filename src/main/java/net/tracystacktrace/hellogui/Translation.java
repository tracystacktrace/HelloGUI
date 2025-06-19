package net.tracystacktrace.hellogui;

import net.minecraft.common.util.i18n.StringTranslate;

public final class Translation {

    public static String quickTranslate(String input) {
        if(input == null || input.isEmpty()) {
            return "";
        }
        return StringTranslate.getInstance().translateKey(input);
    }

    public static String quickTranslate(String input, String first) {
        if(input == null || input.isEmpty()) {
            return "";
        }
        final String inputTranslation = StringTranslate.getInstance().translateKey(input);
        return String.format(inputTranslation, StringTranslate.getInstance().translateKey(first));
    }
}
