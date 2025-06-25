package net.tracystacktrace.hellogui.element;

import net.minecraft.client.gui.GuiButton;

/**
 * A button that contains several choose options, so the end user could
 * choose one specific element from the given list.
 * <br>
 * The elements are indexed and work with array of possible options.
 * <br>
 * In order to make this thing work, run {@link ButtonIndexed#moveNextElement()} within {@link net.minecraft.client.gui.GuiScreen#actionPerformed(GuiButton)} method.
 * @since 0.1
 */
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

    /**
     * Directly sets the button to the index provided
     * @param i new index
     * @return true if successfully moved, otherwise false
     */
    public boolean setElementIndex(int i) {
        if (i >= 0 && i < options.length) {
            this.index = i;
            this.displayString = this.options[i];
            return true;
        }
        return false;
    }

    /**
     * Move to the next index and update the button
     */
    public void moveNextElement() {
        if (this.index + 1 == this.options.length) {
            this.index = 0;
        } else {
            this.index++;
        }

        this.displayString = options[index];
    }

    /**
     * Get the current index of the button
     * @return the current index
     */
    public int getCurrentIndex() {
        return this.index;
    }

}
