package net.tracystacktrace.hellogui.menu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.hellogui.func.ChoiceResponse;

/**
 * This is a simple one-choice dialog as a screen
 */
public class GuiBakedChoice extends GuiScreen {

    protected final boolean allowBack;
    protected final String question;
    protected final String[] choiceOptions;
    protected final ChoiceResponse handler;

    public GuiBakedChoice(GuiScreen parentScreen, boolean allowBack, String question, String[] choiceOptions, ChoiceResponse choiceHandler) {
        this.parentScreen = parentScreen;
        this.allowBack = allowBack;
        this.question = question;
        this.choiceOptions = choiceOptions;
        this.handler = choiceHandler;
    }

    @Override
    public void initGui() {
        this.controlList.clear();

        final int offsetX = this.width / 2 - 100;
        final int offsetY = this.height / 2 - (choiceOptions.length * 20 + (choiceOptions.length - 1) * 6) / 2;

        for (int i = 0; i < choiceOptions.length; i++) {
            this.controlList.add(new GuiButton(i, offsetX, offsetY + (26 * i), 200, 20, choiceOptions[i]));
        }

        if (this.allowBack) {
            this.controlList.add(new GuiButton(-1, this.width - 70, 10, 60, 20, StringTranslate.getInstance().translateKey("gui.cancel")));
        }
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == -1) {
                this.mc.displayGuiScreen(this.parentScreen);
                return;
            }
            handler.process(guiButton.id, this);
        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();

        final int offsetY = this.height / 2 - (choiceOptions.length * 20 + (choiceOptions.length - 1) * 10) / 2 - 21;

        this.drawCenteredString(fontRenderer, question, this.width / 2, offsetY, 0xffffffff);
        super.drawScreen(mouseX, mouseY, deltaTicks);
    }

    @Override
    public void keyTyped(char eventChar, int eventKey) {
        if (eventKey == 1 && !this.allowBack) {
            return;
        }
        super.keyTyped(eventChar, eventKey);
    }
}
