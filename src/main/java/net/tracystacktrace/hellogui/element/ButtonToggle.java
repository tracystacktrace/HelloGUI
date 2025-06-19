package net.tracystacktrace.hellogui.element;

import net.minecraft.client.gui.GuiButton;
import net.tracystacktrace.hellogui.Translation;

public class ButtonToggle extends GuiButton {
    public final String labelOff;
    public final String labelOn;

    protected boolean value;

    public ButtonToggle(int id, int x, int y, int w, int h, String labelOff, String labelOn) {
        super(id, x, y, w, h, labelOff);
        this.labelOff = labelOff;
        this.labelOn = labelOn;
    }

    public ButtonToggle(int id, int x, int y, int w, int h) {
        this(
                id, x, y, w, h,
                Translation.quickTranslate("hellogui.off"),
                Translation.quickTranslate("hellogui.on")
        );
    }

    public ButtonToggle(int id, int x, int y, int w, int h, String descriptor, String off, String on) {
        this(
                id, x, y, w, h,
                Translation.quickTranslate(descriptor, off),
                Translation.quickTranslate(descriptor, on)
        );
    }

    public ButtonToggle(int id, int x, int y, int w, int h, String descriptor) {
        this(
                id, x, y, w, h,
                Translation.quickTranslate(descriptor, "hellogui.off"),
                Translation.quickTranslate(descriptor, "hellogui.on")
        );
    }

    public void toggleValue(boolean b) {
        this.value = b;
        this.displayString = b ? this.labelOn : this.labelOff;
    }

    public void toggleValue() {
        this.toggleValue(!this.value);
    }

    public boolean getValue() {
        return this.value;
    }
}
