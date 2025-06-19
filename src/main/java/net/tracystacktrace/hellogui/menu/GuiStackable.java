package net.tracystacktrace.hellogui.menu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.common.util.i18n.StringTranslate;
import org.jetbrains.annotations.NotNull;

public class GuiStackable extends GuiScreen {

    /* inner work-with interfaces and classes */

    @FunctionalInterface
    public interface LayoutProvider {
        void process(
                @NotNull final GuiStackable main,
                final int layer,
                final int offsetX,
                final int offsetY
        );
    }

    @FunctionalInterface
    public interface ButtonPressed {
        void process(
                @NotNull final GuiStackable main,
                @NotNull final GuiButton button
        );
    }

    public static class LayoutStyle {
        public final int default_button_width;
        public final int default_button_height;
        public final int default_button_gap;
        public final int default_section_gap;

        public LayoutStyle(int defaultButtonWidth, int defaultButtonHeight, int defaultButtonGap, int defaultSectionGap) {
            default_button_width = defaultButtonWidth;
            default_button_height = defaultButtonHeight;
            default_button_gap = defaultButtonGap;
            default_section_gap = defaultSectionGap;
        }
    }

    /* static values */

    public static final LayoutStyle DEFAULT_STYLE = new LayoutStyle(150, 20, 5, 17);

    /* main class */

    protected final LayoutStyle layoutStyle;
    protected final LayoutProvider layoutProvider;
    protected final ButtonPressed buttonTrigger;
    protected final int maxLayers;
    protected final String[] title;

    public GuiStackable(
            @NotNull GuiScreen parentScreen,
            @NotNull String[] title,
            int maxLayers,
            @NotNull LayoutStyle style,
            @NotNull LayoutProvider layoutProvider,
            @NotNull ButtonPressed buttonPressed
    ) {
        this.parentScreen = parentScreen;
        this.title = title;
        this.maxLayers = maxLayers;
        this.layoutStyle = style;
        this.layoutProvider = layoutProvider;
        this.buttonTrigger = buttonPressed;
    }

    public GuiStackable(
            @NotNull GuiScreen parentScreen,
            @NotNull String title,
            int maxLayers,
            @NotNull LayoutStyle style,
            @NotNull LayoutProvider layoutProvider,
            @NotNull ButtonPressed buttonPressed
    ) {
        this(parentScreen, StringTranslate.getInstance().translateKey(title).split("\n"), maxLayers, style, layoutProvider, buttonPressed);
    }

    public GuiStackable(
            @NotNull GuiScreen parentScreen,
            @NotNull String[] title,
            int maxLayers,
            @NotNull LayoutProvider layoutProvider,
            @NotNull ButtonPressed buttonPressed
    ) {
        this(parentScreen, title, maxLayers, DEFAULT_STYLE, layoutProvider, buttonPressed);
    }

    public GuiStackable(
            @NotNull GuiScreen parentScreen,
            @NotNull String title,
            int maxLayers,
            @NotNull LayoutProvider layoutProvider,
            @NotNull ButtonPressed buttonPressed
    ) {
        this(parentScreen, title, maxLayers, DEFAULT_STYLE, layoutProvider, buttonPressed);
    }

    /* public methods for use in uhh... layoutprovider */

    public void addButton(@NotNull GuiButton button) {
        this.controlList.add(button);
    }

    public int getButtonHeight(int layer) {
        return this.layoutStyle.default_section_gap + (12 * this.title.length) + ((layoutStyle.default_button_gap + layoutStyle.default_button_height) * layer);
    }

    /* protected inner methods */

    @Override
    public void initGui() {
        this.controlList.clear();

        final int offsetX = this.width / 2 - this.layoutStyle.default_button_width / 2;
        final int offsetY = this.height / 2 - getButtonHeight(maxLayers) / 2;

        for(int i = 0; i < (this.maxLayers - 1); i++) {
            this.layoutProvider.process(this, i, offsetX, offsetY);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        this.buttonTrigger.process(this, button);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();

        final int offsetY = this.height / 2 - getButtonHeight(maxLayers) / 2;

        for(int i = 0; i < title.length; i++) {
            this.drawCenteredString(fontRenderer, title[i], this.width / 2, offsetY + (12 * i), 0xFFFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, deltaTicks);
    }
}
