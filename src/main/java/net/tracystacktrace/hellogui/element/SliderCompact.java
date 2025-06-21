package net.tracystacktrace.hellogui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlider;
import net.tracystacktrace.hellogui.apis.ISliderUpdate;

public class SliderCompact extends GuiSlider {

    protected final String text;

    protected ISliderUpdate triggerUpdate;

    public SliderCompact(int id, int x, int y, int w, int h, String text, float value) {
        super(id, x, y, text, value);
        this.width = w;
        this.height = h;
        this.text = text;
    }

    public SliderCompact(int id, int x, int y, String text, float value) {
        this(id, x, y, 100, 20, text, value);
    }

    public void setTrigger(ISliderUpdate trigger) {
        this.triggerUpdate = trigger;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, float x, float y) {
        super.mouseDragged(minecraft, x, y);
        if (this.visible && this.dragging) {
            this.displayString = String.format(this.text, this.sliderValue);

            if (this.triggerUpdate != null) {
                this.triggerUpdate.onSliderChanged(this.id);
            }
        }
    }

}
