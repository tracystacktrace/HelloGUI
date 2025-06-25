package net.tracystacktrace.hellogui;

import net.minecraft.common.util.i18n.StringTranslate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.IllegalFormatException;

/**
 * A very simple access to {@link StringTranslate}'s functions in a very-very static manner.
 *
 * @since 0.1
 */
public final class Translation {

    /**
     * A safe wrap for {@link StringTranslate#translateKey(String)}, allows to get a value from ".lang" file
     *
     * @param input lang key string
     * @return translation result from ".lang" file (if exists), or key lang string (if not found)
     */
    public static @NotNull String quickTranslate(@Nullable String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return StringTranslate.getInstance().translateKey(input);
    }

    /**
     * A safe wrap for {@link StringTranslate#translateKey(String)}, allows to get a value from ".lang" file and put another string via {@link String#format(String, Object...)}.
     * <br>
     * Both strings undergo translations
     *
     * @param input  lang key string
     * @param string another string
     * @return translation result from ".lang" file (if exists), or key lang string (if not found)
     */
    public static @NotNull String quickTranslate(@Nullable String input, @Nullable String string) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        try {
            return String.format(
                    StringTranslate.getInstance().translateKey(input),
                    StringTranslate.getInstance().translateKey(string)
            );
        } catch (IllegalFormatException e) {
            return "";
        }
    }

    /**
     * A safe wrap for {@link StringTranslate#translateKey(String)}, allows to get a value from ".lang" file and put another string via {@link String#format(String, Object...)}.
     *
     * @param input lang key string
     * @param i     another value
     * @return translation result from ".lang" file (if exists), or key lang string (if not found)
     */
    public static @NotNull String quickTranslate(@Nullable String input, int i) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        try {
            return String.format(StringTranslate.getInstance().translateKey(input), i);
        } catch (IllegalFormatException e) {
            return "";
        }
    }

    /**
     * A safe wrap for {@link StringTranslate#translateKey(String)}, allows to get a value from ".lang" file and put another string via {@link String#format(String, Object...)}.
     *
     * @param input lang key string
     * @param f     another value
     * @return translation result from ".lang" file (if exists), or key lang string (if not found)
     */
    public static @NotNull String quickTranslate(@Nullable String input, float f) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        try {
            return String.format(StringTranslate.getInstance().translateKey(input), f);
        } catch (IllegalFormatException e) {
            return "";
        }
    }

}
