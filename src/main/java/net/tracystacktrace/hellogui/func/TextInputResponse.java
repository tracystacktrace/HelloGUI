package net.tracystacktrace.hellogui.func;

import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface TextInputResponse {
    void process(@NotNull String input, @Nullable GuiScreen parentScreen);
}
