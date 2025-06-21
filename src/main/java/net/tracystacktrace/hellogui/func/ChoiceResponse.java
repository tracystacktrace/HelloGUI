package net.tracystacktrace.hellogui.func;

import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ChoiceResponse {
    void process(int i, @Nullable GuiScreen parentScreen);
}
