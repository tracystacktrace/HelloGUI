package net.tracystacktrace.epsilon.helper;

import net.minecraft.client.gui.GuiButton;
import net.tracystacktrace.epsilon.gui.GuiEpsilonConfig;

public interface IActionTrigger {
    void onAction(GuiEpsilonConfig menu, GuiButton button);
}
