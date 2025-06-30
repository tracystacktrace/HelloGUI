package net.tracystacktrace.hellogui.menu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.tracystacktrace.hellogui.NumberFeatures;
import net.tracystacktrace.hellogui.Translation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GuiSelectIcon extends GuiScreen {

    protected final String title;
    protected final char[] presets;
    protected final Consumer<String> handler;

    protected char icon;
    protected char color = 'f';
    protected boolean bold = false;
    protected boolean italic = false;
    protected boolean underscore = false;
    protected boolean strikethrough = false;

    private int iconIndex = 0;
    private GuiButton buttonLeft;
    private GuiButton buttonRight;
    private GuiButton buttonShow;

    public GuiSelectIcon(
            @Nullable GuiScreen parentScreen,
            @NotNull String title,
            char @NotNull [] presets,
            @Nullable String current,
            @NotNull Consumer<String> handler
    ) {
        this.parentScreen = parentScreen;
        this.title = title;
        this.handler = handler;
        this.presets = presets;
        this.icon = this.presets[0];
        this.processInitial(current);
    }

    @Override
    public void initGui() {
        this.controlList.clear();

        final int offsetX = this.width / 2 - 100;
        final int offsetY = this.height / 2 - 42;

        this.controlList.add(this.buttonLeft = new GuiButton(-1, offsetX, offsetY + 22, 20, 20, "<"));
        this.controlList.add(this.buttonRight = new GuiButton(-2, offsetX + 180, offsetY + 22, 20, 20, ">"));
        this.controlList.add(this.buttonShow = new GuiButton(-3, offsetX + 20, offsetY + 22, 160, 20, Character.toString(this.icon)));
        this.controlList.add(new GuiButton(0, offsetX, offsetY + 52, 200, 20, Translation.quickTranslate("gui.done")));

        this.buttonShow.enabled = false;
        this.updateButtonState();
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();

        final int offsetY = this.height / 2 - 42;

        this.drawCenteredString(mc.fontRenderer, this.title, this.width / 2, offsetY, 0xFFFFFFFF);

        super.drawScreen(mouseX, mouseY, deltaTicks);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if(guiButton.id == 0) {
                handler.accept(this.cookString());
                this.mc.displayGuiScreen(this.parentScreen);
                return;
            }
            if (guiButton.id == -1) {
                this.moveIconIndex(true);
                return;
            }
            if (guiButton.id == -2) {
                this.moveIconIndex(false);
                return;
            }
        }

    }

    private void moveIconIndex(boolean left) {
        //protect from out of bounds exception
        if (left && this.iconIndex == 0) return;
        if (!left && (this.iconIndex + 1 < this.presets.length)) return;

        //update
        this.iconIndex += (left ? -1 : 1);
        this.updateButtonState();
    }

    private void updateButtonState() {
        this.icon = this.presets[this.iconIndex];
        this.buttonLeft.enabled = (iconIndex > 0);
        this.buttonRight.enabled = (iconIndex + 1 < presets.length);
        this.buttonShow.displayString = cookString();
    }

    private void processInitial(String s) {
        if (s == null || s.isEmpty()) {
            this.color = 'f';
            this.iconIndex = 0;
            this.icon = this.presets[this.iconIndex];
            return;
        }

        final String[] values = s.toLowerCase()
                .replaceAll("\\s", "")
                .split("(?<=§[a-zA-Z0-9])|(?=§[a-zA-Z0-9])");

        for (String value : values) {
            final String token = value.trim();
            if (token.isEmpty()) {
                continue;
            }

            if (token.startsWith("§")) {
                if (token.length() < 2) continue;

                final char color_token = token.charAt(1);
                if (NumberFeatures.isValidHEX(color_token)) {
                    this.color = color_token;
                    continue;
                }
                if (NumberFeatures.isValidFormattingChar(color_token)) {
                    this.setFormatting(color_token);
                    continue;
                }
            }

            //just set as char then
            this.putIfValid(token.charAt(0));
        }
    }

    private String cookString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("§").append(this.color);

        if (this.bold) builder.append("§l");
        if (this.strikethrough) builder.append("§m");
        if (this.underscore) builder.append("§n");
        if (this.italic) builder.append("§o");

        builder.append(this.icon);

        return builder.toString();
    }

    private void setFormatting(char c) {
        switch (c) {
            case 'l': {
                this.bold = true;
                return;
            }
            case 'm': {
                this.strikethrough = true;
                return;
            }
            case 'n': {
                this.underscore = true;
                return;
            }
            case 'o': {
                this.italic = true;
                return;
            }
        }
    }

    private void putIfValid(char c) {
        for (int i = 0; i < this.presets.length; i++) {
            if (this.presets[i] == c) {
                this.icon = c;
                this.iconIndex = i;
                return;
            }
        }
    }
}
