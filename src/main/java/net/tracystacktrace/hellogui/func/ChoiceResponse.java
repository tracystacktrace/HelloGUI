package net.tracystacktrace.hellogui.func;

import net.minecraft.client.gui.GuiScreen;

@FunctionalInterface
public interface ChoiceResponse {
    void process(int i, GuiScreen parentScreen);
}
