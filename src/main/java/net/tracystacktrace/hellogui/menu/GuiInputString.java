package net.tracystacktrace.hellogui.menu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.hellogui.NumberFeatures;
import net.tracystacktrace.hellogui.func.KeyPredicate;
import net.tracystacktrace.hellogui.func.TextInputResponse;

/**
 * A small dialog to input a string
 */
public class GuiInputString extends GuiScreen {

    protected final boolean allowBack;
    protected final String question;
    protected final String defValue;
    protected final TextInputResponse resultHandler;
    protected final KeyPredicate checkInput;
    protected final int minLength;
    protected final int maxLength;

    protected GuiTextField inputTextField;

    public GuiInputString(GuiScreen parentScreen, boolean allowBack, String question, String defValue, TextInputResponse resultHandler, KeyPredicate checkInput, int minLength, int maxLength) {
        this.parentScreen = parentScreen;
        this.allowBack = allowBack;
        this.question = question;
        this.defValue = defValue;
        this.resultHandler = resultHandler;
        this.checkInput = checkInput;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public GuiInputString(GuiScreen parentScreen, String question, String defValue, TextInputResponse resultHandler) {
        this(parentScreen, false, question, defValue, resultHandler, null, 0, 128);
    }

    @Override
    public void initGui() {
        this.controlList.clear();

        this.inputTextField = new GuiTextField(this.width / 2 - 100, this.height / 2 - 10, 200, 20, "");
        this.inputTextField.isEnabled = true;
        this.inputTextField.isFocused = true;
        this.inputTextField.setMaxStringLength(this.maxLength);

        if (this.defValue != null) {
            this.inputTextField.setText(this.defValue);
            this.inputTextField.setCursorPosition(this.defValue.length());
        }

        final StringTranslate translate = StringTranslate.getInstance();

        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 20, 96, 20, translate.translateKey("hellogui.reset")));
        this.controlList.add(new GuiButton(1, this.width / 2 + 4, this.height / 2 + 20, 96, 20, translate.translateKey("hellogui.done")));
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.inputTextField.setText(this.defValue != null ? this.defValue : "");
                this.inputTextField.setCursorPosition(this.inputTextField.text.length());
            }

            if (guiButton.id == 1) {
                this.resultHandler.process(this.inputTextField.getText(), parentScreen);
            }
        }
    }

    @Override
    public void keyTyped(char eventChar, int eventKey) {
        if (eventKey == 1 && !this.allowBack) {
            return;
        }

        if (this.inputTextField.isFocused) {
            if (NumberFeatures.isValidTextManipulationKey(eventKey) || checkInput == null || checkInput.check(eventChar, eventKey)) {
                this.inputTextField.textboxKeyTyped(eventChar, eventKey);
                ((GuiButton) this.controlList.get(1)).enabled = this.inputTextField.getText().length() >= this.minLength;
            }
        }

        super.keyTyped(eventChar, eventKey);
    }

    @Override
    public void mouseClicked(float x, float y, int click) {
        super.mouseClicked(x, y, click);
        this.inputTextField.mouseClicked(x, y, click);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();
        this.inputTextField.drawTextBox();
        this.drawCenteredString(fontRenderer, this.question, this.width / 2, this.height / 2 - 29, 0xffffffff);
        super.drawScreen(mouseX, mouseY, deltaTicks);
    }
}
