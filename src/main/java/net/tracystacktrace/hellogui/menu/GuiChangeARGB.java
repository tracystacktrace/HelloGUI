package net.tracystacktrace.hellogui.menu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.hellogui.NumberFeatures;
import net.tracystacktrace.hellogui.Translation;
import net.tracystacktrace.hellogui.apis.ISliderUpdate;
import net.tracystacktrace.hellogui.element.SliderCompact;
import org.lwjgl.input.Keyboard;

import java.util.function.IntConsumer;

public class GuiChangeARGB extends GuiScreen implements ISliderUpdate {

    //Red
    private GuiTextField redIntTextField;
    private SliderCompact redIntSlider;
    private short red;

    //Green
    private GuiTextField greenIntTextField;
    private SliderCompact greenIntSlider;
    private short green;

    //Blue
    private GuiTextField blueIntTextField;
    private SliderCompact blueIntSlider;
    private short blue;

    //Alpha
    private GuiTextField alphaIntTextField;
    private SliderCompact alphaIntSlider;
    private short alpha;

    //Common
    private GuiTextField hexTextField;

    //Internal
    private final GuiScreen parentScreen;
    private final IntConsumer saveHandler;
    protected final String screenTitle;

    public GuiChangeARGB(GuiScreen parentScreen, String title, int argb, IntConsumer saveHandler) {
        this.parentScreen = parentScreen;
        this.screenTitle = Translation.quickTranslate(title);
        this.saveHandler = saveHandler;
        this.alpha = (short) ((argb >> 24) & 0xFF);
        this.red = (short) ((argb >> 16) & 0xFF);
        this.green = (short) ((argb >> 8) & 0xFF);
        this.blue = (short) ((argb) & 0xFF);
    }

    @Override
    public void initGui() {
        this.controlList.clear();
        Keyboard.enableRepeatEvents(true);

        final int offsetX = this.width / 2 - 120;
        final int offsetY = this.height / 2 - 55;

        final StringTranslate translate = StringTranslate.getInstance();

        //buttons init
        this.controlList.add(new GuiButton(1, this.width / 2 - 45, offsetY + 120, 90, 20, translate.translateKey("gui.done")));

        //sliders init
        this.redIntSlider = new SliderCompact(2, offsetX, offsetY, translate.translateKeyFormat("hellogui.red", this.red), this.red / 255.0F);
        this.greenIntSlider = new SliderCompact(3, offsetX, offsetY + 30, translate.translateKeyFormat("hellogui.green", this.green), this.green / 255.0F);
        this.blueIntSlider = new SliderCompact(4, offsetX, offsetY + 60, translate.translateKeyFormat("hellogui.blue", this.blue), this.blue / 255.0F);
        this.alphaIntSlider = new SliderCompact(5, offsetX, offsetY + 90, translate.translateKeyFormat("hellogui.alpha", this.alpha), this.alpha / 255.0F);

        this.redIntSlider.setTrigger(this);
        this.greenIntSlider.setTrigger(this);
        this.blueIntSlider.setTrigger(this);
        this.alphaIntSlider.setTrigger(this);

        this.controlList.add(this.redIntSlider);
        this.controlList.add(this.greenIntSlider);
        this.controlList.add(this.blueIntSlider);
        this.controlList.add(this.alphaIntSlider);

        //text fields init
        this.redIntTextField = new GuiTextField(offsetX + 110, offsetY, 50, 20, String.valueOf(this.red));
        this.greenIntTextField = new GuiTextField(offsetX + 110, offsetY + 30, 50, 20, String.valueOf(this.green));
        this.blueIntTextField = new GuiTextField(offsetX + 110, offsetY + 60, 50, 20, String.valueOf(this.blue));
        this.alphaIntTextField = new GuiTextField(offsetX + 110, offsetY + 90, 50, 20, String.valueOf(this.alpha));

        this.redIntTextField.setMaxStringLength(3);
        this.redIntTextField.isEnabled = true;

        this.greenIntTextField.setMaxStringLength(3);
        this.greenIntTextField.isEnabled = true;

        this.blueIntTextField.setMaxStringLength(3);
        this.blueIntTextField.isEnabled = true;

        this.alphaIntTextField.setMaxStringLength(3);
        this.alphaIntTextField.isEnabled = true;

        //hex field
        this.hexTextField = new GuiTextField(offsetX + 170, offsetY, 70, 20, "");
        this.hexTextField.setMaxStringLength(8);
        this.hexTextField.isEnabled = true;
        this.updateHexTextFieldDisplayString();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.redIntTextField.updateCursorCounter();
        this.greenIntTextField.updateCursorCounter();
        this.blueIntTextField.updateCursorCounter();
        this.alphaIntTextField.updateCursorCounter();
        this.hexTextField.updateCursorCounter();
    }

