package net.tracystacktrace.hellogui.func;

@FunctionalInterface
public interface KeyPredicate {
    boolean check(char eventChar, int eventKey);
}
