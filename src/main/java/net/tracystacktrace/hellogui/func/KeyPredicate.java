package net.tracystacktrace.hellogui.func;

@FunctionalInterface
public interface KeyPredicate {
    boolean checkKey(char eventChar, int eventKey);
}
