package net.tracystacktrace.hellogui.menu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.tracystacktrace.hellogui.Translation;
import net.tracystacktrace.hellogui.apis.ISliderUpdate;
import net.tracystacktrace.hellogui.element.SliderCompact;
import net.tracystacktrace.hellogui.func.FloatNormalization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GuiMultipleSliders extends GuiScreen implements ISliderUpdate {

    protected final String menuTitle;
    protected final String[] sliderNames;
    protected final float[] initialValues;
    protected final FloatNormalization normalization;
    protected final Consumer<float[]> saveHandler;

    //value handler
    protected SliderCompact[] sliders;
    protected float[] value;

    public GuiMultipleSliders(
            @Nullable GuiScreen parentScreen,
            @NotNull String menuTitle,
            @NotNull String[] slidersNames,
            float[] initialValues,
            @NotNull FloatNormalization normalization,
            @NotNull Consumer<float[]> saveHandler
    ) {
        this.parentScreen = parentScreen;
        this.menuTitle = Translation.quickTranslate(menuTitle);
        this.sliderNames = slidersNames;
        this.initialValues = initialValues;
        this.normalization = normalization;
        this.saveHandler = saveHandler;

        this.value = new float[initialValues.length];
        System.arraycopy(initialValues, 0, this.value, 0, initialValues.length);
    }

    @Override
    public void initGui() {
        this.controlList.clear();

        final int jump1 = (20 * this.initialValues.length) + (5 * (this.initialValues.length - 1));

        final int offsetX = this.width / 2 - 100;
        final int offsetY = this.height / 2 - (30 + jump1) / 2;

        this.sliders = new SliderCompact[this.initialValues.length];
        for(int i = 0; i < this.initialValues.length; i++) {
            this.sliders[i] = new SliderCompact(i, offsetX, offsetY + (25 * i), 200, 20, this.sliderNames[i], normalization.normalize(this.value[i]));
            this.sliders[i].setTrigger(this);
            this.controlList.add(sliders[i]);
        }

        this.controlList.add(new GuiButton(-1, offsetX, offsetY + 10 + jump1, 95, 20, Translation.quickTranslate("hellogui.reset")));
        this.controlList.add(new GuiButton(-2, offsetX + 105, offsetY + 10 + jump1, 95, 20, Translation.quickTranslate("hellogui.save_exit")));

        this.onSliderChanged(-1);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            //reset values
            if (guiButton.id == -1) {
                for(int i = 0; i < initialValues.length; i++) {
                    this.value[i] = initialValues[i];
                    this.sliders[i].sliderValue = normalization.normalize(this.initialValues[i]);
                    this.sliders[i].displayString = String.format(this.sliderNames[i], this.value[i]);
                }
                return;
            }

            //exit and save
            if (guiButton.id == -2) {
                this.saveHandler.accept(this.value);
                this.mc.displayGuiScreen(this.parentScreen);
                return;
            }
        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();

        final int offsetY = this.height / 2 - (30 + (20 * this.value.length) + (5 * (this.value.length - 1))) / 2;
        this.drawCenteredString(fontRenderer, this.menuTitle, this.width / 2, offsetY - 29, 0xFFFFFFFF);

        super.drawScreen(mouseX, mouseY, deltaTicks);
    }

    @Override
    public void onSliderChanged(int slider_id) {
        if(slider_id == -1) {
            for(int i = 0; i < this.initialValues.length; i++) {
                this.value[i] = normalization.denormalize(this.sliders[i].sliderValue);
                this.sliders[i].displayString = String.format(this.sliderNames[i], this.value[i]);
            }

            return;
        }

        if(slider_id >= 0 && slider_id < this.initialValues.length) {
            this.value[slider_id] = normalization.denormalize(this.sliders[slider_id].sliderValue);
            this.sliders[slider_id].displayString = String.format(this.sliderNames[slider_id], this.value[slider_id]);
        }
    }
}