    @Override
    public void keyTyped(char eventChar, int eventKey) {
        if (this.redIntTextField.isFocused && NumberFeatures.isValidDigit(eventChar, eventKey) && NumberFeatures.within256Range(this.red, eventChar)) {
            this.redIntTextField.textboxKeyTyped(eventChar, eventKey);
            this.onUpdateFromTextFields();
        }

        if (this.greenIntTextField.isFocused && NumberFeatures.isValidDigit(eventChar, eventKey) && NumberFeatures.within256Range(this.green, eventChar)) {
            this.greenIntTextField.textboxKeyTyped(eventChar, eventKey);
            this.onUpdateFromTextFields();
        }

        if (this.blueIntTextField.isFocused && NumberFeatures.isValidDigit(eventChar, eventKey) && NumberFeatures.within256Range(this.blue, eventChar)) {
            this.blueIntTextField.textboxKeyTyped(eventChar, eventKey);
            this.onUpdateFromTextFields();
        }

        if (this.alphaIntTextField.isFocused && NumberFeatures.isValidDigit(eventChar, eventKey) && NumberFeatures.within256Range(this.alpha, eventChar)) {
            this.alphaIntTextField.textboxKeyTyped(eventChar, eventKey);
            this.onUpdateFromTextFields();
        }

        if (this.hexTextField.isFocused && NumberFeatures.isValidHEX(eventChar, eventKey)) {
            this.hexTextField.textboxKeyTyped(eventChar, eventKey);
            this.onUpdateFromHexField();
        }

        //esc quit
        if (eventKey == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }

        //enter quit
        if (eventChar == '\r') {
            this.saveResult();
            this.mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public void mouseClicked(float x, float y, int click) {
        super.mouseClicked(x, y, click);
        this.redIntTextField.mouseClicked(x, y, click);
        this.greenIntTextField.mouseClicked(x, y, click);
        this.blueIntTextField.mouseClicked(x, y, click);
        this.alphaIntTextField.mouseClicked(x, y, click);
        this.hexTextField.mouseClicked(x, y, click);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();

        this.redIntTextField.drawTextBox();
        this.greenIntTextField.drawTextBox();
        this.blueIntTextField.drawTextBox();
        this.alphaIntTextField.drawTextBox();
        this.hexTextField.drawTextBox();

        final int offsetX = this.width / 2 - 120;
        final int offsetY = this.height / 2 - 40;

        this.drawCenteredString(this.fontRenderer, this.screenTitle, (float) this.width / 2, offsetY - 34, 0x00FFFFFF);

        super.drawScreen(mouseX, mouseY, deltaTicks);

        //draw color
        drawRect(
                offsetX + 170, offsetY + 15,
                offsetX + 170 + 70, offsetY + 15 + 80,
                ((this.alpha & 0xFF) << 24) | ((this.red & 0xFF) << 16) | ((this.green & 0xFF) << 8) | (this.blue & 0xFF)
        );
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            //done button
            if (guiButton.id == 1) {
                this.saveResult();
                this.mc.displayGuiScreen(parentScreen);
            }
        }
    }

    public void saveResult() {
        this.saveHandler.accept(((this.alpha & 0xFF) << 24) | ((this.red & 0xFF) << 16) | ((this.green & 0xFF) << 8) | (this.blue & 0xFF));
    }

    /* ===== ===== RGB TEXT/SLIDER METHODS ===== ===== */

    /**
     * Applies update edits from text fields
     * lol
     */
    private void onUpdateFromTextFields() {
        //apply values
        this.red = NumberFeatures.parseColorShort(this.redIntTextField.getText());
        this.green = NumberFeatures.parseColorShort(this.greenIntTextField.getText());
        this.blue = NumberFeatures.parseColorShort(this.blueIntTextField.getText());
        this.alpha = NumberFeatures.parseColorShort(this.alphaIntTextField.getText());

        //force update of sliders values
        this.updateSlidersDisplayString();
        this.updateHexTextFieldDisplayString();
    }

    /**
     * Applies update edits from sliders
     */
    @Override
    public void onSliderChanged(int slider_id) {
        //apply values
        this.red = (short) (this.redIntSlider.sliderValue * 255.0F + 0.5F);
        this.green = (short) (this.greenIntSlider.sliderValue * 255.0F + 0.5F);
        this.blue = (short) (this.blueIntSlider.sliderValue * 255.0F + 0.5F);
        this.alpha = (short) (this.alphaIntSlider.sliderValue * 255.0F + 0.5F);

        final StringTranslate translate = StringTranslate.getInstance();
        this.redIntSlider.displayString = translate.translateKeyFormat("hellogui.red", this.red);
        this.greenIntSlider.displayString = translate.translateKeyFormat("hellogui.green", this.green);
        this.blueIntSlider.displayString = translate.translateKeyFormat("hellogui.blue", this.blue);
        this.alphaIntSlider.displayString = translate.translateKeyFormat("hellogui.alpha", this.alpha);

        //force update of text fields values
        this.updateTextFieldsDisplayString();
        this.updateHexTextFieldDisplayString();
    }

    /**
     * Applies update edits from the hex text field
     */
    private void onUpdateFromHexField() {
        final String safeHex = NumberFeatures.fixStringARGB(this.hexTextField.getText());

        this.alpha = (short) Integer.parseInt(safeHex.substring(0, 2), 16);
        this.red = (short) Integer.parseInt(safeHex.substring(2, 4), 16);
        this.green = (short) Integer.parseInt(safeHex.substring(4, 6), 16);
        this.blue = (short) Integer.parseInt(safeHex.substring(6, 8), 16);

        this.updateSlidersDisplayString();
        this.updateTextFieldsDisplayString();
    }

    /**
     * Update sliders data to current RGB values
     * <br>
     * Primarily edits {@link GuiSlider#sliderValue} and {@link GuiSlider#displayString}
     */
    private void updateSlidersDisplayString() {
        this.redIntSlider.sliderValue = this.red / 255.0F;
        this.greenIntSlider.sliderValue = this.green / 255.0F;
        this.blueIntSlider.sliderValue = this.blue / 255.0F;
        this.alphaIntSlider.sliderValue = this.alpha / 255.0F;

        final StringTranslate translate = StringTranslate.getInstance();
        this.redIntSlider.displayString = translate.translateKeyFormat("hellogui.red", this.red);
        this.greenIntSlider.displayString = translate.translateKeyFormat("hellogui.green", this.green);
        this.blueIntSlider.displayString = translate.translateKeyFormat("hellogui.blue", this.blue);
        this.alphaIntSlider.displayString = translate.translateKeyFormat("hellogui.alpha", this.alpha);
    }

    /**
     * Update text fields data to current RGB values
     * <br>
     * Primarily edits {@link GuiTextField#text} value
     */
    private void updateTextFieldsDisplayString() {
        this.redIntTextField.setText(String.valueOf(this.red));
        this.greenIntTextField.setText(String.valueOf(this.green));
        this.blueIntTextField.setText(String.valueOf(this.blue));
        this.alphaIntTextField.setText(String.valueOf(this.alpha));

        this.redIntTextField.moveCursorBy(4);
        this.greenIntTextField.moveCursorBy(4);
        this.blueIntTextField.moveCursorBy(4);
        this.alphaIntTextField.moveCursorBy(4);
    }

    /**
     * Update hex text field data to current RGB value
     */
    private void updateHexTextFieldDisplayString() {
        final String formatted = String.format("%02X%02X%02X%02X", this.alpha, this.red, this.green, this.blue);
        this.hexTextField.setText(formatted);
        this.hexTextField.moveCursorBy(8);
    }
}
