package net.tracystacktrace.hellogui.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.tracystacktrace.hellogui.Translation;
import net.tracystacktrace.hellogui.func.AgreementResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GuiCustomYesNo extends GuiScreen {

    protected final String[] message;
    protected final String answerOk;
    protected final String answerDeny;
    protected final AgreementResponse trigger;

    public GuiCustomYesNo(
            @Nullable GuiScreen parentScreen,
            @NotNull String[] message,
            @NotNull String answerOk,
            @Nullable String answerDeny,
            @Nullable AgreementResponse responseTrigger
    ) {
        this.parentScreen = parentScreen;
        this.message = message;
        this.answerOk = Translation.quickTranslate(answerOk);
        this.answerDeny = answerDeny != null ? Translation.quickTranslate(answerDeny) : null;
        this.trigger = responseTrigger != null ? responseTrigger : (notUsed, ps) -> Minecraft.getInstance().displayGuiScreen(ps);
    }

    public GuiCustomYesNo(
            @Nullable GuiScreen parentScreen,
            @NotNull String message,
            @NotNull String answerOk,
            @Nullable String answerDeny,
            @Nullable AgreementResponse responseTrigger
    ) {
        this(parentScreen, Translation.quickTranslate(message).split("\n"), answerOk, answerDeny, responseTrigger);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            this.trigger.process(guiButton.id == 0, this.parentScreen);
        }
    }

    @Override
    public void initGui() {
        this.controlList.clear();

        final int offsetY = this.height / 2 - (42 + 12 * this.message.length) / 2;
        final int offsetX = this.width / 2 - 125;

        if (answerDeny != null) {
            this.controlList.add(new GuiButton(0, offsetX, offsetY + 34, 120, 20, this.answerOk));
            this.controlList.add(new GuiButton(1, offsetX + 130, offsetY + 34, 120, 20, this.answerDeny));
        } else {
            this.controlList.add(new GuiButton(0, offsetX, offsetY + 34, 250, 20, this.answerOk));
        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();

        final int offsetY = this.height / 2 - (42 + 12 * this.message.length) / 2;

        for (int i = 0; i < this.message.length; i++) {
            this.drawCenteredString(fontRenderer, this.message[i], this.width / 2, offsetY + (12 * i), 0xFFFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, deltaTicks);
    }
}
