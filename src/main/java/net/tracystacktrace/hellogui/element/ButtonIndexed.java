package net.tracystacktrace.hellogui.element;

import net.minecraft.client.gui.GuiButton;

public class ButtonIndexed extends GuiButton {

    protected final String[] options;

    protected int index;

    public ButtonIndexed(int _id, int x, int y, int w, int h, String[] options) {
        super(_id, x, y, w, h, "");
        this.options = options;

        this.displayString = options[0];
        this.index = 0;
    }

    public ButtonIndexed(int _id, int x, int y, String[] options) {
        this(_id, x, y, 200, 20, options);
    }

    public void setElementIndex(int i) {
        if (i >= 0 && i < options.length) {
            this.index = i;
            this.displayString = this.options[i];
        }
    }

    public void moveNextElement() {
        if (this.index + 1 == this.options.length) {
            this.index = 0;
        } else {
            this.index++;
        }

        this.displayString = options[index];
    }

    public int getCurrentIndex() {
        return this.index;
    }

}
