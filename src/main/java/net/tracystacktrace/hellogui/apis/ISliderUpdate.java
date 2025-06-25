package net.tracystacktrace.hellogui.apis;

/**
 * A funny interface that will be called upon the slider being changed
 * <br>
 * The usual way to get this done is to implement this interface in a class
 * that extends {@link net.minecraft.client.gui.GuiScreen} (custom GUI class) and then provide it as:
 * <pre>
 * {@code
 *  SliderCompact compact = ...;
 *  compact.setTrigger(this); //sets the trigger
 * }
 * </pre>
 * @since 0.1
 */
public interface ISliderUpdate {
    /**
     * This method is called when a slider detects the change
     * @param slider_id the slider id
     */
    void onSliderChanged(int slider_id);
}
