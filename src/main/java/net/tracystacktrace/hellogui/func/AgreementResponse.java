package net.tracystacktrace.hellogui.func;

import net.minecraft.client.gui.GuiScreen;

@FunctionalInterface
public interface AgreementResponse {
    void process(boolean agreed, GuiScreen parentScreen);
}
