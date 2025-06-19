package net.tracystacktrace.hellogui.presets;

import net.minecraft.common.util.ChatAllowedCharacters;
import net.tracystacktrace.hellogui.func.KeyPredicate;

public final class KeyPredicatePresets {
    public static final KeyPredicate DEFAULT = (eventChar, eventKey) -> ChatAllowedCharacters.isAllowedCharacter(eventChar);

    public static final KeyPredicate ONLY_DIGITS = (eventChar, eventKey) -> Character.isDigit(eventChar);

    public static final KeyPredicate ONLY_ALPHABET = (eventChar, eventKey) -> Character.isAlphabetic(eventChar);

    public static final KeyPredicate ONLY_ALPHANUMERICAL = (eventChar, eventKey) -> Character.isAlphabetic(eventChar) || Character.isDigit(eventChar);
}
