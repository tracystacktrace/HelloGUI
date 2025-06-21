package net.tracystacktrace.hellogui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlider;
import net.tracystacktrace.hellogui.Translation;
import net.tracystacktrace.hellogui.apis.ISliderUpdate;

public class SliderCompact extends GuiSlider {

    protected final String prebakedString;

    protected ISliderUpdate triggerUpdate;

    public SliderCompact(int _id, int x, int y, int w, int h, String text, float value) {
        super(_id, x, y, text, value);
        this.width = w;
        this.height = h;
        this.prebakedString = Translation.quickTranslate(text);
    }

    public SliderCompact(int _id, int x, int y, String text, float value) {
        this(_id, x, y, 100, 20, text, value);
    }

    public void setTrigger(ISliderUpdate trigger) {
        this.triggerUpdate = trigger;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, float x, float y) {
        super.mouseDragged(minecraft, x, y);
        if (this.visible && this.dragging) {
            this.displayString = String.format(this.prebakedString, this.sliderValue);

            if (this.triggerUpdate != null) {
                this.triggerUpdate.onSliderChanged(this.id);
            }
        }
    }

}
