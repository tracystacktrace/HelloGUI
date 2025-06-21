package net.tracystacktrace.hellogui.func;

import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface AgreementResponse {
    void process(boolean agreed, @Nullable GuiScreen parentScreen);
}
