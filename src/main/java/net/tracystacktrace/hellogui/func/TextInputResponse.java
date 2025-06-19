package net.tracystacktrace.hellogui.func;

import net.minecraft.client.gui.GuiScreen;

@FunctionalInterface
public interface TextInputResponse {
    void process(String input, GuiScreen parentScreen);
}
