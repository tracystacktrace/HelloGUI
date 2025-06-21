package net.tracystacktrace.hellogui.menu;

import it.unimi.dsi.fastutil.floats.FloatConsumer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.tracystacktrace.hellogui.Translation;
import net.tracystacktrace.hellogui.apis.ISliderUpdate;
import net.tracystacktrace.hellogui.element.SliderCompact;
import net.tracystacktrace.hellogui.func.FloatNormalization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GuiChangeBySlider extends GuiScreen implements ISliderUpdate {

    protected final String title;
    protected final String sliderTitle;
    protected final float initialValue;
    protected final FloatNormalization normalization;
    protected final FloatConsumer saveHandler;

    //value handler
    protected SliderCompact slider;
    protected float value;

    public GuiChangeBySlider(
            @Nullable GuiScreen parentScreen,
            @NotNull String title,
            @NotNull String sliderTitle,
            float initial,
            @NotNull FloatNormalization normalization,
            @NotNull FloatConsumer saveHandler
    ) {
        this.parentScreen = parentScreen;
        this.title = Translation.quickTranslate(title);
        this.sliderTitle = Translation.quickTranslate(sliderTitle);
        this.initialValue = initial;
        this.normalization = normalization;
        this.saveHandler = saveHandler;

        this.value = initial;
    }

    @Override
    public void initGui() {
        this.controlList.clear();

        final int offsetX = this.width / 2 - 100;
        final int offsetY = this.height / 2 - 25;

        this.slider = new SliderCompact(0, offsetX, offsetY, 200, 20, "", normalization.normalize(this.value));
        this.controlList.add(this.slider);

        this.controlList.add(new GuiButton(1, offsetX, offsetY + 30, 95, 20, Translation.quickTranslate("bootifulblockoutline.reset")));
        this.controlList.add(new GuiButton(2, offsetX + 105, offsetY + 30, 95, 20, Translation.quickTranslate("gui.done")));

        this.onSliderChanged(0);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.value = initialValue;
                this.slider.sliderValue = normalization.normalize(this.initialValue);
                this.slider.displayString = String.format(this.sliderTitle, this.value);
                return;
            }

            if (guiButton.id == 2) {
                this.saveHandler.accept(this.value);
                this.mc.displayGuiScreen(this.parentScreen);
                return;
            }
        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();

        final int offsetY = this.height / 2 - 25;
        this.drawCenteredString(fontRenderer, this.title, this.width / 2, offsetY - 29, 0xFFFFFFFF);

        super.drawScreen(mouseX, mouseY, deltaTicks);
    }

    @Override
    public void onSliderChanged(int slider_id) {
        this.value = normalization.denormalize(this.slider.sliderValue);
        this.slider.displayString = String.format(this.sliderTitle, this.value);
    }
}
